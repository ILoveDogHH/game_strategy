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

public class ProjectileEvent extends AbstractEvent implements IUnitLinkedEvent {

    private final IEntity attacker;
    private final Player target;
    private final DamageType damageType;
    private final int baseDamage;

    public ProjectileEvent(long executeTime, EventPriority eventPriority, IEntity attacker, Player target,
                           int baseDamage, DamageType damageType) {
        super(executeTime, eventPriority);
        this.attacker = attacker;
        this.target = target;
        this.baseDamage = baseDamage;
        this.damageType = damageType;
    }

    @Override
    public void execute(BattleContext ctx) {
        Action action = DamageCalculator.calculateDamage(ctx,attacker, target, baseDamage, damageType, ActionType.PROJECTILE);
        ctx.addAction(action);
        //飞行道具行为
    }

    @Override
    public IEntity getEntity() {
        return attacker;
    }
}