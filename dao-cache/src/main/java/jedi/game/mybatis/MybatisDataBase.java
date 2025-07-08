package jedi.game.mybatis;//package mybatis;
//
//import com.baomidou.mybatisplus.core.MybatisConfiguration;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import db.DBInterface;
//import org.apache.ibatis.mapping.Environment;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
//
//import javax.sql.DataSource;
//
//public class MybatisDataBase  implements DBInterface {
//
//    private SqlSessionFactory sqlSessionFactory ;
//
//
//
//    /**
//     * 对外调用：只传 ip、端口、库名、用户名、密码、mapper包名
//     */
//    public void register(String ip, int port, String dbName, String user, String password, String packageName) {
//        String jdbcUrl = buildJdbcUrl(ip, port, dbName);
//        register(jdbcUrl, user, password, packageName);
//    }
//
//
//    private void register(String jdbcUrl, String user, String pass, String packageName) {
//        DataSource dataSource = HikariDataSourceFactory.create(jdbcUrl, user, pass);
//        // 构建 MyBatis-Plus Configuration
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setMapUnderscoreToCamelCase(true);
//
//        // 加载 Mapper 包，注册 Mapper
//        // 这里扫描并添加所有 Mapper 接口
//        configuration.addMappers(packageName);
//
//        // 添加 MyBatis-Plus 拦截器（分页等）
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        // 这里可添加分页、乐观锁等拦截器
//        configuration.addInterceptor(interceptor);
//
//        Environment environment = new Environment("development", new JdbcTransactionFactory(), dataSource);
//        configuration.setEnvironment(environment);
//
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//
//    }
//
//
//
//
//    /**
//     * JDBC 拼接函数（可扩展参数）
//     */
//    private static String buildJdbcUrl(String ip, int port, String dbName) {
//        return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", ip, port, dbName);
//    }
//
//}
