package jedi.game.enums;

/**
 * TargetType 表示技能或攻击的目标选择类型。
 * 用于定义一个行为（如攻击、治疗）应作用于哪些单位。
 */
public enum TargetType {

    /**
     * SELF：自身作为目标。
     * 用于自我加血、加盾、增加状态等效果。
     */
    SELF(3001),

    /**
     * PRIORITY_FRONT：优先攻击前军，若前军已全部阵亡，则攻击后军。
     * 常用于普通攻击和默认技能逻辑。
     */
    PRIORITY_FRONT(3002),

    /**
     * FRONT：仅选择前军为目标。
     * 不考虑后军是否存在，仅锁定前排单位。
     */
    FRONT(3003),

    /**
     * BACK：仅选择后军为目标。
     * 通常用于远程技能、策略类技能。
     */
    BACK(3004),

    /**
     * ALL：对敌方所有单位（前军和后军）生效。
     * 多用于群体伤害或群体控制技能。
     */
    ALL(3005),

    /**
     * RANDOM：随机选择一个敌方单位作为目标。
     * 带有不确定性，适合某些特殊流派。
     */
    RANDOM(3006),

    /**
     * LOWEST_HP：选择当前血量百分比最低的单位为目标。
     * 常用于补刀、压低血线的技能。
     */
    LOWEST_HP(3007);

    public int value;

    TargetType(int value) { this.value = value; }

    public static TargetType fromValue(int value) {
        for (TargetType type : TargetType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No TargetType with value " + value);
    }
}
