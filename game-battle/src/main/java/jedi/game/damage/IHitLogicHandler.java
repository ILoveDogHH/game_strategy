package jedi.game.damage;

import jedi.game.enums.DamageType;
import jedi.game.player.IEntity;

/**
 * IHitLogicHandler 用于定义伤害行为中的命中判定逻辑。
 * 每种 DamageType（如物理、计策、燃烧、真实伤害）可以绑定不同的命中逻辑策略。
 * 该接口用于统一处理暴击、闪避以及暴击倍率等关键逻辑的判断。
 *
 * 实现类应结合具体伤害类型，从攻击者、防御者的属性中获取对应暴击率、闪避率、失效率等参数。
 * 通过该接口，战斗系统可以实现对不同伤害类型的命中判定进行解耦、扩展与定制。
 */
public interface IHitLogicHandler {

    /**
     * 判断当前攻击是否触发暴击。
     *
     * @param attacker  发起攻击的单位
     * @param type      本次伤害的类型（物理、计策等）
     * @return          是否暴击
     */
    boolean isCrit(IEntity attacker, DamageType type);

    /**
     * 判断当前攻击是否被目标闪避。
     *
     * @param defender  承受攻击的单位
     * @param type      本次伤害的类型（物理、计策等）
     * @return          是否闪避成功
     */
    boolean isDodged(IEntity defender, DamageType type);

    /**
     * 获取暴击倍率（例如：1.5 表示暴击造成 150% 伤害）。
     *
     * @param attacker  发起攻击的单位
     * @param type      本次伤害的类型
     * @return          暴击伤害倍率（≥1.0）
     */
    double getCritMultiplier(IEntity attacker, DamageType type);
}
