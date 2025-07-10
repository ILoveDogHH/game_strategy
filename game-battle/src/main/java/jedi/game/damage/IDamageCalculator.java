package jedi.game.damage;

import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;

public interface IDamageCalculator {

    /**
     * 物理伤害的基础计算公式
     * - 攻击力为基础
     * - 考虑攻击加成、固守/破绽修正、
     * - 不处理暴击、闪避（上层控制） 战斗加速倍率
     */
    double calculate(BattleContext ctx, IEntity attacker, IEntity defender, int baseDamage);
}