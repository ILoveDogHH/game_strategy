package jedi.game.mysql;

import org.apache.commons.pool.BasePoolableObjectFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySqlPoolableObjectFactory extends BasePoolableObjectFactory<Connection> {

    private String dbUrl = "";

    public MySqlPoolableObjectFactory(String dbUrl) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.dbUrl = dbUrl;
    }

    @Override
    public Connection makeObject() throws Exception {
        return DriverManager.getConnection(dbUrl);
    }

    @Override
    public void destroyObject(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {

        }
    }

    /*
     * @Override
     * public boolean validateObject(Object obj)
     * {
     * Connection conn = (Connection) obj;
     * try {
     * return conn.isValid(0);
     * } catch (SQLException e) {
     * logger.error("validate db connection error", e);
     * }
     * 
     * return false;
     * }
     */
}