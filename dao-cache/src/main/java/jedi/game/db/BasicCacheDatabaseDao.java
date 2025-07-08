package jedi.game.db;



import jedi.game.cache.*;
import jedi.game.exception.DaoException;
import jedi.game.mysql.Keys;
import jedi.game.mysql.MysqlArrayHolder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主要用来保存数据库源
 * 与常用查询方法的封装
 * 可以理解为一个表层次的封装 提供对某一个表的操作
 *  理论上一个需要缓存的表对象持有一个这个对象
 * */
public class BasicCacheDatabaseDao<T> implements DatabaseAccesserHolder, CacheDatabaseDao<T> {
	protected final CacheTable table;
	protected final String[] columns;
	protected final DatabaseAccesser dbAccesser;
	protected final Class<T> mapperClass;

	private ConcurrentHashMap<Integer, String> getters = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Integer, String> gettersBatch = new ConcurrentHashMap<>();

	public BasicCacheDatabaseDao(DatabaseAccesser dbAccesser, Class<?> mapperClass,  int tableType, String tableName, String[] columns) {
		this.dbAccesser = dbAccesser;
		/*
		 * USER = 1;STATE = 11; public
		 * MAP = EXTRA_CENTER = 31;
		 */
		this.table = new CacheTable(tableType,tableName);
		this.columns = columns;
		this.mapperClass = (Class<T>) mapperClass;
		StringBuilder sql = new StringBuilder(String.format("select * from %s", tableName));
		String sqlBatch = String.format("select * from %s", tableName);
		getters.put(0, sql.toString());
		gettersBatch.put(0, sqlBatch);
		sql.append(" where ");
		String sqlBatchTail = " where (%s) in (?)";
		StringBuilder sqlBatchTailKey = new StringBuilder();
		for (int i = 0; i < columns.length; i++) {
			sql.append(columns[i]).append("=? and ");
			getters.put(i + 1, sql.substring(0, sql.length() - 5));

			sqlBatchTailKey.append(columns[i]).append(",");
			gettersBatch.put(i + 1,
					sqlBatch + String.format(sqlBatchTail, sqlBatchTailKey.substring(0, sqlBatchTailKey.length() - 1)));
		}
	}

	public String getTableName() {
		return table.getTableName();
	}

	public int getTableType() {
		return table.getTableType();
	}

	public String[] getColumns() {
		return columns;
	}

	
	public CacheTable getTable() {
		return table;
	}

	//这里决定了长度必须一致 并且顺序不能乱
	@Override
	public T getOne(Object[] key) throws DaoException {
		if (key.length != columns.length) {
			return null;
		}
		String sql = getters.get(key.length);
		T sql_fetch_one = (T) getAccesser().getDb(key).sql_fetch_one(mapperClass, sql, key);
		return sql_fetch_one;
	}

	/**
	 * 通过一个完整的key(其中能拿到唯一的uniqueKey)+特定的sql语句获取单条的数据
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	public T getOneCustom(Object[] key, String sql, Object... args) throws DaoException {
		return (T) getAccesser().getDb(key).sql_fetch_one(mapperClass, sql, args);
	}

	@Override
	public List<T> getList(Object[] key) throws DaoException {
		if (key.length > columns.length) {
			return new ArrayList<>();
		}
		if (key.length == 0) {
			throw new DaoException("unknown key-length=0");
		}
		String sql = getters.get(key.length);
		return getAccesser().getDb(key).sql_fetch_rows(mapperClass, sql, key);
	}






	/**
	 * 通过一个完整的key(其中能拿到唯一的uniqueKey)+特定的sql语句获取多条数据
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	public List<T> getListCustom(Object[] key, String sql, Object... args) throws DaoException {
		return getAccesser().getDb(key).sql_fetch_rows(mapperClass, sql, args);
	}
	/**
	 * 要保证所有的key的长度是一样的
	 * */
	@Override
	public List<T> getBatch(Collection<Object[]> keys) throws DaoException {
		if (keys.size() == 0) {
			return new ArrayList<>();
		}
		List<Object[]> uids = new ArrayList<>();
		int length = 0;
		for (Object[] key : keys) {
			if (key.length == 0) {
				throw new DaoException("unknown keys-length=0");
			}
			if (length == 0) {
				length = key.length;
			}
			if (length != key.length) {
				throw new DaoException("keys.length not same");
			}
			uids.add(key);
		}
		if (length == 0 || length > columns.length) {
			throw new DaoException("uncorrect length");
		}
		List<T> data = new ArrayList<>();
		Map<DBInterface, MysqlArrayHolder> dbs = getAccesser().classifyDbs(uids);
		for (DBInterface db : dbs.keySet()) {
			data.addAll(db.sql_fetch_rows(mapperClass, gettersBatch.get(length), dbs.get(db)));
		}
		return data;
	}



