package jedi.game.enums;

/**
 * ActionType 表示战斗中某个行为的类型。
 * 用于区分普通攻击、技能释放、Buff触发等不同来源的战斗事件。
 */
public enum ActionType {

    /**
     * ATTACK：普通攻击。
     * 包含基础攻击动作，由攻击力计算伤害。
     */
    ATTACK("攻击"),

    /**
     * SKILL：释放技能。
     * 包含主动技能、被动触发技能等，用于技能发动时的表现。
     */
    SKILL("释放技能"),

    /**
     * PROJECTILE：飞行道具。
     * 表示攻击或技能释放出的投射物，例如：箭矢、火球、暗器等。
     * 与命中延迟相关，可用于弹道表现。
     */
    PROJECTILE("飞行道具"),

    /**
     * ENERGY_RECOVER：气力恢复。
     * 表示单位通过某些方式恢复气力（如自然恢复、暴击回能）。
     */
    ENERGY_RECOVER("气力恢复"),

    /**
     * BUFF_TICK：Buff 定时触发。
     * 某些 Buff 在固定时间间隔触发（如持续掉血、持续加盾）。
     */
    BUFF_TICK("buff定时触发"),

    /**
     * ENVIRONMENT：环境伤害。
     * 表示因地形、机关、天气等环境造成的伤害，例如：火圈、毒雾、陷阱。
     */
    ENVIRONMENT(""),

    /**
     * OTHER：其他类型行为。
     * 不属于以上任何类型的行为可归类为 OTHER，作为兜底处理。
     */
    OTHER("");

    /** 中文名或行为描述，可用于日志、前端展示等 */
    public final String name;

    ActionType(String name){
        this.name = name;
    }
}
