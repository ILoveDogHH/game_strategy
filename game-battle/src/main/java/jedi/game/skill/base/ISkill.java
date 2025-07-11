package jedi.game.skill.base;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.player.Player;

import java.util.List;
import java.util.Set;

public interface ISkill {

    int getSkillid();

    EffectType getEffectType();

    TargetType  getTargetType();

    Set<Integer> getCaster();

    SkillTriggerType getTriggerType();  // 声明自己要监听哪些事件

    List<ActionEffect> apply(BattleContext ctx, IEntity source, Player target);

    List<ActionEffect> tick(BattleContext ctx, IEntity source, Player target);


    void setExpireTime(long expireTime);



    public long getExpireTime();

    public long getTick();

    boolean isExpired(long currentTime);


}
