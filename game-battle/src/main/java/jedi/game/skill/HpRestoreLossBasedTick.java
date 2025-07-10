package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
//LangKey({1}{2}后，每{3}秒让{4}回复自身已损失兵力值{5}点兵力)_LangArgs(我方任意单位;战斗开始;1;自身;1%)
public class HpRestoreLossBasedTick extends AbstractSkill {



    public double percent;


    public HpRestoreLossBasedTick(CfgSkill cfgSkill) {
        super(cfgSkill);
    }

    @Override
    public void deduceParams(String params) {
        String[] param = params.split(";");
        this.caster = param[0];
        this.trigger = Integer.parseInt(param[1]);
        this.tick = Long.parseLong(param[2]);
        this.target = Integer.parseInt(param[3]);
        this.percent = Double.parseDouble(param[4]);
    }

    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        return null;
    }

    @Override
    public ActionEffect executeTick(BattleContext ctx, IEntity source, IEntity target, Player defender) {
        if (!(target instanceof IBattleUnit)) return null;

        IBattleUnit battleUnit = (IBattleUnit) target;


        int lostHp = battleUnit.getLostHp(); // 计算损失的生命值

        int addHp = (int) (lostHp * percent ); // 根据百分比计算恢复的生命值
        if(addHp <= 0) {
            return null; // 如果计算结果小于等于0，则不执行恢复
        }

        battleUnit.addHp(addHp);
        ActionEffect actionEffect = getActionEffect(target);
        actionEffect.setValue(addHp); // 设置恢复的生命值
        return actionEffect; // 返回执行效果
    }
}
