package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;

//LangKey(当{1}{2}{3}时，每{4}秒让{5}回复{6}点兵力，持续{7}秒)_LangArgs(我方任意单位;兵力首次低于;30%;0.3;90;6)
public class HpPeriodicHeal extends AbstractSkill {


    //施法者位置
    public String caster;

    //触发类型
    public int trigger;

    public double tiggerValue;

    public double tick;

    //目标类型
    public int target;

    public int hpRecover;

    public int expireTime = 0;



    public HpPeriodicHeal(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.tiggerValue = Double.parseDouble(param[2]);
        this.tick = Double.parseDouble(param[3]);
        this.target = Integer.valueOf(param[4]);
        this.hpRecover = Integer.parseInt(param[5]);
        this.expireTime = Integer.parseInt(param[6]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        ActionEffect actionEffect = getActionEffect(target);
        target.addShield(hpRecover);
        actionEffect.setValue(hpRecover);
        return actionEffect;
    }
}
