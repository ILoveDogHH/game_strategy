package jedi.game.player;

import jedi.game.battle.BattleContext;
import jedi.game.enums.TargetType;

// IEnergyUnit：有气力（武将有）
public interface IEnergyUnit  extends IEntity{
    int getCurrentEnergy();
    int getMaxEnergy();
    int getEnergyRecoverPerSecond();
    boolean canReleaseSkill();
    void setCurrentEnergy(int val);

    void recoverEnergyAndReschedule(BattleContext ctx, IEntity enity, Player target, int addEnergy);


}