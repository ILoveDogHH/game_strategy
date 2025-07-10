package jedi.game.damage;


/**
 * 表示一次战斗中的伤害结算结果，支持暴击、闪避、吸血、护盾吸收等扩展机制。
 */
public class Damage {

    /** 实际对目标造成的伤害（可能被护盾吸收后减少） */
    public int damage;

    /** 气力变化*/
    public int energy;

    /** 本次攻击是否为暴击 */
    public boolean isCrit;

    /** 本次攻击是否被目标闪避 */
    public boolean isDodged;

    /** 造成伤害前的原始伤害值（未减防御、未被护盾吸收） */
    public int rawDamage;

    /** 被护盾吸收的部分（最终对目标造成的伤害 = rawDamage - shieldAbsorb - defenseReduce） */
    public int shieldAbsorb;

    /** 被防御或减伤机制减免的伤害值 */
    public int defenseReduce;

    /** 攻击者因本次攻击吸取的生命值（吸血） */
    public int lifeSteal;

    /** 反弹给攻击者的伤害（如荆棘甲） */
    public int reflectedDamage;

    /** 是否被免疫了（如霸体/无敌等） */
    public boolean isImmune;

    /** 是否为溢出伤害（比如目标只有10血，你打了100） */
    public boolean isOverkill;


}