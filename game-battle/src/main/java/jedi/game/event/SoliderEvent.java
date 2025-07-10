package jedi.game.event;

import jedi.game.action.Action;
import jedi.game.action.ActionDetail;
import jedi.game.enums.DamageType;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.EventPriority;
import jedi.game.event.api.AbstractEvent;
import jedi.game.event.api.IUnitLinkedEvent;
import jedi.game.player.*;
import jedi.game.skill.base.TargetSelector;

import java.util.List;

public class SoliderEvent extends AbstractEvent implements IUnitLinkedEvent {
    private final IEntity attacker;
    private final Player target;

    public SoliderEvent(long executeTime, EventPriority eventPriority, IEntity attacker, Player target) {
        super(executeTime, eventPriority);
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void execute(BattleContext ctx) {
        if (!(attacker instanceof IBattleUnit)) return;

        IBattleUnit battleUnit = (IBattleUnit) attacker;


        Player enemy = target;
        List<IEntity> targets = TargetSelector.selectTargets(attacker, target, battleUnit.getTargeType());

        if (targets.isEmpty()|| !battleUnit.isAlive()) return; // 没目标或攻击者死了，跳过
        Action action = new Action(ctx.getCurrentTime());


        for(IEntity entity : targets){
            ActionDetail actionDetail = new ActionDetail(attacker, entity, ActionType.ATTACK);
            actionDetail.scheduledHitTime = battleUnit.getProjectileTs();
            action.addActionDetail(actionDetail);
        }
        ctx.addAction(action);
        //攻击触发飞行
        ctx.scheduleEvent(new ProjectileEvent(ctx.getCurrentTime()  + battleUnit.getProjectileTs(), EventPriority.PROJECTILE, attacker, target, 10, DamageType.PHYSICAL));
        // 安排下一次攻击事件
        long interval = (long)(1000 / (battleUnit.getAttackSpeed() * ctx.getSpeedCoefficient()));
        ctx.scheduleEvent(new SoliderEvent(ctx.getCurrentTime() + interval,EventPriority.ATTACK, attacker, enemy));
    }


    @Override
    public IEntity getEntity() {
        return attacker;
    }
}
