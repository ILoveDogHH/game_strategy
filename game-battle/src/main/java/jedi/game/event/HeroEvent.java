package jedi.game.event;

import jedi.game.action.Action;
import jedi.game.damage.DamageCalculator;
import jedi.game.enums.DamageType;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.EventPriority;
import jedi.game.event.api.AbstractEvent;
import jedi.game.event.api.IUnitLinkedEvent;
import jedi.game.player.IEntity;
import jedi.game.player.IHeroUnit;
import jedi.game.player.Player;
import jedi.game.skill.base.TargetSelector;

import java.util.List;

/**
 * 武将的气力恢复
 */
public class HeroEvent extends AbstractEvent implements IUnitLinkedEvent {

    private final IEntity attacker;
    private final Player target;

    public HeroEvent(long executeTime, IEntity attacker, Player target) {
        super(executeTime, EventPriority.ULTIMATE_Skill);
        this.attacker = attacker;
        this.target = target;
    }


    @Override
    public void execute(BattleContext context) {
        if (!(attacker instanceof IHeroUnit)) return;

        IHeroUnit hero = (IHeroUnit) attacker;

        // 2️⃣ 判断是否可释放技能
        if (!hero.canReleaseSkill()) {
            // 2.1 当前不能释放技能 → 恢复气力并结束
            hero.recoverEnergyAndReschedule(context, hero, target, hero.getenergyRecoverPerSecond());
        } else {
            // 2.2 可以释放技能 → 执行大招

            // 清空当前气力
            hero.setCurrentEnergy(0);

            // 计算伤害
            Action action = DamageCalculator.calculateDamage(context,
                    attacker, target, 0, DamageType.STRATEGY, ActionType.SKILL);
            context.addAction(action);

        }

        // 3️⃣ 安排下一次气力恢复事件（无论是否释放了技能）
        long nextTime = context.getCurrentTime() + 1000;
        context.scheduleEvent(new HeroEvent(nextTime, attacker, target));
    }


    @Override
    public IEntity getEntity() {
        return attacker;
    }
}
