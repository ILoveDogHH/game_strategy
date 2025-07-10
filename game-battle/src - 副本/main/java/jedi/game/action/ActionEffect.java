package jedi.game.action;

import jedi.game.enums.EffectType;
import jedi.game.enums.TargetType;

/**
 * 表示一次行为产生的额外效果，用于客户端展示（例如 buff、生效动画、恢复等）。
 */
public class ActionEffect {

    /** 效果标识（例如 buffId、技能id、恢复来源等） */
    public final int effectId;

    /** 效果类型，例如 BUFF_ADD, HEAL, SHIELD 等 */
    public final EffectType effectType;

    /** 效果生效人 */
    public final TargetType effectTargetType;

    /** 效果目标的 uid（可能是自己或敌方） */
    public final int targetUid;
    /** 效果目标的position (可能是自己或敌方)*/
    public final int targetPosion;

    /** 效果值（如加血值、护盾值、Buff 层数） */
    public double value;

    /** 可选的持续时间（毫秒），仅对有时效的效果有效，如 Buff、护盾等 */
    public int duration;

    /** 是否为正面效果（客户端可用来区分颜色、图标） */
    public boolean isPositive;

    public ActionEffect(int effectId, EffectType effectType, TargetType effectTargetType, int targetUid, int targetPosion) {
        this.effectId = effectId;
        this.effectType = effectType;
        this.effectTargetType = effectTargetType;
        this.targetUid = targetUid;
        this.targetPosion = targetPosion;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
