package jedi.game.event.api;

import jedi.game.battle.BattleContext;

/**
 * 战斗事件接口，定义战斗中所有可调度事件的基本行为规范。
 * 所有战斗事件（如攻击、Buff生效、回气等）必须实现本接口，
 * 并由战斗调度系统按时间顺序执行。
 *
 * 该接口继承 Comparable，用于事件队列中按执行时间排序。
 */
public interface IEvent extends Comparable<IEvent> {

    /**
     * 获取事件的执行时间（毫秒），用于时间调度排序。
     * 时间值越小，事件越早执行。
     *
     * @return 事件计划执行的战斗时间
     */
    long getExecuteTime();

    long getSeq();

    int getPriority();

    /**
     * 执行事件的具体逻辑。
     * 例如：执行一次攻击、触发一个 Buff 效果、回复气力等。
     *
     * @param context 当前战斗上下文（BattleContext）
     */
    void execute(BattleContext context);



}
