package jedi.game.player;

/**
 * 战斗单位接口：有生命、有攻击力、有暴击/闪避、有攻击速度等。
 * 士兵实现它，Hero 不实现。
 */
public interface IBattleUnit extends IEntity {
    // 生命相关
    int getCurrentHp();
    int getMaxHp();
    void receiveDamage(int damage);
    void heal(int amount);
    void addShield(int value);
    void subHp(int value);
    int getLostHp();
    int getCritMultiplier();
    // 攻击属性
    int getAttack();
    double getCritRate();
    double getDodgeRate();
    double getAttackSpeed();

    boolean isAlive();

    long getProjectileTs();

    void addBurn(int stack);

    void addFreeze(int stack);

    void addHp(int hp);

    void setStunTs(long stunTs);

    void addVulnerable(int stak);

    int getBurn();

}