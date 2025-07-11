package jedi.game.servercfg.dao;

import jedi.game.cache.CacheDatas;
import jedi.game.cache.CacheUtils;
import jedi.game.cache.ICacheDatas;
import jedi.game.db.BasicCacheDatabaseDao;
import jedi.game.db.DatabaseTableAnnotation;
import jedi.game.db.TableType;
import jedi.game.exception.DaoException;
import jedi.game.servercfg.base.ServerCfgCacheDaoInterface;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.servercfg.enity.CfgSoldier;

@DatabaseTableAnnotation(name = "cfg_soldier", mapperClass = CfgSkill.class, type = TableType.SERVERCFG, columns = "soldier_id")
public class CfgSoldierDao implements ServerCfgCacheDaoInterface {

    /**
     * disable constructor
     */
    private CfgSoldierDao() {
    }

    protected static ICacheDatas<CfgSoldier> cacheDatas;

    private static volatile boolean inited = false;

    public static void init(BasicCacheDatabaseDao dao) {
        if (inited) {
            return;
        }
        // TODO 这里锁的类在新建一个dao时需要修改
        synchronized (CfgSoldierDao.class) {
            if (inited) {
                return;
            }
            cacheDatas = CacheDatas.newCacheDatas(dao);
            inited = true;
        }
    }


    public static CfgSoldier getOne(int soldier_id) throws DaoException {
        return cacheDatas.getOne(CacheUtils.toKey(soldier_id));
    }


}
