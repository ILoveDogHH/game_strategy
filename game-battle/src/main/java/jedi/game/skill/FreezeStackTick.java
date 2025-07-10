package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
// LangKey({1}{2}后，每{3}秒对{4}添加{5}层冰冻)_LangArgs(我方前军;战斗开始;1;敌方全体;6)
public class FreezeStackTick extends AbstractSkill {


    //冰冻层数
    public int freezeStack;


    public FreezeStackTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String[] param = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.tick = Long.parseLong(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.freezeStack = Integer.parseInt(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if (!(target instanceof IBattleUnit)) return null;

        IBattleUnit battleUnit = (IBattleUnit) target;

        battleUnit.addFreeze(freezeStack);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(freezeStack);
        return actionEffect;
    }
}
