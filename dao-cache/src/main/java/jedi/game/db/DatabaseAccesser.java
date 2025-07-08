package jedi.game.db;



import jedi.game.exception.DaoException;
import jedi.game.mysql.MysqlArrayHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 *	数据库连接管理中心
 *	可以获得任意数据源
 *
 *	这个存在的意义是使得持有者（DAO）不再局限于一个库的访问权限，
 *	而是拥有所有的数据源
 */
public interface DatabaseAccesser {
	/**
	 * 根据唯一的key获取对应的DBInterface
	 * 
	 * @param uniqueKey
	 * @return
	 */
	DBInterface getDbByUniqueKey(Object uniqueKey) throws DaoException;

	/**
	 * 获取唯一的key来, 用来获得db
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	Object getUniqueKey(Object[] key) throws DaoException;

	/**
	 * 通过key直接获取db
	 * 
	 * @param key
	 * @return
	 * @throws DaoException
	 */
	default DBInterface getDb(Object[] key) throws DaoException {
		return getDbByUniqueKey(getUniqueKey(key));
	}

	/**
	 * 通过key对db进行分类
	 * 
	 * @param keys
	 * @return
	 * @throws DaoException
	 */
	default <T extends Object, U extends Collection<T[]>> Map<DBInterface, MysqlArrayHolder> classifyDbs(
			U keys) throws DaoException {
		Map<DBInterface, MysqlArrayHolder> dbs = new HashMap<>();
		for (T[] key : keys) {
			DBInterface db = getDbByUniqueKey(getUniqueKey(key));
			dbs.putIfAbsent(db, new MysqlArrayHolder());
			MysqlArrayHolder MysqlArrayHolder = dbs.get(db);
			MysqlArrayHolder.getArrays().add(key);
		}
		return dbs;
	}
}
