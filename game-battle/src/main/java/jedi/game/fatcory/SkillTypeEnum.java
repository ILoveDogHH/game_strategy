package jedi.game.fatcory;

import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.base.ISkill;

public enum SkillTypeEnum {

    NONE(1, "desc", null);


    int type;

    String desc;

    TypeFactory factory;


    private interface TypeFactory {
        ISkill newType(int id, String param);
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
        ISkill skill = skillTypeEnum.newType(cfgSkill.getSkill_id(), cfgSkill.getParam());
        return skill;
    }

    public TypeFactory getFactory(){
        return factory;
    }


    /**
     * 创建一个新的task
     *
     * @param id
     * @param params
     * @return
     */
    private ISkill newType(int id,  String params) {
        return factory.newType(id, params);
    }



}
