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

    public SkillEvent(long executeTime, EventPriority eventPriority, IEntity attacker, Player target, ISkill skill) {
        super(executeTime, eventPriority);
        this.skill = skill;
        this.target = target;
        this.attacker = attacker;
    }

    @Override
    public void execute(BattleContext ctx) {
        List<ActionEffect> actionEffects = attacker.getSkillManager().trigger(SkillTriggerType.TICKABLE, ctx, attacker, target);
        if(skill.getInterval() <= 0){
            return;
        }
        Action action = new Action(ctx.getCurrentTime());

        ActionDetail actionDetail = new ActionDetail(attacker, null, ActionType.BUFF_TICK);
        actionDetail.addActionEffect(actionEffects);
        action.addActionDetail(actionDetail);
        ctx.addAction(action);
        // 注册下一次 Tick 事件
        long nextTick = ctx.getCurrentTime() + skill.getInterval();
        ctx.scheduleEvent(new SkillEvent(nextTick, EventPriority.BUFF_TICK, attacker, target, skill));
    }


    @Override
    public IEntity getEntity() {
        return attacker;
    }
}
