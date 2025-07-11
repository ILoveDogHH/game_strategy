package jedi.game.player;

import jedi.game.battle.BattleContext;
import jedi.game.enums.TargetType;
import jedi.game.skill.base.SkillManager;

/**
 * 战斗单位基础接口。
 * 所有参与战斗的个体（如士兵、武将）均应实现该接口。
 * 定义了战斗中所有实体应具备的基本行为或属性，如 UID、位置、名称、技能系统、目标选择逻辑等。
 */
public interface IEntity {

    /**
     * 获取实体在战斗中的唯一标识。
     * 通常用于事件系统、日志记录、Buff追踪等需要识别唯一单位的场景。
     * @return 实体的唯一ID
     */
    int getUid();

    /**
     * 获取实体所在的位置编号。
     * 可用于判断前军、后军、武将等不同站位。
     * @return 位置编号
     */
    int getPosition();

    /**
     * 获取实体的名称。
     * 可用于界面显示、战斗日志、调试信息。
     * @return 实体名称
     */
    String getName();

    /**
     * 获取实体的技能管理器。
     * 用于管理 Buff、技能效果、触发逻辑等。
     * @return 技能管理器对象
     */
    SkillManager getSkillManager();

    /**
     * 获取实体的目标选择类型。
     * 决定攻击或技能释放时优先选择的敌人类型（如优先前军、随机、最低血量等）。
     * @return 目标选择策略枚举值
     */
    TargetType getTargeType();

    /**
     * 获取实体的暴击率。
     * 暴击率用于决定普通攻击或技能是否触发暴击。
     * @return 当前暴击率（0~1）
     */
    double getCritRate();
}