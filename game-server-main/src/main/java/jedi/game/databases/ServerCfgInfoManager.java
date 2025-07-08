package jedi.game.databases;

import jedi.game.exception.DaoException;
import jedi.game.manager.NewDaoManager;
import jedi.game.mysql.MysqlDatabase;
import jedi.game.mysql.MysqlDatabaseConfig;
import jedi.game.servercfg.base.ServerCfgCacheDaoInterface;

public enum ServerCfgInfoManager {


    INSTANCE;
    private ServerCfg coreData;



    public void init() throws DaoException, ClassNotFoundException {
        // 获得地图对应数据库的ip port等信息
        coreData = new ServerCfg(1);
        MysqlDatabaseConfig config = new MysqlDatabaseConfig("outside-dev-main.jedi-games.com",3306,"game_strategy_dev","game_strategy_dev","1HNVkhuC7Q6vAeak");
        MysqlDatabase mysqlDatabase = new MysqlDatabase(config);
        DatabasesManager.INSTANCE.addDatabase(getServerCfgDaoName(), mysqlDatabase);
        NewDaoManager.INSTANCE.register(ServerCfgCacheDaoInterface.class, GameDatabaseAccesser.SERVER_CFG.getAccesser() , new String[] { "jedi.game.servercfg.dao" });
    }






    public String getServerCfgDaoName() throws DaoException {
        if (coreData == null) {
            throw new DaoException("map has not initialized");
        }
        return coreData.getDaoName();
    }
}
