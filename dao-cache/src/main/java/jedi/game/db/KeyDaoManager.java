package jedi.game.db;


import jedi.game.exception.DaoException;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class KeyDaoManager<K, T extends DaoBase> {
	private ConcurrentHashMap<K, ConcurrentHashMap<Class<?>, T>> daos;
	
	public KeyDaoManager() {
		daos=new ConcurrentHashMap<>();
	}
	
	/**
	 * 增加一个key-dao实例
	 * @param key
	 * @param dao
	 */
	public void add(K key, T dao){
	    Optional.ofNullable(dao).ifPresent(d->{
	        daos.putIfAbsent(key, new ConcurrentHashMap<>());
	        daos.get(key).putIfAbsent(d.getClass(), d);
	    });
	}
	
	/**
	 * 删除一个key
	 * @param key
	 * @param dao
	 */
	public void remove(K key){
	    if (daos.containsKey(key)) {
	    	daos.remove(key);
		}
	}
	/**
	 * 获取key-dao实例
	 * @param key
	 * @param claz
	 * @return
	 * @throws
	 */
	@SuppressWarnings("unchecked")
    public <U extends T> U get(K key, Class<U> claz) throws DaoException {
        daos.putIfAbsent(key, new ConcurrentHashMap<>());
	    return Optional.ofNullable((U) daos.get(key).get(claz)).orElseThrow(()->{
	                String msg=String.format("dao[%s] has not register", claz.getName());
			DaoException execption = new DaoException(msg);
                    return execption;
                });
	}
	
	public void destoryByKey(K key) {
		if(!daos.containsKey(key)) {
			return ;
		}
		ConcurrentHashMap<Class<?>, T> daoMap = daos.get(key);
		daos.remove(key);
		daoMap.keySet().stream().forEach(claz->{
			destory(key, claz);
		});
	}
	
	public void destory(K key, Class<?> claz) {
        if(!daos.containsKey(key)) {
            return ;
        }
        if(!daos.get(key).containsKey(claz)) {
            return ;
        }
        daos.get(key).get(claz).destory();
        daos.get(key).remove(claz);
    }
}
