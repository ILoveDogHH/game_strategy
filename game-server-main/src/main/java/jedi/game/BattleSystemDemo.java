package jedi.game;

import jedi.game.battle.BattleTeam;
import jedi.game.enums.PositionType;
import jedi.game.player.Hero;
import jedi.game.player.Player;
import jedi.game.player.Soldier;
import jedi.game.skill.base.ISkill;

import java.util.ArrayList;
import java.util.List;

public class BattleSystemDemo {




    public static void main(String[] args) {
        runBattle(new ArrayList<>(), new ArrayList<>());
    }



    public static List<String> runBattle(List<Integer> atkBuff, List<Integer> defBuff) {
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


        for(Integer i : atkBuff){
            ISkill skill = null;
            if(i == 20001){
                skill = new VitalStrikeBuff(20001, "200");
            }
            if(i == 30001){
                skill = new CritFreezeBuff(30001, "4");
            }

            if(i == 40001){
                skill = new RecoverLostHpBuff(40001, "1000,0.1");
            }
            p1.frontSoldier.getSkillManager().addSkill(skill);
        }


        for(Integer i : defBuff){
            ISkill skill = null;
            if(i == 20001){
                skill = new VitalStrikeBuff(20001, "200");
            }
            if(i == 30001){
                skill = new CritFreezeBuff(30001, "4");
            }

            if(i == 40001){
                skill = new RecoverLostHpBuff(40001, "1000,0.1");
            }
            p2.frontSoldier.getSkillManager().addSkill(skill);
        }


        BattleTeam battle = new BattleTeam(p1, p2);
        List<String> actions = battle.run();
        return actions;
    }
}
