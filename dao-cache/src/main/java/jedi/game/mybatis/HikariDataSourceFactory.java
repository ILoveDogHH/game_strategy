//package jedi.game.mybatis;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import javax.sql.DataSource;
//
//public class HikariDataSourceFactory {
//
//
//    /**
//     * 创建默认配置的 HikariDataSource
//     */
//    public static DataSource create(String jdbcUrl, String username, String password) {
//        return create(jdbcUrl, username, password, 10, 2, 30000, 10000);
//    }
//
//    /**
//     * 创建可配置参数的 HikariDataSource
//     */
//    public static DataSource create(String jdbcUrl,
//                                    String username,
//                                    String password,
//                                    int maxPoolSize,
//                                    int minIdle,
//                                    long idleTimeout,
//                                    long connectionTimeout) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(jdbcUrl);
//        config.setUsername(username);
//        config.setPassword(password);
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//
//        config.setMaximumPoolSize(maxPoolSize);
//        config.setMinimumIdle(minIdle);
//        config.setIdleTimeout(idleTimeout);
//        config.setConnectionTimeout(connectionTimeout);
//
//        return new HikariDataSource(config);
//    }
//}
