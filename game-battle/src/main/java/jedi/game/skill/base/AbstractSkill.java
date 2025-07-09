package jedi.game.skill.base;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;

import java.util.*;
import java.util.stream.Collectors;
public abstract class AbstractSkill implements ISkill {

    protected final int skillid;
    protected final int skill_star;
    //过期时间
    public long expireTime = -1;

    //施法者位置
    public String caster;

    //触发类型
    public int trigger;

    //目标类型
    public int target;

    //伤害类型
    protected final EffectType effectType;


    public AbstractSkill(CfgSkill cfgSkill) {
        this.skillid = cfgSkill.getSkill_id();
        skill_star = cfgSkill.getStar();
        this.effectType = EffectType.getEffectType(cfgSkill.getEffect());
        deduceParams(cfgSkill.getParams());
    }

    public abstract void deduceParams(String params);


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
            ActionEffect actionEffect = execute(ctx, source, e, target);
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
            ActionEffect actionEffect = tiggertick(ctx, source, e, target);
            if(actionEffect != null){
                actionEffects.add(actionEffect);
            }
        }
        return actionEffects;
    }


    public abstract ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender);

    public  ActionEffect tiggertick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        //错误兼容处理， 无间隔的不触发
        if(getTick() <= 0){
            return null;
        }
        return executeTick(ctx, source, target, defender);
    }


    public abstract ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender);



    @Override
    public int getSkillid() {
        return skillid;
    }

    @Override
    public EffectType getEffectType() {
        return effectType;
    }

    @Override
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public long getExpireTime() {
        return expireTime;
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

    @Override
    public boolean isExpired(long currentTime) {
        if(expireTime <= 0){
            return false; // 没有设置过期时间，视为未过期
        }
        if(currentTime >= expireTime){
            return true;
        }
        return false;
    }
}