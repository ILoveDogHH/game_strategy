package jedi.game.controller;

import jedi.game.battle.BattleTeam;
import jedi.game.enums.PositionType;
import jedi.game.exception.DaoException;
import jedi.game.player.Hero;
import jedi.game.player.Player;
import jedi.game.player.Soldier;
import jedi.game.servercfg.dao.CfgHeroDao;
import jedi.game.servercfg.dao.CfgSoldierDao;
import jedi.game.servercfg.enity.CfgHero;
import jedi.game.servercfg.enity.CfgSoldier;
import jedi.game.skill.base.ISkill;

import java.util.List;

public class BattleSystemDemo {




    public static void main(String[] args) {


    }




    public static Player createPlayer(int uid, String uname, int h_id) throws DaoException {
        CfgHero cfgHero = CfgHeroDao.getOne(h_id);
        CfgSoldier frontSoldier = CfgSoldierDao.getOne(cfgHero.getFront_soldier());
        CfgSoldier backSoldier = CfgSoldierDao.getOne(cfgHero.getBack_soldier());
        Player player = new Player(0, uname, cfgHero, frontSoldier, backSoldier);
        return player;
    }

}
