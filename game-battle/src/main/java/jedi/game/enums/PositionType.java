package jedi.game.enums;

/**
 * PositionType 表示单位在战斗中的位置类型。
 * 可用于区分攻击目标、技能生效范围、单位类型等。
 */
public enum PositionType {

    /**
     * 前军（FRONT）：
     * 位于战斗阵型前排，通常承担坦克、吸收伤害的角色。
     */
    FRONT(1),

    /**
     * 后军（BACK）：
     * 位于后排，通常用于输出或辅助，受到攻击概率较低。
     */
    BACK(2),

    /**
     * 英雄（HERO）：
     * 特殊单位类型，代表主控角色，不属于前军或后军。
     */
    HERO(3);

    /** 数值标识，用于数据库或传输协议中的映射 */
    private final int value;

    PositionType(int value) {
        this.value = value;
    }

    /**
     * 获取枚举对应的数值表示。
     * @return 枚举的整数值
     */
    public int getValue() {
        return value;
    }


    public static PositionType fromValue(int value) {
        for (PositionType type : PositionType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No TargetType with value " + value);
    }
}
