package jedi.game.cache;


import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 缓存数据, 包括如何从db中读取数据并缓存
 * 
 * @author @cc
 *
 */
public class CacheData<T> {
	/**
	 * cache数据
	 */
	private final ConcurrentLinkedQueue<T> datas;

	/**
	 * 空数据专用
	 */
	public CacheData() {
		this.datas = new ConcurrentLinkedQueue<>();
	}

	/**
	 * 给key=>多个value使用
	 * 
	 * @param datas
	 */
	public CacheData(Collection<T> datas) {
		this.datas = new ConcurrentLinkedQueue<>(datas);
	}

	/**
	 * 给key=>value一一对应使用
	 * 
	 * @param data
	 */
	public CacheData(T data) {
		this.datas = new ConcurrentLinkedQueue<>();
		this.datas.add(data);
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public ConcurrentLinkedQueue<T> getList() {
		return datas;
	}

	/**
	 * 有可能为空的
	 * 
	 * @return
	 */
	public Optional<T> getOne() {
		return Optional.ofNullable(datas.peek());
	}
}
