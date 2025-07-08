package jedi.game.action;


import jedi.game.controller.BuffType;

import java.util.ArrayList;
import java.util.List;

public class Action {
    public final long currentTime;

    List<ActionDetail> actionDetails = new ArrayList<>();


    public Action(long currentTime) {
        this.currentTime = currentTime;
    }

    public void addActionDetail(ActionDetail actionDetail){
        this.actionDetails.add(actionDetail);
    }



    public String toVisualString() {
        StringBuilder sb = new StringBuilder();
        sb.append("触发时间: ").append(currentTime).append(" ");

        for (ActionDetail detail : actionDetails) {
            sb.append(">> [").append(detail.actionType.name).append("] ").append("  ")
                    .append("攻击者").append(detail.sourceName).append("  【HP:").append(detail.sourceHp).append("/").append(detail.sourceMaxHp).append("】  ")
                    .append("防御者").append(detail.targetName).append("  【HP:").append(detail.targetHp).append("/").append(detail.targetMaxHp).append("】  ");


            if (detail.scheduledHitTime > currentTime) {
                long delay = detail.scheduledHitTime - currentTime;
                sb.append("   本次攻击为飞行道具，预计命中时间: ")
                        .append(detail.scheduledHitTime).append("（延迟 ").append(delay).append("ms）  ");
            }

            if (detail.damage.damage > 0) {
                sb.append("   扣除血量: ").append(detail.damage.damage);
                if (detail.damage.isCrit) {
                    sb.append(" (暴击)");
                }
                if (detail.damage.isDodged) {
                    sb.append(" (闪避)");
                }
            }

            if(detail.damage.energy > 0){
                sb.append(" 当前气力: ").append(detail.damage.energy);
            }
            if(detail.effects.size() > 0){
                sb.append("   附加效果:   ");

            }
            for(ActionEffect e :  detail.effects){
                sb.append("     - ").append(BuffType.getDesc(e.effectId))

                 .append(" 效果值: ").append(e.value);

            }
        }

        return sb.toString();
    }





}