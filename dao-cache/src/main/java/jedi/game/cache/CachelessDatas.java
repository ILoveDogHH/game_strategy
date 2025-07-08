package jedi.game.cache;


import jedi.game.db.BasicCacheDatabaseDao;
import jedi.game.exception.DaoException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 缓存数据, 包括如何从db中读取数据并缓存
 * 
 * @author @cc
 *
 */
public class CachelessDatas<T> implements ICacheDatas<T> {
	private final CachelessDatas instance;
	private BasicCacheDatabaseDao dao;

	public CachelessDatas() {
		instance = this;
	}

	public CachelessDatas(BasicCacheDatabaseDao dao) {
		this();
		setDatabaseAccessObject(dao);
	}

	/**
	 * 设置dao
	 * 
	 * @param dao
	 */
	@Override
	public void setDatabaseAccessObject(BasicCacheDatabaseDao dao) {
		this.dao = dao;
	}

	/**
	 * 获取表名
	 * 
	 * @return
	 */
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
	}

	@Override
	public void invalidateKey(Object[] key) {
	}

	@Override
	public void invalidateKeys(Collection<Object[]> keys) {
	}

	@Override
	public T getOne(Object[] key) throws DaoException {
		return getOneFromDao(key);
	}

	@Override
	public T getOneFromDao(Object[] key) throws DaoException {
		return (T) dao.getOne(key);
	}

	@Override
	public T getOneCustomFromDao(Object[] key, String sql, Object... args)
			throws DaoException {
		return (T) dao.getOneCustom(key, sql, args);
	}

	@Override
	public boolean checkOneFromDao(Object[] key, String sql, Object... args) throws DaoException {
		return dao.getOneCustom(key, sql, args) == null ? false : true;
	}

	@Override
	public ConcurrentLinkedQueue<T> getList(Object[] key) throws DaoException {
		return getListFromDao(key);
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
		if(keys.isEmpty()) {
			return new ConcurrentLinkedQueue<T>();
		}
		return getBatchFromDao(keys);
	}

	@Override
	public ConcurrentLinkedQueue<T> getBatchFromDao(Collection<Object[]> keys) throws DaoException {
		if(keys.isEmpty()) {
			return new ConcurrentLinkedQueue<T>();
		}
		return new ConcurrentLinkedQueue<>(dao.getBatch(keys));
	}

	@Override
	public ConcurrentLinkedQueue<T> getBatchCustomFromDao(Collection<Object[]> keys, String sql, Object... args)
			throws DaoException {
		if(keys.isEmpty()) {
			return new ConcurrentLinkedQueue<T>();
		}
		return new ConcurrentLinkedQueue<>(dao.getBatchCustom(keys, sql, args));
	}

	@Override
	public int update(Object[] key, String sql, Object... args) throws DaoException {
		int ret = dao.update(key, sql, args);
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
		if(keys.isEmpty()) {
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
}
