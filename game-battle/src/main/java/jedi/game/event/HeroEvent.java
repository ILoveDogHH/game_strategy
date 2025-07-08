package jedi.game.event;

import jedi.game.action.Action;
import jedi.game.action.DamageCalculator;
import jedi.game.action.DamageType;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.EventPriority;
import jedi.game.event.api.AbstractEvent;
import jedi.game.event.api.IUnitLinkedEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;

/**
 * 武将的气力恢复
 */
public class HeroEvent extends AbstractEvent implements IUnitLinkedEvent {

    private final IEntity attacker;
    private final Player target;

    public HeroEvent(long executeTime, EventPriority eventPriority, IEntity attacker, Player target) {
        super(executeTime, eventPriority);
        this.attacker = attacker;
        this.target = target;
    }



    @Override
    public void execute(BattleContext context) {

        // 2️⃣ 判断是否可释放技能
        if (!attacker.canReleaseSkill()) {
            // 2.1 当前不能释放技能 → 恢复气力并结束
            attacker.recoverEnergyAndReschedule(context, attacker, target, attacker.getenergyRecoverPerSecond());
        } else {
            // 2.2 可以释放技能 → 执行大招

            // 清空当前气力
            attacker.setCurrentEnergy(0);

            // 固定计策伤害（可改为配置）
            int skillDamage = 50;

            // 计算伤害
            Action action = DamageCalculator.calculateDamage(context,
                    attacker, target, skillDamage, DamageType.STRATEGY, ActionType.SKILL);
            context.addAction(action);

        }

        // 3️⃣ 安排下一次气力恢复事件（无论是否释放了技能）
        long nextTime = context.getCurrentTime() + 1000;
        context.scheduleEvent(new HeroEvent(nextTime, EventPriority.SKILL, attacker, target));
    }


    @Override
    public IEntity getEntity() {
        return attacker;
    }
}
