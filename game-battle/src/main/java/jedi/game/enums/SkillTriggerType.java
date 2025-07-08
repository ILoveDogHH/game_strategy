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
    ON_CRIT,

    /**
     * 当单位发起一次攻击行为（普攻）时。
     * 示例：每次攻击时有概率附加中毒、护盾等。
     */
    ON_ATTACK,

    /**
     * 当单位攻击命中敌方目标时。
     * 示例：命中后对敌方造成额外燃烧层数。
     */
    ON_HIT,

    /**
     * 当单位成功闪避一次攻击时。
     * 示例：闪避后获得速度加成、对敌人施加减益。
     */
    ON_DODGE,

    /**
     * 当单位受到伤害后触发。
     * 示例：受伤后反击、生成护盾、叠加愤怒等。
     */
    ON_DAMAGE_TAKEN,

    /**
     * 循环 技能事件
     */
    TICKABLE;

}
