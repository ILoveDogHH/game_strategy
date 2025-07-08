package jedi.game.skill;

import jedi.game.action.ActionEffect;
import jedi.game.battle.BattleContext;
import jedi.game.enums.PositionType;
import jedi.game.player.IEntity;
import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.AbstractSkill;
//LangKey({1}{2}时，对{3}添加{4}层护盾)_LangArgs(前军;暴击;自身;4)
public class DynamicShield extends AbstractSkill {

    private  int position; // 位置，1前军，2后军

    private  int stack;    // 层数



    public DynamicShield(CfgSkill cfgSkill){
        super(cfgSkill.getSkill_id(), cfgSkill.getStar(), cfgSkill.getEffect());
        deduceParams(cfgSkill.getParam());
    }

    private void deduceParams(String params){
        String param[] = params.split(";");
        this.position = Integer.valueOf(param[0]);
        this.trigger = Integer.valueOf(param[1]);
        this.target = Integer.valueOf(param[2]);
        this.stack = Integer.valueOf(param[3]);
    }




    @Override
    public ActionEffect execute(BattleContext ctx, IEntity source, IEntity target) {
        ActionEffect actionEffect = getActionEffect(target);
        if(source.getPosition() != position){
            return null;
        }
        target.addShield(stack);
        actionEffect.setValue(stack);
        return actionEffect;
    }
}
