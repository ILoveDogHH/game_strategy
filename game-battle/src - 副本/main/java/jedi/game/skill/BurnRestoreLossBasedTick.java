package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.event.SkillEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

public class BurnRestoreLossBasedTick extends AbstractSkill {


    public double percent;

    public int minStack;


    public BurnRestoreLossBasedTick(CfgSkill cfgSkill) {
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
        if(getTick() <= 0){
            return null;
        }
        // 注册下一次 Tick 事件
        long nextTick = ctx.getCurrentTime() + getTick();
        ctx.scheduleEvent(new SkillEvent(nextTick, source, defender, this));

        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {

        int lostHp = target.getLostHp(); // 计算损失的生命值

        int addStack = (int) Math.max(lostHp * percent, minStack);

        target.addBurn(addStack);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(addStack);
        return actionEffect;
    }
}
