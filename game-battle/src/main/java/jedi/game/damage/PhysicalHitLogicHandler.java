package jedi.game.damage;

import jedi.game.enums.DamageType;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;
import jedi.game.utils.Random;

public class PhysicalHitLogicHandler implements IHitLogicHandler{
    @Override
    public boolean isCrit(IEntity attacker, DamageType type) {
        return Random.isRand(attacker.getCritRate());
    }

    @Override
    public boolean isDodged(IEntity defender, DamageType type) {
        if (!(defender instanceof IBattleUnit)){
            return false;
        }
        IBattleUnit defenderc = (IBattleUnit) defender;

        return Random.isRand(defenderc.getDodgeRate());
    }

    @Override
    public double getCritMultiplier(IEntity attacker, DamageType type) {
        return 0;
    }
}
