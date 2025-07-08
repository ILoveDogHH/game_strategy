package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.PositionType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//LangKey({1}{2}时，对{3}添加{4}层护盾)_LangArgs(前军;暴击;自身;4)
public class DynamicShield extends AbstractSkill {

    private String caster;
    private int target;
    private int trigger;
    private  int stack;    // 层数


    public DynamicShield(CfgSkill cfgSkill){
        super(cfgSkill.getSkill_id(), cfgSkill.getStar(), cfgSkill.getEffect());
        deduceParams(cfgSkill.getParam());
    }

    private void deduceParams(String params){
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.target = Integer.valueOf(param[2]);
        this.stack = Integer.valueOf(param[3]);
    }




    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target) {
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
    public SkillTriggerType getTriggerTypes() {
        return SkillTriggerType.fromValue(trigger);
    }
}
