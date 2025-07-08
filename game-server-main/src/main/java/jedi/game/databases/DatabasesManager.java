package jedi.game.databases;




import jedi.game.db.DBInterface;
import jedi.game.exception.DaoException;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  保存整个游戏所有的数据库连接
 * */
public enum DatabasesManager {
	INSTANCE;
	/**
	 * key对应的数据库
	 */
	private ConcurrentHashMap<String, DBInterface> databases = new ConcurrentHashMap<>();



	public DBInterface getServerCfgDataBases() throws DaoException {
		return getDatabase(ServerCfgInfoManager.INSTANCE.getServerCfgDaoName());
	}




	/**
	 * 获取指定的DBInterface
	 * 
	 * @return
	 * @throws DaoException
	 */
	public DBInterface getDatabase(String key) throws DaoException {
		return databases.get(key);
	}

	/**
	 * 添加DBInterface
	 * 
	 * @param
	 * @return
	 * @throws DaoException
	 */
	public void addDatabase(String key, DBInterface database) {
		databases.put(key, database);
	}


	/**
	 * 是否已经初始化
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasInitialized(String key) {
		return databases.containsKey(key);
	}

	public Collection<String> getAllDaoNames() {
		return new HashSet<>(databases.keySet());
	}
}
