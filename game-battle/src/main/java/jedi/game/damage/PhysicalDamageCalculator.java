package jedi.game.damage;

import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;

public class PhysicalDamageCalculator implements IDamageCalculator {

    public PhysicalDamageCalculator(){

    }

    @Override
    public double calculate(BattleContext ctx, IEntity attacker, IEntity defender, int baseDamage) {
        double damage = baseDamage;
        // 1️⃣ 计算攻击者的物理攻击力


        return Math.max(1, 100);  // 保底伤害为1
    }
}