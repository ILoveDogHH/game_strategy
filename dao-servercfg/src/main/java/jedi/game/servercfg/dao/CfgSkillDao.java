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
import org.apache.ibatis.mapping.CacheBuilder;

import java.util.List;
import java.util.stream.Collectors;

@DatabaseTableAnnotation(name = "cfg_skill", mapperClass = CfgSkill.class, type = TableType.SERVERCFG, columns = "skill_id")
public class CfgSkillDao implements ServerCfgCacheDaoInterface {

    /**
     * disable constructor
     */
    private CfgSkillDao() {
    }

    protected static ICacheDatas<CfgSkill> cacheDatas;

    private static volatile boolean inited = false;

    public static void init(BasicCacheDatabaseDao dao) {
        if (inited) {
            return;
        }
        // TODO 这里锁的类在新建一个dao时需要修改
        synchronized (CfgSkill.class) {
            if (inited) {
                return;
            }
            cacheDatas = CacheDatas.newCacheDatas(dao);
            inited = true;
        }
    }


    public static List<CfgSkill> getSkills() throws DaoException {
        String sql = "select * from cfg_skill";
        return cacheDatas.getListCustomFromDao(CacheUtils.toKey(), sql).stream().collect(Collectors.toList());
    }


    public static CfgSkill getSkill(int skillid) throws DaoException {
        return cacheDatas.getOneFromDao(CacheUtils.toKey(skillid));
    }


}
