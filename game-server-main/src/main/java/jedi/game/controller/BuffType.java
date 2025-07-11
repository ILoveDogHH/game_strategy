package jedi.game.controller;

public enum  BuffType {



    Vital_Strike(20001,  20001, "生命暴击","每次暴击额外增加敌人当前生命值2%的伤害。", "200"),

    CritFreeze(30001,  30001, "冰冻暴击", "每次暴击时，对敌人添加4层冰冻。", "4"),

    Recover_LostHp_Per_Second(40001, 40001, "间隔回血", "每过1秒，回复已损失生命值1%的生命值。", "1000,0.1");


    int buffid;
    int buffType;

    String name;

    String desc;

    String params;

    BuffType(int buffid, int buffType, String name, String desc, String params){
        this.buffid = buffid;
        this.buffType = buffType;
        this.name = name;
        this.desc = desc;
        this.params = params;

    }


    public static String getDesc(int buffid){
        for (BuffType buffType : BuffType.values()) {
            if (buffType.buffid == buffid) {
                return buffType.desc;
            }
        }
        return "未知的Buff";
    }


}
