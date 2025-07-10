package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
// LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层冰冻，且至少添加{6}层)_LangArgs(我方前军;战斗开始;1.5;敌方全体;0.6%;1)
public class FreezeRestoreLossBasedTick extends AbstractSkill {


    public double percent;

    public int minStack;


    public FreezeRestoreLossBasedTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String[] param = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.tick = Long.parseLong(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.percent = Double.parseDouble(param[4]);
        this.minStack = Integer.parseInt(param[5]);
    }




    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        int lostHp = target.getLostHp(); // 计算损失的生命值

        int addStack = (int) Math.max(lostHp * percent, minStack); // 计算添加的冰冻层数，至少为minStack层

        target.addFreeze(addStack);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(addStack);
        return actionEffect;
    }


}
