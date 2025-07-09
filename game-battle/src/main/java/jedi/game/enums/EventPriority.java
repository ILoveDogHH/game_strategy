package jedi.game.enums;

/**
 * EventPriority 表示战斗事件在同一时刻触发时的优先级顺序。
 * 数值越小，优先级越高，越早被调度执行。
 */
public enum EventPriority {

    /**
     * PROJECTILE（优先级 100）：
     * 表示飞行道具（如火球、箭矢）命中触发的事件。
     * 一般应在伤害或效果真正命中前被调度处理。
     */
    PROJECTILE(100),

    /**
     * BUFF_TICK（优先级 200）：
     * 表示 技能 定时触发（如持续伤害、持续治疗）的事件。
     * 通常在时间轮询中定期执行。
     */
    SKILL_TICK(200),




    /**
     * ATTACK（优先级 300）：
     * 表示普通攻击行为，包括直接近战攻击或触发型远程攻击。
     */
    ATTACK(300),



    /**
     * 大招（优先级 400）：
     * 表示技能释放事件，通常在玩家或敌人使用技能时触发。
     */

    ULTIMATE_Skill(400),

    /**
     * OTHER（优先级 0）：
     * 其他未归类的事件类型，优先级最低，通常兜底使用。
     */
    OTHER(0);

    /** 优先级数值，数值越小，事件越早执行 */
    private final int value;

    EventPriority(int value) {
        this.value = value;
    }

    /**
     * 获取事件优先级值。
     * @return 整型优先级数值
     */
    public int getValue() {
        return value;
    }

}
