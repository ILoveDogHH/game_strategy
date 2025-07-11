package jedi.game.servercfg.dao;

import jedi.game.cache.CacheDatas;
import jedi.game.cache.CacheUtils;
import jedi.game.cache.ICacheDatas;
import jedi.game.db.BasicCacheDatabaseDao;
import jedi.game.db.DatabaseTableAnnotation;
import jedi.game.db.TableType;
import jedi.game.exception.DaoException;
import jedi.game.servercfg.base.ServerCfgCacheDaoInterface;
import jedi.game.servercfg.enity.CfgHero;
import jedi.game.servercfg.enity.CfgSkill;

@DatabaseTableAnnotation(name = "cfg_hero", mapperClass = CfgHero.class, type = TableType.SERVERCFG, columns = "h_id")
public class CfgHeroDao  implements ServerCfgCacheDaoInterface {

    /**
     * disable constructor
     */
    private CfgHeroDao() {
    }

    protected static ICacheDatas<CfgHero> cacheDatas;

    private static volatile boolean inited = false;

    public static void init(BasicCacheDatabaseDao dao) {
        if (inited) {
            return;
        }
        // TODO 这里锁的类在新建一个dao时需要修改
        synchronized (CfgHeroDao.class) {
            if (inited) {
                return;
            }
            cacheDatas = CacheDatas.newCacheDatas(dao);
            inited = true;
        }
    }


    public static CfgHero getOne(int h_id) throws DaoException {
        return cacheDatas.getOne(CacheUtils.toKey(h_id));
    }
}
