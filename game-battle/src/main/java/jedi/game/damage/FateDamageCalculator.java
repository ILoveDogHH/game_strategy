package jedi.game.damage;

import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;

/**
 * 天命/真实伤害计算器：
 * - 不参与暴击
 * - 不参与闪避
 * - 不受任何加成或减免影响
 * - 不受加速修正影响
 * - 直接返回固定伤害值（如技能传入）
 */
public class FateDamageCalculator implements IDamageCalculator {

    @Override
    public double calculate(BattleContext ctx, IEntity attacker, IEntity defender, int baseDamage) {
        // 直接返回，不参与任何修正
        return baseDamage;
    }

}