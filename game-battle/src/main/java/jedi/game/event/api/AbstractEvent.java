package jedi.game.event.api;

import jedi.game.enums.EventPriority;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 抽象事件基类，所有战斗事件的基础模型。
 * 实现 IEvent 接口，并支持基于时间的优先级排序（用于事件队列调度）。
 * 子类可实现 execute() 方法以完成攻击、Buff触发等逻辑。
 */
public abstract class AbstractEvent implements IEvent  {


    /** 事件预定执行时间（毫秒时间戳，基于战斗时间） */
    protected long executeTime;

    protected final long seq;

    protected final EventPriority priority;

    /** 全局自增序号，确保时间相同时顺序稳定 */
    private static final AtomicLong COUNTER = new AtomicLong();

    /**
     * 构造函数
     * @param executeTime 事件计划执行的时间（毫秒）
     */
    public AbstractEvent(long executeTime, EventPriority priority) {
        this.executeTime = executeTime;
        this.seq = COUNTER.getAndUpdate(prev -> (prev == Long.MAX_VALUE) ? 0 : prev + 1);
        this.priority = priority;
    }

    /**
     * 获取事件的执行时间，用于调度系统排序
     * @return 执行时间（毫秒）
     */
    @Override
    public long getExecuteTime() {
        return executeTime;
    }

    @Override
    public long getSeq() {
        return seq;
    }


    /**
     * 比较两个事件的执行时间，用于事件优先队列排序
     * @param o 另一个事件对象
     * @return 小于0表示本事件更早执行
     */
    @Override
    public int compareTo(IEvent o) {
        int cmp = Long.compare(this.executeTime, o.getExecuteTime());
        if (cmp != 0) return cmp;

        // 行为优先级：值小优先
        cmp = Integer.compare(getPriority(), o.getPriority());
        if (cmp != 0) return cmp;


        // 最后用 seq 保证稳定性
        return Long.compare(this.seq, o.getSeq());
    }

    @Override
    public int getPriority(){
        return priority.getValue();
    }

}
