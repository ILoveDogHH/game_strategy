package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

//LangKey({1}{2}后，让{3}每{4}秒回复{5}点气力，持续{6}秒)_LangArgs(我方武将;释放大招;我方武将;0.2;1;2)
public  class EnergyRecoverExpireTick extends AbstractSkill {



    public int energyRecover;


    public EnergyRecoverExpireTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.target = Integer.parseInt(param[2]);
        this.tick = Long.parseLong(param[3]);
        this.energyRecover = Integer.parseInt(param[4]);
        this.expireTime = Long.parseLong(param[5]);
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
