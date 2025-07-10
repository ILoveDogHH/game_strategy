package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
import jedi.game.utils.Random;

// LangKey(当{1}{2}时，{3}概率对{4}添加{5}层燃烧)_LangArgs(我方后军;暴击;100%;敌方后军;5)
public class BurnRandDynamic extends AbstractSkill {

    private  int stack;    // 层数
    private double rate; // 触发率






    public BurnRandDynamic(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    public void deduceParams(String params){
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.rate = Double.parseDouble(param[2]);
        this.target = Integer.valueOf(param[3]);
        this.stack = Integer.valueOf(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {

        if (!(target instanceof IBattleUnit)) return null;

        IBattleUnit battleUnit = (IBattleUnit) target;

        if(!Random.isRand(rate)){
            return null;
        }
        ActionEffect actionEffect = getActionEffect(target);
        battleUnit.addBurn(stack);
        actionEffect.setValue(stack);
        return actionEffect;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }
}
