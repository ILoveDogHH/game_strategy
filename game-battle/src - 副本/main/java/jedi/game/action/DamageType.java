package jedi.game.action;

// 枚举类，表示伤害类型及其特性
public enum DamageType {
    // 物理伤害，可暴击，可闪避
    PHYSICAL(true, true),
    // 大招伤害，不可暴击，不可闪避
    STRATEGY(false, false),
    // 燃烧伤害，不可暴击，不可闪避
    BURN(false, false),
    // 命运伤害，不可暴击，不可闪避
    FATE(false, false);

    // 是否可以暴击
    private final boolean canCrit;
    // 是否可以闪避
    private final boolean canDodge;

    // 构造方法，用于初始化伤害类型的特性
    DamageType(boolean canCrit, boolean canDodge) {
        this.canCrit = canCrit;
        this.canDodge = canDodge;
    }

    // 获取是否可以暴击
    public boolean canCrit() {
        return canCrit;
    }

    // 获取是否可以闪避
    public boolean canDodge() {
        return canDodge;
    }
}