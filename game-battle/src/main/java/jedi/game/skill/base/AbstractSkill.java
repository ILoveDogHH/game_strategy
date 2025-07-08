package jedi.game.skill.base;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractSkill implements ISkill {

    protected final int skillid;
    protected final EffectType effectType;
    protected final TargetType effectTargetType;
    protected final Set<SkillTriggerType> skillTriggerTypes;

    public AbstractSkill(int skillid, EffectType effectType, TargetType effectTargetType, Set<SkillTriggerType> skillTriggerTypes) {
        this.skillid = skillid;
        this.effectType = effectType;
        this.effectTargetType = effectTargetType;
        this.skillTriggerTypes = skillTriggerTypes;
    }


    public List<IEntity> getEffectIentity(IEntity source, Player target, TargetType effectTargetType){
        return TargetSelector.selectTargets(source, target, effectTargetType);
    }


    public ActionEffect getActionEffect(IEntity entity){
        return new ActionEffect(skillid, effectType, effectTargetType, entity.getUid(), entity.getPosition());
    }




    @Override
    public List<ActionEffect> apply(BattleContext ctx, IEntity source, Player target) {
        List<IEntity> entity = getEffectIentity(source, target, getEffectTargetType());
        List<ActionEffect> actionEffects = new ArrayList<>();
        for(IEntity e : entity){
            ActionEffect actionEffect = execute(ctx, source, e);
            if(actionEffect != null){
                actionEffects.add(actionEffect);
            }
        }
        return actionEffects;
    }


    public abstract ActionEffect execute(BattleContext ctx, IEntity source, IEntity target);




    @Override
    public Set<SkillTriggerType> getTriggerTypes() {
        return skillTriggerTypes;
    }



    @Override
    public long getInterval() {
        return -1;
    }


    @Override
    public int getSkillid() {
        return skillid;
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public TargetType getEffectTargetType() {
        return effectTargetType;
    }






}