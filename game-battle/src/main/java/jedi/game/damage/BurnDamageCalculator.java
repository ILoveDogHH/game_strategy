package jedi.game.damage;

import jedi.game.battle.BattleContext;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;

public class BurnDamageCalculator implements IDamageCalculator{

    @Override
    public double calculate(BattleContext ctx, IEntity attacker, IEntity defender, int baseDamage) {
        double damage = baseDamage;
        // 1️⃣ 计算攻击者的燃烧伤害
        if(damage <=0){
            if (attacker instanceof IBattleUnit) {
                IBattleUnit unit = (IBattleUnit) attacker;
                damage = unit.getBurn();
            }
        }
        return damage;
    }



}
