package jedi.game;


import jedi.game.databases.DBManager;
import jedi.game.exception.DaoException;
import jedi.game.logger.JLogger;
import jedi.game.servercfg.dao.CfgSkillDao;
import jedi.game.servercfg.enity.CfgSkill;

import java.util.List;

public class Main {




    public static void main(String[] args) throws DaoException, ClassNotFoundException {
        JLogger.setDebugLoggerLevel("DEBUG");

        DBManager.instance.initialize();



        BattleSystemDemo.runBattle();


    }

}