	@Override
	public Map<CacheKey, CacheData> getBatchT(Collection<Object[]> keys) throws DaoException {
		if (keys.size() == 0) {
			return new HashMap<>();
		}
		List<Object[]> uids = new ArrayList<>();
		int length = 0;
		for (Object[] key : keys) {
			if (key.length == 0) {
				throw new DaoException("unknown keys-length=0");
			}
			if (length == 0) {
				length = key.length;
			}
			if (length != key.length) {
				throw new DaoException("keys.length not same");
			}
			uids.add(key);
		}
		if (length == 0 || length > columns.length) {
			throw new DaoException("uncorrect length");
		}
		Map<DBInterface, MysqlArrayHolder> dbs = getAccesser().classifyDbs(uids);
		Map<CacheKey, CacheData> batch = new HashMap<>();
		for (DBInterface db : dbs.keySet()) {
			Map<Keys, List<T>> res = db.sql_fetch_rows(mapperClass, columns,  gettersBatch.get(length), dbs.get(db));
			for(Map.Entry<Keys, List<T>> entry : res.entrySet()) {
				CacheKey cacheKey = new CacheKey(table, entry.getKey().getKey());
				CacheData cacheData = new CacheData(entry.getValue());
				batch.put(cacheKey, cacheData);
			}
		}
		return batch;
	}


	/**
	 * 根据多个key(其中每个都能拿到唯一的uniqueKey)+特定的sql语句获取对应的数据
	 * 
	 * @param
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	public List<T> getBatchCustom(Collection<Object[]> keys, String sql, Object... args) throws DaoException {
		if (keys.size() == 0) {
			return new ArrayList<>();
		}
		List<Object[]> uids = new ArrayList<>();
		int length = 0;
		for (Object[] key : keys) {
			if (key.length == 0) {
				throw new DaoException("unknown keys-length=0");
			}
			if (length == 0) {
				length = key.length;
			}
			if (length != key.length) {
				throw new DaoException("keys.length not same");
			}
			uids.add(key);
		}
		if (length == 0 || length > columns.length) {
			throw new DaoException("uncorrect length");
		}
		List<T> data = new ArrayList<>();
		Map<DBInterface, MysqlArrayHolder> dbs = getAccesser().classifyDbs(uids);
		for (DBInterface db : dbs.keySet()) {
			Object[] newArgs = Arrays.copyOf(args, args.length);
			for (int i = 0; i < newArgs.length; i++) {
				if (newArgs[i] == CacheUtils.TO_REPLACE_KEY) {
					newArgs[i] = dbs.get(db);
				}
			}
			data.addAll(db.sql_fetch_rows(mapperClass, sql, newArgs));
		}
		return data;
	}

	@Override
	public int update(Object[] key, String sql, Object... args) throws DaoException {
		return getAccesser().getDb(key).sql_update(sql, args);
	}

	@Override
	public int insert(Object[] key, String sql, Object... args) throws DaoException {
		return getAccesser().getDb(key).sql_insert(sql, args);
	}

	@Override
	public List<Integer> insert_batch(Object[] key, String sql, Object... args) throws DaoException {
		return getAccesser().getDb(key).sql_insert_batch(sql, args);
	}

	@Override
	public int updateBatch(Collection<Object[]> keys, String sql, Object... args) throws DaoException {
		if (keys.size() == 0) {
			return 0;
		}
		List<Object[]> uids = new ArrayList<>();
		int length = 0;
		for (Object[] key : keys) {
			if (key.length == 0) {
				throw new DaoException("unknown keys-length=0");
			}
			if (length == 0) {
				length = key.length;
			}
			if (length != key.length) {
				throw new DaoException("keys.length not same");
			}
			uids.add(key);
		}
		if (length == 0 || length > columns.length) {
			throw new DaoException("uncorrect length");
		}
		int ret = 0;
		Map<DBInterface, MysqlArrayHolder> dbs = getAccesser().classifyDbs(uids);
		for (DBInterface db : dbs.keySet()) {
			Object[] newArgs = Arrays.copyOf(args, args.length);
			for (int i = 0; i < newArgs.length; i++) {
				if (newArgs[i] == CacheUtils.TO_REPLACE_KEY) {
					newArgs[i] = dbs.get(db);
				}
			}
			ret += db.sql_update(sql, newArgs);
		}
		return ret;
	}

	public int updateBatch(String sql, Map<Object[], List<Object>> keyArgs) throws DaoException {
		if (keyArgs.size() == 0) {
			return 0;
		}
		Map<DBInterface, List<List<Object>>> dbArgs = new HashMap<>();
		int length = 0;
		for (Object[] key : keyArgs.keySet()) {
			if (key.length == 0) {
				throw new DaoException("unknown keys-length=0");
			}
			if (length == 0) {
				length = key.length;
			}
			if (length != key.length) {
				throw new DaoException("keys.length not same");
			}
			DBInterface db = getAccesser().getDb(key);
			dbArgs.putIfAbsent(db, new ArrayList<>());
			dbArgs.get(db).add(keyArgs.get(key));
		}
		if (length == 0 || length > columns.length) {
			throw new DaoException("uncorrect length");
		}
		int ret = 0;
		for (DBInterface db : dbArgs.keySet()) {
			int[] retTmp = db.batchUpdate(sql, dbArgs.get(db));
			for (int r : retTmp) {
				ret += r;
			}
		}
		return ret;
	}

	
	public DBInterface getDbByUniqueKey(Object uniqueKey) throws DaoException {
		return dbAccesser.getDbByUniqueKey(uniqueKey);
	}

	
	public Object getUniqueKey(Object[] key) throws DaoException {
		return dbAccesser.getUniqueKey(key);
	}

	@Override
	public DatabaseAccesser getAccesser() {
		// TODO Auto-generated method stub
		return dbAccesser;
	}

	@Override
	public CacheTable getCacheTable() {
		// TODO Auto-generated method stub
		return table;
	}


	public Class<T> getMapperClass() {
		return mapperClass;
	}
}
