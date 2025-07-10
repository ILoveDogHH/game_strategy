package jedi.game.enums;

/**
 * BuffTriggerType 表示 Buff 的触发时机类型。
 * 用于标识 Buff 在战斗中响应的事件节点，搭配 TriggeredBuff 接口使用。
 */
public enum SkillTriggerType {

    /**
     * 当单位触发暴击时。
     * 示例：暴击额外造成真实伤害、暴击时回复气力。
     */
    ON_CRIT(101),

    /**
     * 当单位发起一次攻击行为（普攻）时。
     * 示例：每次攻击时有概率附加中毒、护盾等。
     */
    ON_ATTACK(102),

    /**
     * 当单位攻击命中敌方目标时。
     * 示例：命中后对敌方造成额外燃烧层数。
     */
    ON_HIT(103),

    /**
     * 当单位成功闪避一次攻击时。
     * 示例：闪避后获得速度加成、对敌人施加减益。
     */
    ON_DODGE(104),

    /**
     * 当单位受到伤害后触发。
     * 示例：受伤后反击、生成护盾、叠加愤怒等。
     */
    ON_DAMAGE_TAKEN(105),

    /**
     * 循环 技能事件
     */
    TICKABLE(106),

    /**
     * 造成伤害时触发。
     * 示例：造成伤害后附加额外效果、回复生命等。
     */
    ON_DEAL_DAMAGE(107),

    /**
     * 释放大招时触发。
     * 示例：释放大招后添加增益、附加额外特效。
     */
    ON_ULTIMATE(108),

    /**
     * 战斗开始时触发。
     * 示例：战斗开始立即获得护盾或施加状态。
     */
    ON_BATTLE_START(109),

    /**
     * 首次即将死亡时触发。
     * 示例：触发一次不死、护盾、无敌等机制。
     */
    ON_FIRST_NEAR_DEATH(110),

    /**
     * 兵力值首次低于某个阈值时触发。
     * 示例：低于50%触发激怒、恢复等效果。
     */
    ON_FORCE_BELOW_THRESHOLD(111),

    /**
     * 获得护盾时触发。
     * 示例：获得护盾后提升防御、攻击等。
     */
    ON_GAIN_SHIELD(112),

    /**
     * 施加易伤时触发。
     * 示例：附加易伤后造成额外伤害、传播状态等。
     */
    ON_APPLY_VULNERABLE(113),

    /**
     * 回复生命时触发。
     * 示例：每次回复生命时获得能量、提升暴击等。
     */
    ON_HEAL(114);



    public  int value;

    SkillTriggerType(int value) { this.value = value; }

    public static SkillTriggerType fromValue(int value) {
        for (SkillTriggerType type : SkillTriggerType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("No TargetType with value " + value);
    }
}
