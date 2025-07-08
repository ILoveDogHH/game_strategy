package jedi.game.cache;



import jedi.game.db.BasicCacheDatabaseDao;
import jedi.game.exception.DaoException;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 可以理解为一个缓存的代理类 并且在这一层保证数据线程安全
 * 
 * @author @cc
 *
 */
public class CacheDatas<T> implements ICacheDatas<T> {
	/**
	 * 提供从数据库中查询内容的方法
	 */
	private BasicCacheDatabaseDao dao;
	/**
	 * 表示cache当前的哪个表
	 */
	private CacheTable table;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	// 当前已经缓存的key
	private Map<Object, Set<CacheKey>> cacheKeys = new HashMap<>();


	public static CacheDatas newCacheDatas(BasicCacheDatabaseDao dao) {
		CacheDatas newCacheDatas = new CacheDatas(dao);
		CacheDatasEventCenter.registerDao(newCacheDatas.table, newCacheDatas);
		return newCacheDatas;
	}

	private CacheDatas(BasicCacheDatabaseDao dao) {
		this.table = new CacheTable(dao.getTableType(), dao.getTableName());
		setDatabaseAccessObject(dao);
	}

	// 仅仅为继承而用 严禁自己调用
	public CacheDatas() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDatabaseAccessObject(BasicCacheDatabaseDao dao) {
		this.dao = dao;
		if (table == null)
			this.table = dao.getTable();
	}

	@Override
	public String getTableName() {
		return dao.getTableName();
	}

	@Override
	public int getTableType() {
		return dao.getTableType();
	}

	@Override
	public void invalidateAll() {
		Set<CacheKey> tmp = new HashSet<CacheKey>();
		lock.writeLock().lock();
		try {
			Iterator<Entry<Object, Set<CacheKey>>> iterator = cacheKeys.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Object, Set<CacheKey>> next = iterator.next();
				tmp.addAll(next.getValue());
			}
			if (tmp.size() != 0)
			CacheDatasEventCenter.invalidateKeys(table, tmp);
			cacheKeys.clear();
		} finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void invalidateKey(Object[] key) {
		Set<CacheKey> invalidateKeys = null;
		//extraLock.writeLock().lock();
		try {
			invalidateKeys = getInvalidateKeys(key);
		} finally {
			//	extraLock.writeLock().unlock();
		}
		if (invalidateKeys != null && invalidateKeys.size() != 0)
			CacheDatasEventCenter.invalidateKeys(table, invalidateKeys);
	}

	@Override
	public void invalidateKeys(Collection<Object[]> keys) {
		//extraLock.writeLock().lock();
		Set<CacheKey> invalidateKeys = null;
		try {
			 invalidateKeys = getInvalidateKeys(keys);
		}finally
		{
		//	extraLock.writeLock().unlock();
		}
		if (invalidateKeys != null && invalidateKeys.size() != 0)
			CacheDatasEventCenter.invalidateKeys(table, invalidateKeys);
	}

	// key的顺序和数量必须一致
	@Override
	public T getOne(Object[] key) throws DaoException {
		if (key.length != dao.getColumns().length) {
			throw new DaoException("error on getOne:length number error");
		}
		if(key.length == 0){
			throw new DaoException("error on getList: length is empty");
		}
		CacheKey cacheKey = new CacheKey(dao.getTableName(), dao.getTableType(), key);
	//	extraLock.readLock().lock();
		try {
			CacheData data = CacheDatasEventCenter.get(cacheKey);
			if (data == null) {
				throw new DaoException("error on getOne: data is null");
			}
			addCacheKey(cacheKey);
			return (T) data.getOne().orElseGet(null);
		}finally {
	//		extraLock.readLock().unlock();
		}

	}

	@Override
	public T getOneFromDao(Object[] key) throws DaoException {
		return (T) dao.getOne(key);
	}

	@Override
	public T getOneCustomFromDao(Object[] key, String sql, Object... args) throws DaoException {
		return (T) dao.getOneCustom(key, sql, args);
	}

	@Override
	public boolean checkOneFromDao(Object[] key, String sql, Object... args) throws DaoException {
		return dao.getOneCustom(key, sql, args) == null ? false : true;
	}


	// 小于等于都可以 但是。顺序必须一致
	@Override
	public ConcurrentLinkedQueue<T> getList(Object[] key) throws DaoException {
		if (key.length > dao.getColumns().length) {
			throw new DaoException("error on getList: length number out of columns.size");
		}
		if(key.length == 0){
			throw new DaoException("error on getList: length is empty");
		}
		//extraLock.readLock().lock();
		try {
			CacheKey cacheKey = new CacheKey(dao.getTableName(), dao.getTableType(), key);
			CacheData data = CacheDatasEventCenter.get(cacheKey);
			if (data == null) {
				throw new DaoException("error on getOne: data is null");
			}
			addCacheKey(cacheKey);
			return data.getList();
		}finally {
		//	extraLock.readLock().unlock();
		}



	}

	@Override
	public ConcurrentLinkedQueue<T> getListFromDao(Object[] key) throws DaoException {
		return new ConcurrentLinkedQueue<>(dao.getList(key));
	}

