package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.skill.base.AbstractSkill;

import java.util.EnumSet;


// 每次暴击额外增加敌人当前生命值2%的伤害
public class VitalStrikeBuff extends AbstractSkill {


    private final int param;

    public VitalStrikeBuff(int skillid, String params) {
        super(skillid, EffectType.DAMAGE, TargetType.PRIORITY_FRONT, EnumSet.of(SkillTriggerType.ON_CRIT));
        this.param = Integer.parseInt(params);
    }



    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target) {
        ActionEffect actionEffect = getActionEffect(target);
        double percent = param / 10000.0;
        double bonus = target.getCurrentHp() * percent;
        actionEffect.setValue(bonus);
        return actionEffect;
    }


}
