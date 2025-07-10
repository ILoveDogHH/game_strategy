package jedi.game.player;

import jedi.game.enums.PositionType;

/**
 * 战斗单位基础接口。
 * 所有参与战斗的个体（如士兵、武将）均应实现该接口。
 */
public interface IEntity {
    int getUid();
    String getName();
    PositionType getPosition();
    boolean isAlive();
}