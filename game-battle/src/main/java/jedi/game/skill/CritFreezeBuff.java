package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.skill.base.AbstractSkill;

import java.util.EnumSet;

/**
 * 每次暴击时，对敌人添加4层冰冻。
 * 实现 ICritTriggerBuff 接口，响应暴击事件。
 */
public class CritFreezeBuff extends AbstractSkill {



    private final int stack;        // 添加层数，默认4



    public CritFreezeBuff(int skillid, String params) {
        super(skillid, EffectType.FREEZE, TargetType.PRIORITY_FRONT, EnumSet.of(SkillTriggerType.ON_CRIT));
        this.stack = Integer.parseInt(params);
    }


    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target) {
        ActionEffect actionEffect = getActionEffect(target);
        target.addFreeze(stack);
        actionEffect.setValue(stack);
        return actionEffect;
    }

}
