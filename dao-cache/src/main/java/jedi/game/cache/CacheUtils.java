package jedi.game.cache;

import org.apache.commons.lang3.ArrayUtils;

public class CacheUtils {
	/**
	 * 在缓存中读取到这个key, 会直接失效全部
	 */
	public static final String INVALIDATE_KEYS_ALL = "@cache-invalidate-keys-all";

	/**
	 * 识别到该key, 在update/select时会自动替换为对应的keys
	 */
	public static final Object TO_REPLACE_KEY = new Object();
	/**
	 * 将Object转为String[]类型
	 * 
	 * @param keys
	 * @return
	 */
	public static Object[] toKey(Object... keys) {
		return ArrayUtils.toArray(keys);
	}


	/**
	 * 检测array1前length是不是和array0相等</br>
	 * array1.length<length || array0.length<length 返回false</br>
	 * 其中有一方是null, 返回false
	 * 
	 * @param array1
	 * @param array2
	 * @param length
	 * @return
	 */
	public static boolean arrayEquals(Object[] array1, Object[] array2, int length) {
		if (array1 == null || array2 == null) {
			return false;
		}
		if (array1.length < length || array2.length < length) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			Object o1 = array1[i];
			Object o2 = array2[i];
			if (!(o1 == null ? o2 == null : o1.equals(o2))) {
				return false;
			}
		}

		return true;
	}

	public static class TableType {
		public static final int USER = 1;
		public static final int STATE = 11;
		public static final int MAP = 21;
		public static final int EXTRA_CENTER = 31;
		public static final int BATTLE = 41;

	}
}
