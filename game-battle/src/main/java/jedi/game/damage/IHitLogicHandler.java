package jedi.game.damage;

import jedi.game.enums.DamageType;
import jedi.game.player.IEntity;

public interface IHitLogicHandler {
    boolean isCrit(IEntity attacker, DamageType type);
    boolean isDodged(IEntity defender, DamageType type);
    double getCritMultiplier(IEntity attacker, DamageType type);
}