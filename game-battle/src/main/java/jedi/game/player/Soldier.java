package jedi.game.player;

import jedi.game.enums.PositionType;
import jedi.game.skill.base.SkillManager;

public class Soldier extends AbstractEntity implements IBattleUnit {
    public int maxHp;
    public int currentHp;
    public int attack;
    public double critRate;
    public double critMultiplier;
    public double dodgeRate;
    public double attackSpeed;
    public int freeze = 0;
    public int shield = 0;
    protected SkillManager buffManager = new SkillManager();

    public Soldier(PositionType positionType, String name, int maxHp, int attack, double critRate, double critMultiplier,
                   double dodgeRate, double attackSpeed, Player owner, int projectileTs) {
        super(positionType, name, owner);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.critRate = critRate;
        this.critMultiplier = critMultiplier;
        this.dodgeRate = dodgeRate;
        this.attackSpeed = attackSpeed;
        this.projectileTs = projectileTs;
    }

    @Override public int getUid() { return uid; }
    @Override public boolean isAlive() { return currentHp > 0; }
    @Override public int getCurrentHp() { return currentHp; }
    @Override public int getMaxHp() { return maxHp; }
    @Override public int getAttack() { return attack; }
    @Override public void receiveDamage(int damage) { currentHp = Math.max(0, currentHp - damage); }
    @Override public void heal(int amount) { this.currentHp = Math.min(this.maxHp, this.currentHp + amount); }
    @Override public void subHp(int hp) { this.currentHp = Math.max(0, currentHp - hp); }
    @Override public void addHp(int hp) { this.currentHp += hp; }
    @Override public void addShield(int shield) { this.shield += shield; }
    @Override public double getCritRate() { return critRate; }
    @Override public double getDodgeRate() { return dodgeRate; }
    @Override public double getAttackSpeed() { return attackSpeed * (1 - freeze * 0.1); }
    @Override public int addFreeze(int freeze) { this.freeze += freeze; return this.freeze; }

    public SkillManager getBuffManager() { return buffManager; }
}
