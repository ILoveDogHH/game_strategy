package jedi.game.db;
/**
 * 数据源持有者
 * 存在是为了让结构清晰
 * */
public  interface DatabaseAccesserHolder {
	
	public DatabaseAccesser getAccesser() ;
	
}
