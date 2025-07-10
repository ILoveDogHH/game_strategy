package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.EventPriority;
import jedi.game.event.SkillEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

// LangKey({1}{2}后，每{3}秒让{4}回复{5}点气力)_LangArgs(我方武将;战斗开始;0.6;我方武将;2)
public class EnergyRecoverTick extends AbstractSkill {


    public int energyRecover;


    public EnergyRecoverTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String[] param = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.tick = Long.parseLong(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.energyRecover = Integer.parseInt(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        target.recoverEnergyAndReschedule(ctx, target, defender, energyRecover);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(energyRecover);
        return actionEffect;
    }
}
