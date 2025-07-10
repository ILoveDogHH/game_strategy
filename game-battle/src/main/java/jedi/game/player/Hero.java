package jedi.game.player;

import jedi.game.action.Action;
import jedi.game.action.ActionDetail;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.PositionType;
import jedi.game.event.HeroEvent;
import jedi.game.skill.base.SkillManager;

public class Hero extends AbstractEntity implements IHeroUnit {
    public int maxEnergy;
    public int currentEnergy;
    public int energyRecoverPerSecond;
    protected SkillManager skillManager = new SkillManager();

    public Hero(PositionType positionType, String name, int maxEnergy, int energyRecoverPerSecond, Player owner, int projectileTs) {
        super(positionType, name, owner);
        this.maxEnergy = maxEnergy;
        this.energyRecoverPerSecond = energyRecoverPerSecond;
        this.projectileTs = projectileTs;
        this.currentEnergy = 0;
    }

    @Override public int getUid() { return uid; }
    @Override public boolean isAlive() { return true; }
    @Override public int getEnergy() { return currentEnergy; }
    @Override public int getMaxEnergy() { return maxEnergy; }
    @Override public void setCurrentEnergy(int energy) { this.currentEnergy = Math.max(0, energy); }
    @Override public int getenergyRecoverPerSecond() { return energyRecoverPerSecond; }
    @Override public boolean canReleaseSkill() { return currentEnergy >= maxEnergy; }

    @Override
    public void recoverEnergyAndReschedule(BattleContext ctx, IEntity entity, Player target, int addEnergy) {
        this.currentEnergy = Math.min(this.maxEnergy, this.currentEnergy + addEnergy);
        Action action = new Action(ctx.getCurrentTime());
        ActionDetail actionDetail = new ActionDetail(entity, null, ActionType.ENERGY_RECOVER);
        actionDetail.damage.energy = currentEnergy;
        action.addActionDetail(actionDetail);
        ctx.addAction(action);
        if (canReleaseSkill()) {
            ctx.removeEventsByEntity(HeroEvent.class, entity);
        }
        long delay = canReleaseSkill() ? 0 : 1000;
        ctx.scheduleEvent(new HeroEvent(ctx.getCurrentTime() + delay, entity, target));
    }

    public SkillManager getSkillManager() { return skillManager; }
}
