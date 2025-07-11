package jedi.game.player;

import jedi.game.enums.PositionType;
import jedi.game.enums.TargetType;
import jedi.game.servercfg.enity.CfgSoldier;
import jedi.game.skill.base.SkillManager;
// Soldier类表示游戏中的士兵角色，继承自Enity接口
public class Soldier extends AbstractEntity implements IBattleUnit{
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
    public int freeze ;

    public int shield;

    int projectileTs ;

    int vulnerable;

    int burn;

    long stunTs;


    protected SkillManager buffManager = new SkillManager();



    public Soldier(int uid, CfgSoldier cfg){
        this.uid = uid;
        this.positionType = PositionType.fromValue(cfg.getPosition_type());
        this.name = cfg.getSoldier_name();
        this.maxHp = cfg.getHp_max();
        this.currentHp = cfg.getHp_max(); // 初始生命值等于最大生命值
        this.attack = cfg.getAttack();
        this.critRate = cfg.getCrit_rate();
        this.critMultiplier = cfg.getCrit_damage_bonus();
        this.dodgeRate = cfg.getDodge_rate();
        this.attackSpeed = cfg.getAttack_speed();
        this.projectileTs = cfg.getProjectile_ts();
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
    public double getDodgeRate() {
        return dodgeRate;
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


    @Override
    public SkillManager getSkillManager() {
        return buffManager;
    }

    @Override
    public TargetType getTargeType() {
         return TargetType.PRIORITY_FRONT;
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
    public void setStunTs(long stunTs) {
        this.stunTs += stunTs;
    }

    @Override
    public void addVulnerable(int stak) {
        this.vulnerable += stak;
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
    public boolean isAlive() {
        return currentHp > 0;
    }

    @Override
    public long getProjectileTs() {
        return projectileTs;
    }

    @Override
    public void addBurn(int stack) {
        this.burn += stack;
    }

    @Override
    public void addFreeze(int stack) {
        this.freeze += stack ;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public void addShield(int shield) {
        this.shield = Math.max(0, this.shield + shield); // 确保盾牌值不会为负
    }

    @Override
    public int getBurn() {
        return burn;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getLostHp() {
        return Math.max(0, maxHp - currentHp); // 计算损失的生命值
    }
}