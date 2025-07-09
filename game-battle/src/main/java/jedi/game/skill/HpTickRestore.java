package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EventPriority;
import jedi.game.event.SkillEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

//LangKey({1}{2}后，每{3}秒让{4}回复{5}点兵力)_LangArgs(我方任意单位;战斗开始;0.8;自身;10)
public class HpTickRestore  extends AbstractSkill {

    //施法者位置
    public String caster;

    //触发类型
    public int trigger;

    //触发间隔
    public int tick;

    //目标类型
    public int target;


    public int hpRestore;





    public HpTickRestore(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.tick = Integer.parseInt(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.hpRestore = Integer.parseInt(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(hpRestore);
        target.addHp(hpRestore);

        // 注册下一次 Tick 事件
        long nextTick = ctx.getCurrentTime() + getTick();
        ctx.scheduleEvent(new SkillEvent(nextTick, EventPriority.BUFF_TICK, source, defender, this));
        return actionEffect;
    }


}
