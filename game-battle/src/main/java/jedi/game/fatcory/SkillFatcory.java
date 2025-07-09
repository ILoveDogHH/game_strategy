package jedi.game.fatcory;

import jedi.game.servercfg.enity.CfgSkill;
import jedi.game.skill.*;
import jedi.game.skill.base.ISkill;

public enum SkillFatcory {

    NONE(0, "", null),

    SHIELD_DYNAMIC(1000, "LangKey(当{1}{2}时，{3}概率对{4}添加{5}层护盾)_LangArgs(我方前军;暴击;1;自身;4)", ShieldDynamic::new),

    SHIELD_RAND_TICK(1001, "LangKey(当{1}{2}时，对{3}添加{4}层护盾，且接下来{5}秒内每{6}秒添加{7}层护盾)_LangArgs(我方任意单位;首次即将死亡时;自身;1000;5;0.5;100)", ShieldRandTick::new),

    SHIELD_PERIODIC(1002, "LangKey({1}{2}后，每{3}秒对{4}添加{5}层护盾)_LangArgs(我方任意单位;战斗开始;1;自身;5)", ShieldPeriodic::new),

    SHIELD_LOSS_BASED_TICK(1003, "LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层护盾，且至少添加{6}层)_LangArgs(我方任意单位;战斗开始;1.5;自身;0.5%;1)", ShieldLossBasedTick::new),

    HP_DYNAMIC_RESTORE(2000, "LangKey(当{1}{2}时，{3}概率让{4}回复{5}点兵力)_LangArgs(我方任意单位;暴击;100%;自身;12)", HpDynamicRestore::new),

    HP_PERIODIC_HEAL(2001, "LangKey(当{1}{2}{3}时，每{4}秒让{5}回复{6}点兵力，持续{7}秒)_LangArgs(我方任意单位;兵力首次低于;30%;0.3;90;6)", HpPeriodicHeal::new),

    HP_TICK_RESTORE(2002, "LangKey({1}{2}后，每{3}秒让{4}回复{5}点兵力)_LangArgs(我方任意单位;战斗开始;0.8;自身;10)", HpRestoreTick::new),

    HP_TICK_RESTORE_LOSS_BASED(2003, "LangKey({1}{2}后，每{3}秒让{4}回复自身已损失兵力值{5}点兵力)_LangArgs(我方任意单位;战斗开始;1;自身;1%)", HpRestoreLossBasedTick::new),

    ENERGY_DYNAMIC_RESTORE(3000, "LangKey(当{1}{2}时，{3}概率让{4}回复{5}点气力)_LangArgs(我方任意单位;暴击;60%;我方武将;2)", EnergyDynamicRestore::new),

    ENERGY__RECOVER_EXPIRE_TICK(3001, "LangKey({1}{2}后，让{3}每{4}秒回复{5}点气力，持续{6}秒)_LangArgs(我方武将;释放大招;我方武将;0.2;1;2)", EnergyRecoverExpireTick::new),

    ENERGY__RECOVER_TICK(3002, "LangKey({1}{2}后，每{3}秒让{4}回复{5}点气力)_LangArgs(我方武将;战斗开始;0.6;我方武将;2)", EnergyRecoverTick::new),

    STUN_DYNAMIC_RAND(4000, "LangKey(当{1}{2}时，{3}概率让{4}进入{5}秒眩晕)_LangArgs(我方任意单位;暴击;50%;敌方前军;1)", StunDynamicRand::new),

    FREEZE_STACK_DYNAMIC(5000,"LangKey(当{1}{2}时，{3}概率对{4}添加{5}层冰冻)_LangArgs(我方任意单位;暴击;100%;敌方前军;4)", FreezeStackDynamic::new),

    FREEZE_STACK_TICK(5001, "LangKey({1}{2}后，每{3}秒对{4}添加{5}层冰冻)_LangArgs(我方前军;战斗开始;1;敌方全体;6)", FreezeStackTick::new),

    FREEZE_RESTORE_LOSS_BASED_TICK(5002, "LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层冰冻，且至少添加{6}层)_LangArgs(我方前军;战斗开始;1.5;敌方全体;0.6%;1)", FreezeRestoreLossBasedTick::new),

    VULNERABILITY_STACK_DYNAMIC(6000, "LangKey(当{1}{2}时，{3}概率对{4}添加{5}层易伤)_LangArgs(我方后军;暴击;100%;敌方前军;4)", VulnerabilityStackDynamic::new),

    VULNERABILITY_STACK_TICK(6001, "LangKey({1}{2}后，每{3}秒对{4}添加{5}层易伤)_LangArgs(我方前军;战斗开始;1;敌方前军;5)", VulnerabilityStackTick::new),

    VULNERABILITY_LOSS_BASED_TICK(6002, "LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层易伤，且至少添加{6}层)_LangArgs(我方前军;战斗开始;1.5;敌方前军;0.5%;1)",VulnerabilityLossBasedTick::new),

    VULNERABILITY_TIME_BASED_TICK(6003, "LangKey({1}{2}后，每{3}秒对{4}添加等于当前战斗时间秒数*{5}的易伤)_LangArgs(我方前军;战斗开始;1;敌方前篇;2)", VulnerabilityTimeBasedTick::new),

    BURN_RAND_DYNAMIC(7000, "LangKey(当{1}{2}时，{3}概率对{4}添加{5}层燃烧)_LangArgs(我方后军;暴击;100%;敌方后军;5)", BurnRandDynamic::new),

    BURN_STACK_TICK(7001, "LangKey({1}{2}后，每{3}秒对{4}添加{5}层燃烧)_LangArgs(我方前军;战斗开始;1;敌方前军;6)", BurnStackTick::new),

    BURN_RESTORE_LOSS_BASED_TICK(7002, "LangKey({1}{2}后，每{3}秒对{4}添加自身已损失兵力值{5}层燃烧，且至少添加{6}层)_LangArgs(我方前军;战斗开始;1500;敌方后军;0.6%;1)", BurnRestoreLossBasedTick::new),

    
    ;

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
