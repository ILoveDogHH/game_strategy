package jedi.game.enums;

import jedi.game.damage.*;


/**
 * 枚举类：表示不同类型的伤害及其计算与命中处理逻辑。
 * 每种伤害类型关联对应的伤害计算器和命中逻辑处理器。
 */
public enum DamageType {

    /**
     * 物理伤害：
     * - 例如普攻造成的伤害
     */
    PHYSICAL(new PhysicalDamageCalculator(), new PhysicalHitLogicHandler()),

    /**
     * 大招伤害（谋略伤害）：
     * - 例如释放主动技能造成的固定伤害
     */
    STRATEGY(new StrategyDamageCalculator(), new StrategyHitLogicHandler()),

    /**
     * 燃烧伤害（持续伤害）：
     * - 每秒伤害型 Buff 的伤害来源
     */
    BURN(new BurnDamageCalculator(), new BurnHitLogicHandler()),

    /**
     * 真实伤害：
     * - 例如穿透类技能或特效伤害
     */
    FATE(new FateDamageCalculator(), new FateHitLogicHandler());

    /**
     * 伤害计算器接口，实现具体伤害计算逻辑
     */
    public final IDamageCalculator damageCalculator;

    /**
     * 命中逻辑处理器接口，实现是否命中、是否暴击等判定
     */
    public final IHitLogicHandler iHitLogicHandler;

    /**
     * 构造函数：为每个伤害类型绑定其计算器与命中处理逻辑
     * @param damageCalculator 伤害计算器
     * @param iHitLogicHandler 命中逻辑处理器
     */
    DamageType(IDamageCalculator damageCalculator, IHitLogicHandler iHitLogicHandler) {
        this.damageCalculator = damageCalculator;
        this.iHitLogicHandler = iHitLogicHandler;
    }
}
