package jedi.game.damage;

import jedi.game.enums.DamageType;
import jedi.game.player.IEntity;

public class BurnHitLogicHandler implements IHitLogicHandler{
    @Override
    public boolean isCrit(IEntity attacker, DamageType type) {
        return false;
    }

    @Override
    public boolean isDodged(IEntity defender, DamageType type) {
        return false;
    }

    @Override
    public double getCritMultiplier(IEntity attacker, DamageType type) {
        return 0;
    }
}
