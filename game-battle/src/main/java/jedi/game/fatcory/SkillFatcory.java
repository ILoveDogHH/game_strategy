package jedi.game.fatcory;

import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.DynamicShield;
import jedi.game.skill.RandTickShield;
import jedi.game.skill.base.ISkill;

import javax.jws.Oneway;

public enum SkillFatcory {

    NONE(0, "", null),

    DYNAMIC_SHIELD(1000, "LangKey(当{1}{2}时，{3}概率对{4}添加{5}层护盾)_LangArgs(我方前军;暴击;1;自身;4)",DynamicShield::new),

    RAND_TICK_SHIELD(1001, "LangKey(当{1}{2}时，对{3}添加{4}层护盾，且接下来{5}秒内每{6}秒添加{7}层护盾)_LangArgs(我方任意单位;首次即将死亡时;自身;1000;5;0.5;100)",RandTickShield::new);



    int type;

    String desc;

    TypeFactory factory;


    private interface TypeFactory {
        ISkill newType(CfgSkill cfgSkill);
    }


    SkillFatcory(int type, String desc, TypeFactory factory){
        this.type = type;
        this.desc = desc;
        this.factory = factory;
    }

    public static SkillFatcory getType(int type){
        for(SkillFatcory skillTypeEnum : values()){
            if(skillTypeEnum.type == type){
                return skillTypeEnum;
            }
        }
        return NONE;
    }


    public static ISkill createSkill(CfgSkill cfgSkill){
        if (cfgSkill == null) {
            return null;
        }
        SkillFatcory skillTypeEnum = SkillFatcory.getType(cfgSkill.getSkil_type());
        ISkill skill = skillTypeEnum.newType(cfgSkill);
        return skill;
    }

    public TypeFactory getFactory(){
        return factory;
    }



    private ISkill newType(CfgSkill cfgSkill) {
        return factory.newType(cfgSkill);
    }



}
