package jedi.game.player;

public interface IBattleUnit {
    int getCurrentHp();
    int getMaxHp();
    int getAttack();
    void receiveDamage(int damage);
    void heal(int amount);
    void subHp(int hp);
    void addHp(int hp);
    void addShield(int shield);
    double getCritRate();
    double getDodgeRate();
    double getAttackSpeed();
    int addFreeze(int freeze);
}
