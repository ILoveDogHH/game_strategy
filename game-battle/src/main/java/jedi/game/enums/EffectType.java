package jedi.game.enums;

/**
 * 表示技能、Buff、物品等附带的效果类型。
 * 每种效果会对目标单位产生特定的状态改变或行为影响。
 */
public enum EffectType {

    DAMAGE(101),         //目标造成物理伤害

    SHIELD(102),          // 为目标添加一个护盾值，抵挡部分伤害（不影响治疗）

    HEAL(103),            // 恢复目标的生命值，可受治疗加成影响

    ENERGY_GAIN(104),     // 增加目标的能量值（气力），常用于能量技能、暴击回能等机制

    STUN(105),            // 使目标进入眩晕状态，一段时间内无法行动或释放技能

    FREEZE(106),          // 使目标被冻结，一段时间内无法行动，并可能受到额外伤害加成

    POISON(107),          // 给目标附加中毒状态，每回合或每秒造成持续伤害，可能叠加

    BURN(108),            // 给目标附加燃烧状态，类似中毒，但可有不同触发机制或伤害类型

    CLEANSE(109),         // 驱散目标身上的负面状态，例如中毒、眩晕、沉默等

    SILENCE(110),         // 目标进入沉默状态，无法释放主动技能（不影响普通攻击）

    COOLDOWN_RESET(111),  // 重置目标单位一个或多个技能的冷却时间，立即可用

    VULNERABLE(112);     // 易伤状态，使目标受到的伤害提升（可叠加或持续一定时间）


    public int value;

    EffectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EffectType getEffectType(int value) {
        for (EffectType type : EffectType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No getEffectType with value " + value);
    }

}