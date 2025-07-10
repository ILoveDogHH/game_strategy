package jedi.game.damage;

import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;

/**
 * 计策（技能）伤害的基础计算方式。
 * - 不考虑暴击、闪避（上层控制）
 * - 基础值通常来自技能配置（如 skill.damage）
 * - 参与：计策增伤百分比、固定增伤、战斗加速系数、对方减伤
 */
public class StrategyDamageCalculator implements IDamageCalculator {


    @Override
    public double calculate(BattleContext ctx, IEntity attacker, IEntity defender, int baseDamage) {
        double damage = baseDamage;
        return damage;
    }
}