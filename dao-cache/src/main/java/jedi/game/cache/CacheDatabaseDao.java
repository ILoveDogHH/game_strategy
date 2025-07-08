package jedi.game.cache;



import jedi.game.exception.DaoException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CacheDatabaseDao<T> {
	/**
	 * 通过一个完整的key(其中能拿到唯一的uniqueKey)获取单条的数据
	 * 
	 * @param keys
	 * @return
	 */
	T getOne(Object[] keys) throws DaoException;

	/**
	 * 根据一个key(其中每个都能拿到唯一的uniqueKey)获取多条的数据
	 * 
	 * @param keys
	 * @return
	 */
	List<T> getList(Object[] keys) throws DaoException;

	/**
	 * 根据多个key(其中每个都能拿到唯一的uniqueKey)获取对应的数据
	 * 
	 * @param keys
	 * @return
	 */
	List<T> getBatch(Collection<Object[]> keys) throws DaoException;


	/**
	 * 根据多个key(其中每个都能拿到唯一的uniqueKey)获取对应的数据
	 *
	 * @param cacheKeys
	 * @return
	 */
	Map<CacheKey, CacheData> getBatchT(Collection<Object[]> keys) throws DaoException;


	/**
	 * 通过部分的key(其中能拿到唯一的uniqueKey)更新sql
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 */
	int update(Object[] key, String sql, Object... args) throws DaoException;

	/**
	 * 通过部分的key(其中能拿到唯一的uniqueKey)插入数据, 并返回插入数据库产生的自增key值
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 */
	int insert(Object[] key, String sql, Object... args) throws DaoException;


	/**
	 * 通过部分的key(其中能拿到唯一的uniqueKey)插入数据, 并返回插入数据库产生的自增key值
	 *
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 */
	List<Integer> insert_batch(Object[] key, String sql, Object... args) throws DaoException;

	/**
	 * 通过完整的key(其中能拿到唯一的uniqueKey)批量更新sql
	 * 
	 * @param keys
	 * @param sql
	 * @param args
	 * @return
	 */
	int updateBatch(Collection<Object[]> keys, String sql, Object... args) throws DaoException;

	CacheTable getCacheTable();
}
