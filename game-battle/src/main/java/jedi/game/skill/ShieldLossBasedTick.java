package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EventPriority;
import jedi.game.event.SkillEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
//LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层护盾，且至少添加{6}层)_LangArgs(我方任意单位;战斗开始;1.5;自身;0.5%;1)
public class ShieldLossBasedTick extends AbstractSkill {


    public long tick;


    //每次添加的护盾层数
    public double percentShield;

    //至少添加的护盾层数
    public int minShield;


    public ShieldLossBasedTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }


    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.tick = Long.valueOf(param[2]);
        this.target = Integer.valueOf(param[3]);
        this.percentShield = Double.parseDouble(param[4]);
        this.minShield = Integer.valueOf(param[5]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        ActionEffect actionEffect = getActionEffect(target);

        int lostHp = target.getLostHp(); // 计算损失的生命值

        int stack0 = (int) Math.max(lostHp * percentShield, minShield); // 计算护盾层数，至少添加 minShield 层

        target.addShield(stack0);
        actionEffect.setValue(stack0);

        // 注册下一次 Tick 事件
        long nextTick = ctx.getCurrentTime() + getTick();
        ctx.scheduleEvent(new SkillEvent(nextTick, source, defender, this));
        return actionEffect;
    }


}
