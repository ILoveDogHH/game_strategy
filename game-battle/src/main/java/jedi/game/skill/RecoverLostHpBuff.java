package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EffectType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.enums.TargetType;
import jedi.game.player.IEntity;
import jedi.game.skill.base.AbstractSkill;

import java.util.EnumSet;
//每过1秒，回复已损失生命值1%的生命值。
public class RecoverLostHpBuff extends AbstractSkill{

    private final double percent; // 每次恢复损失生命值的百分比
    private final long interval;  // 间隔时间（毫秒）


    public RecoverLostHpBuff(int buffid, String params) {
        super(buffid, EffectType.HEAL, TargetType.SELF, EnumSet.of(SkillTriggerType.TICKABLE));
        String[] param = params.split(",");
        interval = Integer.parseInt(param[0]);
        percent = Double.parseDouble(param[1]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target) {
        ActionEffect actionEffect = getActionEffect(target);
        int maxHp = target.getMaxHp();
        int curHp = target.getCurrentHp();
        int lostHp = maxHp - curHp;

        if (lostHp <= 0) return null;

        int healAmount = (int)(lostHp * percent);
        if(healAmount <= 0){
            return null;
        }
        target.heal(healAmount);
        actionEffect.setValue(healAmount);
        return actionEffect;
    }


    @Override
    public long getInterval() {
        return interval;
    }
}
