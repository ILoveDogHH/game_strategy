package jedi.game.player;

import jedi.game.battle.BattleContext;
import jedi.game.enums.TargetType;

/**
 * 拥有气力（能量）机制的战斗单位接口。
 * 主要用于拥有技能释放能力的单位（如武将），
 * 提供获取当前气力、最大气力、气力恢复速度等功能。
 * 继承自 IEntity，具备实体基本属性。
 */
public interface IEnergyUnit extends IEntity {

    /**
     * 获取当前气力值（currentEnergy）。
     * 气力值通常用于技能释放的前提条件。
     * @return 当前气力
     */
    int getCurrentEnergy();

    /**
     * 获取最大气力值（maxEnergy）。
     * 决定当前气力的上限值。
     * @return 最大气力
     */
    int getMaxEnergy();

    /**
     * 获取每秒恢复的气力值。
     * 用于气力自动恢复机制，例如每秒恢复 10 点气力。
     * @return 每秒气力恢复值
     */
    int getEnergyRecoverPerSecond();

    /**
     * 判断当前单位是否可以释放技能。
     * 通常由当前气力是否达到某个门槛决定，也可受状态影响。
     * @return true 表示可释放技能
     */
    boolean canReleaseSkill();

    /**
     * 设置当前气力值。
     * 一般在释放技能或回复气力时调用。
     * @param val 要设置的气力值
     */
    void setCurrentEnergy(int val);

    /**
     * 恢复气力并根据条件重新调度技能事件。
     * 一般在定时气力恢复逻辑中调用，恢复气力后如达到释放条件，则插入技能事件。
     *
     * @param ctx 当前战斗上下文
     * @param enity 本单位实体（一般为自身）
     * @param target 技能目标（敌方玩家）
     * @param addEnergy 增加的气力值
     */
    void recoverEnergyAndReschedule(BattleContext ctx, IEntity enity, Player target, int addEnergy);
}
