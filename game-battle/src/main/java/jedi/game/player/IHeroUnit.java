package jedi.game.player;

import jedi.game.battle.BattleContext;

public interface IHeroUnit {
    int getEnergy();
    int getMaxEnergy();
    void setCurrentEnergy(int energy);
    int getenergyRecoverPerSecond();
    boolean canReleaseSkill();
    void recoverEnergyAndReschedule(BattleContext ctx, IHeroUnit self, Player target, int addEnergy);
}
