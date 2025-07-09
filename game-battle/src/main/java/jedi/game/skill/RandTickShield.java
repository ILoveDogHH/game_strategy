package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class RandTickShield extends AbstractSkill {

    private String caster;

    private int trigger;

    //生效人
    private int target;

    private int stack0;

    //过期时间
    private int interval;

    //每次触发的时间间隔
    private double tick;

    private int stack;

    public RandTickShield(CfgSkill cfgSkill) {
        super(cfgSkill);
        deduceParams(cfgSkill.getParams());
    }


    private void deduceParams(String params){
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.target = Integer.valueOf(param[3]);
        this.stack0 = Integer.valueOf(param[4]);
        this.interval = Integer.valueOf(param[5]);
        this.tick = Double.valueOf(param[6]);
        this.stack = Integer.valueOf(param[7]);
    }


    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target) {
        ActionEffect actionEffect = getActionEffect(target);
        target.addShield(stack0);
        actionEffect.setValue(stack0);
        //会触发一个定时的buff
        return actionEffect;
    }


    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target) {
        ActionEffect actionEffect = getActionEffect(target);
        target.addShield(stack);
        actionEffect.setValue(stack);
        return actionEffect;
    }


    @Override
    public Set<Integer> getCaster() {
        return Arrays.stream(caster.split(",")).map(m -> Integer.valueOf(m)).collect(Collectors.toSet());
    }

    @Override
    public TargetType getTargetType() {
        return TargetType.fromValue(target);
    }
    @Override
    public SkillTriggerType getTriggerType() {
        return SkillTriggerType.fromValue(trigger);
    }
}
