package jedi.game.action;

import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.SkillTriggerType;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.skill.base.TargetSelector;

import java.util.List;

public class DamageCalculator {
    /**
     * 计算最终伤害
     * @param attacker 攻击者（可以为 null，例如技能伤害）
     * @param baseDamage 基础伤害
     * @param  （物理、策略、燃烧、天命等）
     * @param ctx 战斗上下文（用于获取全局速率等信息）
     * @return 实际造成的伤害
     */
    public static Action calculateDamage(BattleContext ctx, IEntity attacker, Player target, int baseDamage, DamageType damageType, ActionType actionType) {
        double finalDamage = baseDamage;
        Action action = new Action(ctx.getCurrentTime());
        List<IEntity> defenders = TargetSelector.selectTargets(attacker, target, attacker.getTargeType());
        for(IEntity defender : defenders){
            ActionDetail actionDetail = new ActionDetail( attacker, defender, actionType);
            boolean isCrit = false;
            boolean isDodged = false;
            // 1.暴击判定
            if (attacker != null && damageType.canCrit() && Math.random() < attacker.getCritRate()) {
                finalDamage +=  Math.max(0,attacker.getCritMultiplier() -1) * finalDamage;
                // 暴击发生时触发
                List<ActionEffect> skillList0 = attacker.getSkillManager().trigger(SkillTriggerType.ON_CRIT, ctx, attacker, target);
                actionDetail.addActionEffect(skillList0);
                isCrit = true;
            }
            // 4. 战斗加速
            finalDamage *= ctx.getSpeedCoefficient();
            // 2. 闪避判定
            if (damageType.canDodge() && Math.random() < defender.getDodgeRate()) {
                isDodged = true;
                finalDamage = 0;
            }
            actionDetail.damage.isDodged = isDodged;
            actionDetail.damage.isCrit = isCrit;
            actionDetail.damage.damage = (int) finalDamage;
            action.addActionDetail(actionDetail);
            defender.subHp((int) finalDamage);
            //测试数据方便展示
            actionDetail.targetHp = actionDetail.targetHp - (int) finalDamage;
        }
        return action;
    }



}
