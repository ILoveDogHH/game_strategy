package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
import jedi.game.utils.Random;

//LangKey(当{1}{2}时，{3}概率让{4}回复{5}点兵力)_LangArgs(我方任意单位;暴击;100%;自身;12)
public class HpDynamicRestore extends AbstractSkill {

    //施法者位置
    public String caster;

    //触发类型
    public int trigger;

    public double rate; //触发概率

    //目标类型
    public int target;

    //回复量
    public int restore;

    public HpDynamicRestore(CfgSkill cfgSkill) {
        super(cfgSkill);
    }



    @Override
    public void deduceParams(String params) {
        String param[] = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.valueOf(param[1]);
        this.rate = Double.parseDouble(param[2]);
        this.target = Integer.valueOf(param[3]);
        this.restore = Integer.valueOf(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if(!Random.isRand(rate)){
            return null;
        }
        ActionEffect actionEffect = getActionEffect(target);
        target.addHp(restore);
        actionEffect.setValue(restore);
        return actionEffect;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }
}