	@Override
	public ConcurrentLinkedQueue<T> getListCustomFromDao(Object[] key, String sql, Object... args)
			throws DaoException {
		return new ConcurrentLinkedQueue<>(dao.getListCustom(key, sql, args));
	}

	@Override
	public ConcurrentLinkedQueue<T> getBatch(Collection<Object[]> keys) throws DaoException {
		if (keys.isEmpty()) {
			return new ConcurrentLinkedQueue<T>();
		}
		Set<CacheKey> notExistsKeys = new HashSet<>();
		for(Object[] k : keys){
			if(k.length == 0){
				throw new DaoException("error on getBatch: length is empty");
			}
			notExistsKeys.add(new CacheKey(dao.getTableName(), dao.getTableType(), k));
		}
	//	extraLock.readLock().lock();
		try {
			Map<CacheKey, CacheData> cacheDatas = CacheDatasEventCenter.getAll(notExistsKeys);

			lock.writeLock().lock();
			try {
				Iterator<Entry<CacheKey, CacheData>> iterator = cacheDatas.entrySet().iterator();
				while (iterator.hasNext()) {
					CacheKey key = iterator.next().getKey();
					addCacheKey(key);
				}
			}finally
			{
				lock.writeLock().unlock();
			}

			ConcurrentLinkedQueue<T> allData = cacheDatas.values().stream().map(c -> c.getList())
					.collect(ConcurrentLinkedQueue::new, ConcurrentLinkedQueue::addAll, ConcurrentLinkedQueue::addAll);
			return allData;
		}finally {
	//		extraLock.readLock().unlock();
		}
	}

	@Override
	public ConcurrentLinkedQueue<T> getBatchFromDao(Collection<Object[]> keys) throws DaoException {
		if (keys.isEmpty()) {
			return new ConcurrentLinkedQueue<T>();
		}
		return new ConcurrentLinkedQueue<>(dao.getBatch(keys));
	}

	@Override
	public ConcurrentLinkedQueue<T> getBatchCustomFromDao(Collection<Object[]> keys, String sql, Object... args)
			throws DaoException {
		if (keys.isEmpty()) {
			return new ConcurrentLinkedQueue<T>();
		}
		return new ConcurrentLinkedQueue<>(dao.getBatchCustom(keys, sql, args));
	}

	@Override
	public int update(Object[] key, String sql, Object... args) throws DaoException {
		int ret  = dao.update(key, sql, args);
		invalidateKey(key);
		return ret;
	}

	@Override
	public int insert(Object[] key, String sql, Object... args) throws DaoException {
		int ret = dao.insert(key, sql, args);
		invalidateKey(key);
		return ret;
	}


	@Override
	public List<Integer> insert_batch(Object[] key, String sql, Object... args) throws DaoException {
		List<Integer> ret = dao.insert_batch(key, sql, args);
		invalidateKey(key);
		return ret;
	}



	@Override
	public int updateBatch(Collection<Object[]> keys, String sql, Object... args) throws DaoException {
		if (keys.isEmpty()) {
			return 0;
		}
		int ret = dao.updateBatch(keys, sql, args);
		invalidateKeys(keys);

		return ret;
	}
	
	@Override
	public int updateBatch(String sql, Map<Object[], List<Object>> keyArgs) throws DaoException {
		if (keyArgs.isEmpty()) {
			return 0;
		}
		int ret = dao.updateBatch(sql, keyArgs);
		Collection<Object[]> keys = keyArgs.keySet();
		invalidateKeys(keys);
		return ret;
	}

	public void  removeKey(CacheKey key) {
		lock.writeLock().lock();
		try {
			Set<CacheKey> set = cacheKeys.get(key.getKey().length);
			if (set == null || CacheDatasEventCenter.getIfPresent(key) != null)
				return;
			set.remove(key);	
		}finally {
			lock.writeLock().unlock();
		}

	}

	public void addCacheKey(CacheKey key) {
		lock.writeLock().lock();
		try {
			cacheKeys.putIfAbsent(key.getKey()[0], new HashSet<>());
			Set<CacheKey> set = cacheKeys.get(key.getKey()[0]);
			CacheData data = CacheDatasEventCenter.getIfPresent(key);
			if(data != null){
				set.add(key);
			}
		}finally
		{
			lock.writeLock().unlock();
		}

	}

	/**
	 * 获取失效的Key</br>
	 * 对于每个Key: 小于key.length的所有; 大于等于key.length时, 判断前n个项目
	 * 
	 * @return
	 */
	public Set<CacheKey> getInvalidateKeys(Collection<Object[]> keys) {
		Set<CacheKey> invalidateKeys = new HashSet<>();
		lock.readLock().lock();
		try {
			for(Object[] k : keys ){
				Object[] key = new Object[k.length];
				for(int i = 0; i < k.length; i++) {
					key[i] = k[i];
					Set<CacheKey> invalidateKey = cacheKeys.getOrDefault(key, new HashSet<>());
					invalidateKeys.addAll(invalidateKey);
				}
			}
		}finally {
			lock.readLock().unlock();
		}
		return invalidateKeys;
	}


	public Set<CacheKey> getInvalidateKeys(Object[] key) {
		return getInvalidateKeys(Collections.singletonList(key));
	}



}
