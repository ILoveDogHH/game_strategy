package jedi.game.player;

import jedi.game.enums.PositionType;
import jedi.game.skill.base.SkillManager;
// Soldier类表示游戏中的士兵角色，继承自Enity接口
public class Soldier extends AbstractEntity {
    // 士兵的最大生命值
    public int maxHp;
    // 士兵当前的生命值
    public int currentHp;
    // 士兵的攻击力
    public int attack;
    // 士兵的暴击率
    public double critRate;
    // 士兵的暴击伤害倍数
    public double critMultiplier;
    // 士兵的闪避率
    public double dodgeRate;
    // 士兵的攻击速度（攻击次数/秒）
    public double attackSpeed;
    //被冰冻的层数
    public int freeze = 0;


    protected SkillManager buffManager = new SkillManager();

    // 构造方法，用于初始化士兵的属性
    public Soldier(PositionType positionType, String name, int maxHp, int attack, double critRate, double critMultiplier,
                   double dodgeRate, double attackSpeed, Player owner, int projectileTs) {
        super(positionType, name, owner);
        this.positionType = positionType;
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp; // 初始生命值等于最大生命值
        this.attack = attack;
        this.critRate = critRate;
        this.critMultiplier = critMultiplier;
        this.dodgeRate = dodgeRate;
        this.attackSpeed = attackSpeed;
        this.owner = owner;
        this.projectileTs = projectileTs;
    }

    @Override
    public int getUid() {
        return uid ;
    }

    @Override
    public int getPosition() {
        return positionType.getValue();
    }

    // 判断士兵是否存活
    @Override
    public boolean isAlive() {
        return currentHp > 0;
    }

    @Override
    public double getCritRate() {
        return critRate;
    }

    @Override
    public int getCritMultiplier() {
        return 0;
    }

    @Override
    public int getDodgeRate() {
        return 0;
    }

    // 接收伤害并更新当前生命值
    @Override
    public void receiveDamage(int damage) {
        currentHp = Math.max(0, currentHp - damage); // 确保生命值不会低于0
    }

    // 获取士兵的名称
    @Override
    public String getName() {
        return name;
    }


    @Override
    public void heal(int amount) {
        this.currentHp = Math.min(this.maxHp, this.currentHp + amount);
    }


    // 获取士兵所属的玩家
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public SkillManager getSkillManager() {
        return buffManager;
    }

    @Override
    public int getCurrentHp() {
        return currentHp;
    }

    @Override
    public void addHp(int hp) {
        currentHp  = currentHp + hp;
    }


    @Override
    public void subHp(int hp) {
        this.currentHp = Math.max(0, currentHp - hp);
    }

    @Override
    public double getAttackSpeed() {
        return attackSpeed * (1 - freeze * 0.1); // 冰冻层数每层减少10%的攻击速度
    }

    @Override
    public int addFreeze(int freeze) {
        return freeze + this.freeze;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }




}