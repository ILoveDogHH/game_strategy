package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
import jedi.game.utils.Random;

//LangKey(当{1}{2}时，{3}概率对{4}添加{5}层冰冻)_LangArgs(我方任意单位;暴击;100%;敌方前军;4)
public class FreezeStackDynamic extends AbstractSkill {

    //触发概率
    public int rate;

    public int stack;


    public FreezeStackDynamic(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String[] param = params.split(",");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.rate = Integer.parseInt(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.stack = Integer.parseInt(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if(!Random.isRand(rate)){
            return null;
        }
        target.addFreeze(stack);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(stack);
        return actionEffect;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }
}
