package jedi.game.event;

import jedi.game.action.Action;
import jedi.game.action.ActionDetail;
import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.EventPriority;
import jedi.game.enums.SkillTriggerType;
import jedi.game.event.api.AbstractEvent;
import jedi.game.event.api.IUnitLinkedEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.skill.base.ISkill;

import java.util.List;

public class SkillEvent extends AbstractEvent implements IUnitLinkedEvent {
    private final ISkill skill;
    private final Player target;
    private final IEntity attacker;

    public SkillEvent(long executeTime, IEntity attacker, Player target, ISkill skill) {
        super(executeTime, EventPriority.SKILL_TICK);
        this.skill = skill;
        this.target = target;
        this.attacker = attacker;
    }

    @Override
    public void execute(BattleContext ctx) {
        if(skill.getTick()<= 0){
            return;
        }
        // 玩家死亡或者技能过期，则移除技能 + 清除未来事件
        if (skill.isExpired(ctx.getCurrentTime())) {
            attacker.getSkillManager().removeSkill(skill);
            return;
        }

        List<ActionEffect> actionEffects = skill.tick(ctx , attacker, target);
        Action action = new Action(ctx.getCurrentTime());
        ActionDetail actionDetail = new ActionDetail(attacker, null, ActionType.BUFF_TICK);
        actionDetail.addActionEffect(actionEffects);
        action.addActionDetail(actionDetail);
        ctx.addAction(action);
        // 注册下一次 Tick 事件
        long nextTick = ctx.getCurrentTime() + skill.getTick();
        ctx.scheduleEvent(new SkillEvent(nextTick, attacker, target, skill));
    }


    @Override
    public IEntity getEntity() {
        return attacker;
    }
}
