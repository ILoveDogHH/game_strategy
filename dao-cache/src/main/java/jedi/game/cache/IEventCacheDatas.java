package jedi.game.cache;

import java.util.Collection;

/**
 *每一个实现类 表明这个 是某个数据库中表的缓存
 * 可以被缓存中心监听
 * 
 * */
public interface IEventCacheDatas {
	/**
	 * 获取表名
	 * 
	 * @return
	 */
	String getTableName();

	/**
	 * 获取table类型
	 * 
	 * @return
	 */
	int getTableType();

	/**
	 * 失效一个key</br>
	 * 小于key.length的所有; 大于等于key.length时, 判断前n个一致
	 * 
	 * 
	 * @param key
	 */
	void invalidateKey(Object[] key);

	/**
	 * 失效一组keys</br>
	 * 对于每个key: 小于key.length的所有; 大于等于key.length时, 判断前n个一致
	 * 
	 * @param keys
	 * @return
	 */
	void invalidateKeys(Collection<Object[]> keys);

	/**
	 * 失效所有的key
	 */
	void invalidateAll();

}
