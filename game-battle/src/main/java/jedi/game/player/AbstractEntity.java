package jedi.game.player;

import jedi.game.enums.PositionType;
import jedi.game.skill.base.ISkill;
import jedi.game.skill.base.SkillManager;

/**
 * 抽象实体类，封装战斗单位（如士兵、武将）共有的通用字段与方法。
 * 实现了 IEntity 接口，为具体单位类（如 Soldier、Hero）提供基础属性支持。
 */
public abstract class AbstractEntity implements IEntity {

    /** 实体的唯一标识符（在战斗中唯一） */
    protected int uid;

    /** 实体所处的位置类型（如前军、后军、武将） */
    protected PositionType positionType;

    /** 实体名称，用于显示或日志输出 */
    protected String name;

    /** 实体的技能管理器（包含主动技能与被动 Buff） */
    protected SkillManager buffManager = new SkillManager();

    /**
     * 获取实体的唯一标识。
     * @return 实体 UID
     */
    @Override
    public int getUid() {
        return uid;
    }

    /**
     * 获取实体的位置编号（前军、后军等）。
     * @return 位置枚举的整数值
     */
    @Override
    public int getPosition() {
        return positionType.getValue();
    }

    /**
     * 获取实体名称。
     * @return 实体名称字符串
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 获取实体的技能管理器，用于管理 Buff 和技能。
     * @return 技能管理器实例
     */
    @Override
    public SkillManager getSkillManager() {
        return buffManager;
    }
}
