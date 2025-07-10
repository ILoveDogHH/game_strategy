package jedi.game.enums;

import jedi.game.damage.*;

// 枚举类，表示伤害类型及其特性
public enum DamageType {
    // 物理伤害，可暴击，可闪避
    PHYSICAL( new PhysicalDamageCalculator(), new PhysicalHitLogicHandler()),
    // 大招伤害，不可暴击，不可闪避
    STRATEGY( new StrategyDamageCalculator(), new StrategyHitLogicHandler()),
    // 燃烧伤害，不可暴击，不可闪避
    BURN(new BurnDamageCalculator(), new BurnHitLogicHandler()),
    // 真实伤害，不可暴击，不可闪避
    FATE(new FateDamageCalculator(), new FateHitLogicHandler()),

    ;


    public final IDamageCalculator damageCalculator;

    public final IHitLogicHandler iHitLogicHandler;

    // 构造方法，用于初始化伤害类型的特性
    DamageType( IDamageCalculator damageCalculator, IHitLogicHandler iHitLogicHandler) {
        this.damageCalculator = damageCalculator;
        this.iHitLogicHandler = iHitLogicHandler;
    }

}