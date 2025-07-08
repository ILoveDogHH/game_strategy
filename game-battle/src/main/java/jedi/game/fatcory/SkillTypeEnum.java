package jedi.game.fatcory;

import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.DynamicShield;
import jedi.game.skill.base.ISkill;

public enum SkillTypeEnum {

    NONE(0, "", null),

    DYNAMIC_SHIELD(1, "LangKey({1}{2}时，对{3}添加{4}层护盾)_LangArgs(前军;暴击;自身;4)",DynamicShield::new);



    int type;

    String desc;

    TypeFactory factory;


    private interface TypeFactory {
        ISkill newType(CfgSkill cfgSkill);
    }


    SkillTypeEnum(int type, String desc, TypeFactory factory){
        this.type = type;
        this.desc = desc;
        this.factory = factory;
    }

    public static SkillTypeEnum getType(int type){
        for(SkillTypeEnum skillTypeEnum : values()){
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
        SkillTypeEnum skillTypeEnum = SkillTypeEnum.getType(cfgSkill.getSkil_type());
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
