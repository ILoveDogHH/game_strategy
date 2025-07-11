package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EventPriority;
import jedi.game.event.SkillEvent;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

public class ShieldRandTick extends AbstractSkill {



    private int stack0;


    private int stack;

    public ShieldRandTick(CfgSkill cfgSkill) {
        super(cfgSkill);
        deduceParams(cfgSkill.getParams());
    }


    public void deduceParams(String params){
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.target = Integer.valueOf(param[3]);
        this.stack0 = Integer.valueOf(param[4]);
        this.expireTime = Integer.valueOf(param[5]);
        this.tick = Long.valueOf(param[6]);
        this.stack = Integer.valueOf(param[7]);
    }


    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if (!(target instanceof IBattleUnit)) return null;

        IBattleUnit battleUnit = (IBattleUnit) target;

        ActionEffect actionEffect = getActionEffect(target);
        battleUnit.addShield(stack0);
        actionEffect.setValue(stack0);

        setExpireTime(ctx.getCurrentTime() + expireTime); //设置buff的过期时间
        // 注册下一次 Tick 事件
        long nextTick = ctx.getCurrentTime() + getTick();
        ctx.scheduleEvent(new SkillEvent(nextTick, source, defender, this));
        return actionEffect;
    }


    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if (!(target instanceof IBattleUnit)) return null;

        IBattleUnit battleUnit = (IBattleUnit) target;

        ActionEffect actionEffect = getActionEffect(target);
        battleUnit.addShield(stack);
        actionEffect.setValue(stack);
        return actionEffect;
    }




}
