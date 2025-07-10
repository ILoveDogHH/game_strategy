package jedi.game.action;

import jedi.game.damage.Damage;
import jedi.game.enums.ActionType;
import jedi.game.player.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 行为的详细内容：包括来源、目标、伤害、效果等
 */
public class ActionDetail {

    /** 发起者 uid */
    public final int sourceUid;

    /** 发起者位置 */
    public final int sourcePosition;

    public final String sourceName;

    //测试数据方便展示
    public final int sourceHp;

    //测试数据方便展示
    public final int sourceMaxHp;


    /** 目标 uid */
    public final int targetUid;

    /** 目标位置 */
    public final int targetPosition;

    //测试数据方便展示
    public  int targetHp;

    //测试数据方便展示
    public final int targetMaxHp;

    public final String targetName;

    /** 行为类型：ATTACK / SKILL / BUFF_TICK / PROJECTILE */
    public final ActionType actionType;

    /** 行为的预期命中时间（0 表示瞬发，>0 表示飞行中） */
    public long scheduledHitTime;

    /** 造成的伤害（可能为空） */
    public Damage damage = new Damage();

    /** 附加效果，如 Buff、护盾、治疗等 */
    public List<ActionEffect> effects = new ArrayList<>();


    public ActionDetail(IEntity attker, IEntity defender, ActionType actionType) {
        this.sourceUid = attker.getUid();
        this.sourcePosition = attker.getPosition();
        this.sourceName = attker.getName();
        this.sourceHp = attker.getCurrentHp();
        this.sourceMaxHp = attker.getMaxHp();
        this.targetUid =defender == null ? 0 :  defender.getUid();
        this.targetPosition = defender == null ? 0 : defender.getPosition();
        this.targetName = defender == null ? "" : defender.getName();
        this.targetHp = defender == null ? 0 : defender.getCurrentHp();
        this.targetMaxHp = defender == null ? 0 : defender.getMaxHp();
        this.actionType = actionType;
    }


    public void addActionEffect(List<ActionEffect> effects){
        this.effects.addAll(effects);
    }



}