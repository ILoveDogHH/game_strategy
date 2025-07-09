package jedi.game.skill.base;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractSkill implements ISkill {

    protected final int skillid;
    protected final int skill_star;
    protected final EffectType effectType;


    public AbstractSkill(CfgSkill cfgSkill) {
        this.skillid = cfgSkill.getSkill_id();
        skill_star = cfgSkill.getStar();
        this.effectType = EffectType.getEffectType(cfgSkill.getEffect());
    }


    public List<IEntity> getEffectIentity(IEntity source, Player target, TargetType effectTargetType){
        return TargetSelector.selectTargets(source, target, effectTargetType);
    }


    public ActionEffect getActionEffect(IEntity entity){
        return new ActionEffect(skillid, effectType, getTargetType(), entity.getUid(), entity.getPosition());
    }




    @Override
    public List<ActionEffect> apply(BattleContext ctx, IEntity source, Player target) {
        if(!getCaster().contains(source.getPosition())){
            return new ArrayList<>();
        }
        List<IEntity> entity = getEffectIentity(source, target, getTargetType());
        List<ActionEffect> actionEffects = new ArrayList<>();
        for(IEntity e : entity){
            ActionEffect actionEffect = execute(ctx, source, e);
            if(actionEffect != null){
                actionEffects.add(actionEffect);
            }
        }
        return actionEffects;
    }


    @Override
    public List<ActionEffect> tick(BattleContext ctx, IEntity source, Player target) {
        if(!getCaster().contains(source.getPosition())){
            return new ArrayList<>();
        }
        List<IEntity> entity = getEffectIentity(source, target, getTargetType());
        List<ActionEffect> actionEffects = new ArrayList<>();
        for(IEntity e : entity){
            ActionEffect actionEffect = tiggertick(ctx, source, e);
            if(actionEffect != null){
                actionEffects.add(actionEffect);
            }
        }
        return actionEffects;
    }


    public abstract ActionEffect execute(BattleContext ctx, IEntity source, IEntity target);

    public  ActionEffect tiggertick(BattleContext ctx, IEntity source, IEntity target){
        //错误兼容处理， 无间隔的不触发
        if(getTick() <= 0){
            return null;
        }
        return executeTick(ctx, source, target);
    }


    public abstract ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target);


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








}