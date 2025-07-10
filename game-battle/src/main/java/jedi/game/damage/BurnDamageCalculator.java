package jedi.game.damage;

import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;

public class BurnDamageCalculator implements IDamageCalculator{

    @Override
    public double calculate(BattleContext ctx, IEntity attacker, IEntity defender, int layerCount) {
        return Math.max(1, (int) 100);
    }



}
