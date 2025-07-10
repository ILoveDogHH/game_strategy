package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEnergyUnit;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
import jedi.game.utils.Random;

// LangKey(当{1}{2}时，{3}概率让{4}回复{5}点气力)_LangArgs(我方任意单位;暴击;60%;我方武将;2)
public class EnergyDynamicRestore extends AbstractSkill {

    public double rate; //触发概率
    public int addEnergy;




    public EnergyDynamicRestore(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.rate = Double.parseDouble(param[2]);
        this.target = Integer.valueOf(param[3]);
        this.addEnergy = Integer.valueOf(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if (!(target instanceof IEnergyUnit)) return null;

        IEnergyUnit energyUnit = (IEnergyUnit) target;


        if(!Random.isRand(rate)){
            return null;
        }
        energyUnit.recoverEnergyAndReschedule(ctx, target, defender, addEnergy);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(addEnergy);
        return actionEffect;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }
}
