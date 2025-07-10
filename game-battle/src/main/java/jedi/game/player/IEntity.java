package jedi.game.player;

import jedi.game.battle.BattleContext;
import jedi.game.enums.TargetType;
import jedi.game.skill.base.SkillManager;

/**
 * 战斗单位基础接口。
 * 所有参与战斗的个体（如士兵、武将）均应实现该接口。
 */
public interface IEntity {
    int getUid();
    int getPosition();
    boolean isAlive();
    double getDodgeRate();
    void receiveDamage(int damage);
    String getName();
    void heal(int amount);
    Player getOwner();
    void setOwner(Player owner);
    SkillManager getSkillManager();
    int getCurrentHp();
    void addHp(int hp);
    double getAttackSpeed();
    int addFreeze(int freeze);
    int getMaxHp();
    int getProjectileTs();
    void setProjectileTs(int projectileTs);
    void addShield(int shield);
    void setStunTs(long stunTs);
    void addVulnerable(long vulnerable);
    void addBurn(int burn);
    int getBurn();
    int getAttack();

    int getLostHp();

    default TargetType getTargeType() { return TargetType.PRIORITY_FRONT; }

    default boolean canReleaseSkill() { return false; }

    default void setCurrentEnergy(int currentEnergy) {}

    default void recoverEnergyAndReschedule(BattleContext ctx, IEntity self, Player target, int addEnergy) {}

    default void subHp(int hp){};

    default int getenergyRecoverPerSecond(){return 0;}


    default double getCritRate(){return 0.0;}

    default int getCritMultiplier() {
        return 0;
    }


}