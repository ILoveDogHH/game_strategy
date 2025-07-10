package jedi.game.player;

import jedi.game.battle.BattleContext;
import jedi.game.enums.TargetType;
import jedi.game.skill.base.SkillManager;

/**
 * 战斗单位基础接口。
 * 所有参与战斗的个体（如士兵、武将）均应实现该接口。
 */
// IEntity：所有实体都有的位置、UID、名称、所属者
public interface IEntity {
    int getUid();
    int getPosition();
    String getName();
    Player getOwner();
    SkillManager getSkillManager();
    TargetType getTargeType();
    double getCritRate();
}