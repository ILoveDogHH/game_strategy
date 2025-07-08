package jedi.game.cache;


import jedi.game.db.BasicCacheDatabaseDao;
import jedi.game.exception.DaoException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface ICacheDatas<T> extends IEventCacheDatas {
	/**
	 * 设置dao
	 * 
	 * @param dao
	 */
	void setDatabaseAccessObject(BasicCacheDatabaseDao dao);

	/**
	 * 通过完整的key获取一个数据
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	T getOne(Object[] key) throws DaoException;

	/**
	 * 单独从dao(数据库)中获取一个数据, 不经过缓存
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	T getOneFromDao(Object[] key) throws DaoException;

	/**
	 * 单独从dao(数据库)中获取特定sql的多组数据, 不经过缓存
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	T getOneCustomFromDao(Object[] key, String sql, Object... args) throws DaoException;

	/**
	 * 通过完整的key获取一个数据, 不经过缓存
	 *
	 * @param key
	 **/
	boolean checkOneFromDao(Object[] key, String sql, Object... args) throws DaoException;


	/**
	 * 通过一个key获取list数组(可以是完整的key或者不完整的key)
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	ConcurrentLinkedQueue<T> getList(Object[] key) throws DaoException;

	/**
	 * 单独从dao(数据库)中获取多个数据, 不经过缓存
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	ConcurrentLinkedQueue<T> getListFromDao(Object[] key) throws DaoException;
	/**
	 * 单独从dao(数据库)中获取特定sql的多组数据, 不经过缓存
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	ConcurrentLinkedQueue<T> getListCustomFromDao(Object[] key, String sql, Object... args) throws DaoException;

	/**
	 * 通过多组key获取多组数据
	 * 
	 * @param keys
	 * @return
	 * @throws DaoException
	 */
	ConcurrentLinkedQueue<T> getBatch(Collection<Object[]> keys) throws DaoException;

	/**
	 * 单独从dao(数据库)中获取多组数据, 不经过缓存
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	ConcurrentLinkedQueue<T> getBatchFromDao(Collection<Object[]> keys) throws DaoException;

	/**
	 * 单独从dao(数据库)中获取特定sql的多组数据, 不经过缓存
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	ConcurrentLinkedQueue<T> getBatchCustomFromDao(Collection<Object[]> keys, String sql, Object... args)
			throws DaoException;

	/**
	 * 更新一个key的数据
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	int update(Object[] key, String sql, Object... args) throws DaoException;

	/**
	 * 插入一个key的数据, 在需要返回自增列的值时使用
	 * 
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	int insert(Object[] key, String sql, Object... args) throws DaoException;


	/**
	 * 批量插入一个key的数据, 在需要返回自增列的值时使用
	 *
	 * @param key
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	List<Integer> insert_batch(Object[] key, String sql, Object... args) throws DaoException;


	/**
	 * 更新一组key的数据
	 * 
	 * @param keys
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	int updateBatch(Collection<Object[]> keys, String sql, Object... args) throws DaoException;

	/**
	 * 更新一组key的数据,每个key都有自己单独的参数
	 * 
	 * @param keys
	 * @param sql
	 * @param args
	 * @return
	 * @throws DaoException
	 */
	int updateBatch(String sql, Map<Object[], List<Object>> keyArgs) throws DaoException;
}
