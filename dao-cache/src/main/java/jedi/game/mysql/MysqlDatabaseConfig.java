package jedi.game.mysql;

import jedi.game.exception.DaoException;
import jedi.game.utils.ConfigReader;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MysqlDatabaseConfig {
	private static Pattern pattern = Pattern.compile("jdbc:mysql://([\\w-.]+):(\\d+)/(\\w+?)[?](\\S*)");
	private static Pattern patternParams=Pattern.compile("(\\w+=\\w+)+");
	private static Pattern patternKeyValue=Pattern.compile("(\\w+)=(\\w+)");
	
	private String ip;
	private int port;
	private String dbName;
	private String user;
	private String password;
	boolean autoReconnect=true;
    boolean useSSL=false;
	private String characterEncoding = "utf8"; // 默认使用utf8字符集
	private Config poolConfig=null;
	
	public MysqlDatabaseConfig(String ip, int port, String dbName,String user,String password) {
		this.ip=ip;
		this.port=port;
		this.dbName=dbName;
		this.user=user;
		this.password=password;
		poolConfig=getDefaultConfig();
	}
	
	public MysqlDatabaseConfig(ConfigReader reader, String name) {
		// TODO 
		ip=reader.getString(name+".ip");
		port=reader.getInt(name+".port");
		dbName=reader.getString(name+".db_name");
		user=reader.getString(name+".user");
		password=reader.getString(name+".pwd");
		if(reader.containsKey(name+".autoReconnect")) {
			autoReconnect=reader.getBoolean(name+".autoReconnect");
		}
        if(reader.containsKey(name+".useSSL")) {
            useSSL=reader.getBoolean(name+".useSSL");
        }
		if (reader.containsKey(name + ".characterEncoding")) {
			characterEncoding = reader.getString(name + ".characterEncoding");
		}
		poolConfig=getDefaultConfig();
		if(reader.getBoolean(name+".config")) {
			if(reader.containsKey(name+".config.maxIdle")){
				poolConfig.maxIdle = reader.getInt(name+".config.maxIdle");
			}
			if(reader.containsKey(name+".config.minIdle")){
				poolConfig.minIdle = reader.getInt(name+".config.minIdle");
			}
			if(reader.containsKey(name+".config.maxActive")){
				poolConfig.maxActive = reader.getInt(name+".config.maxActive");
			}
			if(reader.containsKey(name+".config.maxWait")){
				poolConfig.maxWait = reader.getLong(name+".config.maxWait");
			}
			if(reader.containsKey(name+".config.whenExhaustedAction")){
				poolConfig.whenExhaustedAction = reader.getByte(name+".config.whenExhaustedAction");
			}
			if(reader.containsKey(name+".config.testOnBorrow")){
				poolConfig.testOnBorrow = reader.getBoolean(name+".config.testOnBorrow");
			}
			if(reader.containsKey(name+".config.testOnReturn")){
				poolConfig.testOnReturn = reader.getBoolean(name+".config.testOnReturn");
			}
			if(reader.containsKey(name+".config.testWhileIdle")){
				poolConfig.testWhileIdle = reader.getBoolean(name+".config.testWhileIdle");
			}
			if(reader.containsKey(name+".config.timeBetweenEvictionRunsMillis")){
				poolConfig.timeBetweenEvictionRunsMillis = reader.getLong(name+".config.timeBetweenEvictionRunsMillis");
			}
			if(reader.containsKey(name+".config.numTestsPerEvictionRun")){
				poolConfig.numTestsPerEvictionRun = reader.getInt(name+".config.numTestsPerEvictionRun");
			}
			if(reader.containsKey(name+".config.minEvictableIdleTimeMillis")){
				poolConfig.minEvictableIdleTimeMillis = reader.getLong(name+".config.minEvictableIdleTimeMillis");
			}
			if(reader.containsKey(name+".config.softMinEvictableIdleTimeMillis")){
				poolConfig.softMinEvictableIdleTimeMillis = reader.getLong(name+".config.softMinEvictableIdleTimeMillis");
			}
			if(reader.containsKey(name+".config.lifo")){
				poolConfig.lifo = reader.getBoolean(name+".config.lifo");
			}
		}
	}
	
	public MysqlDatabaseConfig(String dbUrl) throws DaoException {
		Matcher match=pattern.matcher(dbUrl);
		if(!match.find()||match.groupCount()<4) {
			throw new DaoException("uncorrect url for "+dbUrl);
		}
		ip=match.group(1);
		port=Integer.valueOf(match.group(2));
		dbName=match.group(3);
		String params = match.group(4);
		Matcher matchParams = patternParams.matcher(params);
		
		if(!matchParams.find()) {
			throw new DaoException("uncorrect url for "+dbUrl);
		}
		do {
			String keyValue=matchParams.group();
			Matcher matchKeyValue = patternKeyValue.matcher(keyValue);
			if(matchKeyValue.find()) {
				String key=matchKeyValue.group(1);
				String value=matchKeyValue.group(2);
				switch(key) {
					case "user":
						user=value;
						break;
					case "password":
						password=value;
						break;
					case "autoReconnect":
						autoReconnect=Boolean.valueOf(value);
                    case "useSSL":
                        useSSL=Boolean.valueOf(value);
						break;
					default:
						break;
				}
			}
		} while (matchParams.find());
		poolConfig=getDefaultConfig();
		poolConfig.testWhileIdle = true;
		poolConfig.maxActive = 64;
		poolConfig.maxIdle = 64;
		poolConfig.minIdle = 0;
	}
	
	public void setAutoReconnect(boolean autoReconnect) {
		this.autoReconnect=autoReconnect;
	}
    public void setUseSSL(boolean useSSL) {
        this.useSSL=useSSL;
    }

	// 设置连接的字符集编码
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	/**
	 * 默认的config
	 * @return
	 */
	private static Config getDefaultConfig() {
		Config config = new Config();
    	config.testOnBorrow = true;
    	config.testWhileIdle = true;
    	config.timeBetweenEvictionRunsMillis = 10000;
    	config.minEvictableIdleTimeMillis = 60000;
    	// 没有足够大连接数就等待
    	config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
    	return config;
	}
	public MysqlDatabaseConfig setMaxIdle(int maxIdle) {
		poolConfig.maxIdle=maxIdle;
		return this;
	}
	public MysqlDatabaseConfig setMinIdle(int minIdle) {
		poolConfig.minIdle=minIdle;
		return this;
	}
	public MysqlDatabaseConfig setMaxActive(int maxActive){
        poolConfig.maxActive=maxActive;
        return this;
	}
	public MysqlDatabaseConfig setMaxWait(long maxWait){
		poolConfig.maxWait=maxWait;
		return this;
	}
	public MysqlDatabaseConfig setWhenExhaustedAction(byte whenExhaustedAction){
		poolConfig.whenExhaustedAction=whenExhaustedAction;
		return this;
	}
	public MysqlDatabaseConfig setTestOnBorrow(boolean testOnBorrow){
		poolConfig.testOnBorrow=testOnBorrow;
		return this;
	}
	public MysqlDatabaseConfig setTestOnReturn(boolean testOnReturn){
		poolConfig.testOnReturn=testOnReturn;
		return this;
	}
	public MysqlDatabaseConfig setTestWhileIdle(boolean testWhileIdle){
		poolConfig.testWhileIdle=testWhileIdle;
		return this;
	}
	public MysqlDatabaseConfig setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
		poolConfig.timeBetweenEvictionRunsMillis=timeBetweenEvictionRunsMillis;
		return this;
	}
	public MysqlDatabaseConfig setNumTestsPerEvictionRun(int numTestsPerEvictionRun){
		poolConfig.numTestsPerEvictionRun=numTestsPerEvictionRun;
		return this;
	}
	public MysqlDatabaseConfig setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
		poolConfig.minEvictableIdleTimeMillis=minEvictableIdleTimeMillis;
		return this;
	}
	public MysqlDatabaseConfig setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis){
		poolConfig.softMinEvictableIdleTimeMillis=softMinEvictableIdleTimeMillis;
		return this;
	}
	public MysqlDatabaseConfig setLifo(boolean lifo){
		poolConfig.lifo=lifo;
		return this;
	}
	
	/**
	 * 数据库ip
	 * @return
	 */
	public String getIp(){
		return ip;
	}
	/**
	 * 数据库端口
	 * @return
	 */
	public int getPort(){
		return port;
	}
	/**
	 * 数据库名字
	 * @return
	 */
	public String getDbName(){
		return dbName;
	}
	/**
	 * 数据库账号
	 * @return
	 */
	public String getUser(){
		return user;
	}
	/**
	 * 数据库密码
	 * @return
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 连接地址(拼接)
	 * @return
	 */
	public String getUrl() {
		return String.format(
				"jdbc:mysql://%s:%d/%s?user=%s&password=%s&autoReconnect=%b&useSSL=%b&characterEncoding=%s", ip, port,
				dbName, user, password, autoReconnect, useSSL, characterEncoding);
	}
	
	@Override
	public String toString() {
		return String.format("MysqlDatabaseConfig [ip=%s, port=%d, dbName=%s, user=%s, password=%s]", 
				ip, port, dbName, user, password);
	}

	public Config getPoolConfig() {
		return poolConfig;
	}
}
