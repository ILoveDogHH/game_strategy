package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
import jedi.game.utils.Random;

// LangKey(当{1}{2}时，{3}概率对{4}添加{5）——（6）秒眩晕)_LangArgs(我方武将;释放大招;100%;敌方全体;0.2;0.5)
public class StunDynamicRand extends AbstractSkill {



    public int rate; //触发概率，百分比


    public int stunDuration1; //眩晕持续时间，单位秒

    public int stunDuration2;



    public StunDynamicRand(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String[] param = params.split(",");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.rate = Integer.parseInt(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.stunDuration1 = Integer.parseInt(param[4]);
        this.stunDuration2 = Integer.parseInt(param[5]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if(!Random.isRand(rate)){
            return null;
        }
        int stun = Random.randInt(stunDuration1, stunDuration2);
        target.setStunTs(ctx.getCurrentTime() + stun);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(stun);
        return actionEffect;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }
}
