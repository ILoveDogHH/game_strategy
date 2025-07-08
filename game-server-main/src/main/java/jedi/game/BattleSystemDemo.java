package jedi.game;

import jedi.game.battle.BattleTeam;
import jedi.game.enums.PositionType;
import jedi.game.exception.DaoException;
import jedi.game.fatcory.SkillFatcory;
import jedi.game.player.Hero;
import jedi.game.player.Player;
import jedi.game.player.Soldier;
import jedi.game.servercfg.dao.CfgSkillDao;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.ISkill;

import java.util.ArrayList;
import java.util.List;

public class BattleSystemDemo {






    public static List<String> runBattle()throws DaoException {
        Player p1 = new Player("玩家A",
                new Soldier(PositionType.FRONT, "A前军", 100, 10, 0.5, 1.5, 0.1, 1, null, 1000),
                new Soldier(PositionType.BACK, "A后军", 100, 10, 0.25, 1.5, 0.1, 1, null,1000),
                new Hero(PositionType.HERO, "A武将", 100, 10, null, 1000)
        );
        Player p2 = new Player("玩家B",
                new Soldier(PositionType.FRONT, "B前军", 100, 10, 0.5, 1.5, 0.1, 1, null, 1000),
                new Soldier(PositionType.BACK, "B后军", 100, 10, 0.25, 1.5, 0.1, 1, null, 1000),
                new Hero(PositionType.HERO, "B武将", 100, 10, null, 1000)
        );



        // 绑定owner
        p1.frontSoldier.setOwner(p1);
        p1.backSoldier.setOwner(p1);
        p1.general.setOwner(p1);

        p2.frontSoldier.setOwner(p2);
        p2.backSoldier.setOwner(p2);
        p2.general.setOwner(p2);


        CfgSkill cfgSkill = CfgSkillDao.getSkill(10);
        ISkill iSkill = SkillFatcory.createSkill(cfgSkill);




        BattleTeam battle = new BattleTeam(p1, p2);
        List<String> actions = battle.run();
        return actions;
    }
}
