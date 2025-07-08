package jedi.game.mybatis;//package mybatis;
//
//import db.DBInterface;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class MyBatisFactory {
//
//    // 缓存不同的数据源工
//    private static final Map<String, DBInterface> factoryCache = new ConcurrentHashMap<>();
//
//    /**
//     * 获取 SqlSessionFactory，可缓存
//     *
//     * @param host        数据库 IP
//     * @param port        数据库端口
//     * @param dbName      数据库名
//     * @param user        用户名
//     * @param password    密码
//     * @param mapperPkg   Mapper 包路径（如：mybatis.mapper）
//     * @return SqlSessionFactory
//     */
//    public static void register(String host, int port, String dbName,
//                                               String user, String password, String mapperPkg) {
//        // 否则初始化
//        MybatisDataBase mybatisDataBase = new MybatisDataBase();
//        mybatisDataBase.register(host, port, dbName, user, password, mapperPkg);
//        factoryCache.put(dbName, mybatisDataBase);
//    }
//
//
//
//    public  static DBInterface getFactory(String dbName) {
//        return factoryCache.get(dbName);
//    }
//
//
//}
