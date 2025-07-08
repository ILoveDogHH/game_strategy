package jedi.game.databases;


import jedi.game.exception.DaoException;


public enum DBManager {

    instance;





    public void initialize() throws DaoException, ClassNotFoundException{

        ServerCfgInfoManager.INSTANCE.init();

    }








    private static String getDecryptDbUrl(String ip, int port, String dbname, String user, String password) {
        return String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&autoReconnect=%b&useSSL=%b", ip, port, dbname,
                user, password, true, false);
    }


}
