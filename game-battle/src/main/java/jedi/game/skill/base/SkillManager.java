package jedi.game.skill.base;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.SkillTriggerType;
import jedi.game.player.IEntity;
import jedi.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SkillManager {
    private final List<ISkill> skills = new ArrayList<>();

    public void addSkill(ISkill buff) {
        skills.add(buff);
    }



    public void removeSkill(ISkill skill) {
        skills.remove(skill);
    }

    /**
     * 触发技能效果
     * @param triggerType 技能触发类型
     * @param ctx 战斗上下文
     * @param source 技能施放者
     * @param target 技能目标
     * @return 返回技能效果列表
     */
    public List<ActionEffect> trigger(SkillTriggerType triggerType, BattleContext ctx, IEntity source, Player target) {
        List<ActionEffect> result = new ArrayList<>();
        for (ISkill buff : skills) {
            if (buff.getTriggerType() == triggerType) {
                List<ActionEffect> ab = buff.apply(ctx, source, target);
                for(ActionEffect ae : ab){
                    if (ae != null && ae.getValue() > 0) {
                        result.add(ae);
                    }
                }
            }
        }
        return result;
    }





    public List<ISkill> getSkills() {
        return skills;
    }



}
