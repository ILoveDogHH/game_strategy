package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
// LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层冰冻，且至少添加{6}层)_LangArgs(我方前军;战斗开始;1.5;敌方全体;0.6%;1)
public class FreezeRestoreLossBasedTick extends AbstractSkill {


    //施法者位置
    public String caster;

    //触发类型
    public int trigger;

    public int tick;

    //目标类型
    public int target;





    public FreezeRestoreLossBasedTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {

    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }
}
