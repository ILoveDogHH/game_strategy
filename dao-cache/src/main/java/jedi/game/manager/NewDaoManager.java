package jedi.game.manager;




import jedi.game.cache.CacheDaoInterface;
import jedi.game.cache.CacheDatasEventCenter;
import jedi.game.db.BasicCacheDatabaseDao;
import jedi.game.db.DatabaseAccesser;
import jedi.game.db.DatabaseTableAnnotation;
import jedi.game.utils.ClassSearcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public enum NewDaoManager {
	INSTANCE;

	/**
	 * 注册key-dao
	 * 
	 * @param classSearcher
	 * @param key
	 * @param database
	 * @param packageNames
	 */
	private void registerByClassSearcher(ClassSearcher classSearcher, Class<? extends CacheDaoInterface> dbDaoBase,
										 DatabaseAccesser da, String[] packageNames) {
		List<String> classNames = new ArrayList<>();
		// 获取包里所有的类
		for (String packagaName : packageNames) {
			classNames.addAll(classSearcher.getClassNames(packagaName, true));
		}
		for (String className : classNames) {
			try {
				Class<?> cls = Class.forName(className, true, classSearcher.getClassLoader());
				// 检测是否继承BasicUser的
				if (!dbDaoBase.isAssignableFrom(cls) || Modifier.isAbstract(cls.getModifiers())) {
					continue;
				}
				DatabaseTableAnnotation annotion = cls.getAnnotation(DatabaseTableAnnotation.class);
				if (annotion == null) {
					NullPointerException e = new NullPointerException(
							"init dao error: annotion is null, class=" + cls.getName());
					System.exit(-1);
				}
				BasicCacheDatabaseDao dao = new BasicCacheDatabaseDao(da, annotion.mapperClass(), annotion.type(), annotion.name(),
						annotion.columns());
				CacheDatasEventCenter.registerDao(dao.getTable(),dao);
				//这里为每一个dao创建了缓存
				Method method = cls.getMethod("init", BasicCacheDatabaseDao.class);
				method.invoke(null, dao);
			} catch (ClassNotFoundException | SecurityException | IllegalArgumentException | NoSuchMethodException
					| IllegalAccessException | InvocationTargetException e) {
				System.out.println("init dao error: " + e.getMessage());
			}
		}
	}

	/**
	 * 使用当前的classLoader注册package下的key-dao
	 * 
	 * @param key
	 * @param database
	 * @param packageNames
	 */
	public void register(Class<? extends CacheDaoInterface> dbDaoBase, DatabaseAccesser da, String[] packageNames) {
		registerByClassSearcher(new ClassSearcher(), dbDaoBase, da, packageNames);
	}


}
