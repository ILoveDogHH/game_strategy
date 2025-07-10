package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
// LangKey({1}{2}后，每{3}秒对{4}添加{5}层护盾)_LangArgs(我方任意单位;战斗开始;1;自身;5)
public class ShieldPeriodic extends AbstractSkill {

    public int stack; // 叠加层数


    public ShieldPeriodic(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.target = Integer.valueOf(param[2]);
        this.stack = Integer.valueOf(param[3]);
    }


    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        ActionEffect actionEffect = getActionEffect(target);
        target.addShield(stack);
        actionEffect.setValue(stack);
        return actionEffect;
    }






}
