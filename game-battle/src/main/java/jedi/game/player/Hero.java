package jedi.game.player;

import jedi.game.action.Action;
import jedi.game.action.ActionDetail;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.EventPriority;
import jedi.game.enums.PositionType;
import jedi.game.enums.TargetType;
import jedi.game.event.HeroEvent;

public class Hero extends AbstractEntity implements IEnergyUnit {
    public int maxEnergy;
    public int currentEnergy;
    public int energyRecoverPerSecond;
    public int projectile_ts;


    public Hero(PositionType positionType, String name, int maxEnergy, int energyRecoverPerSecond, int projectileTs) {
        super(positionType, name);
        this.positionType = positionType;
        this.name = name;
        this.maxEnergy = maxEnergy;
        this.energyRecoverPerSecond = energyRecoverPerSecond;
        this.currentEnergy = 0;
        this.projectile_ts = projectileTs;
    }


    @Override
    public int getCurrentEnergy() {
        return currentEnergy;
    }

    @Override
    public int getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public int getEnergyRecoverPerSecond() {
        return energyRecoverPerSecond;
    }

    @Override
    public boolean canReleaseSkill() {
        return false;
    }

    @Override
    public void setCurrentEnergy(int val) {

    }

    @Override
    public TargetType getTargeType() {
        return TargetType.PRIORITY_FRONT;
    }

    @Override
    public double getCritRate() {
        return 0;
    }

    @Override
    public void recoverEnergyAndReschedule(BattleContext ctx, IEntity enity, Player target, int addEnergy){
        // 1️⃣ 增加气力（但不超过上限）
        this.currentEnergy = Math.min(this.maxEnergy, this.currentEnergy + addEnergy);

        Action action = new Action(ctx.getCurrentTime());

        ActionDetail actionDetail = new ActionDetail(enity, null, ActionType.ENERGY_RECOVER);
        actionDetail.damage.energy = currentEnergy;
        action.addActionDetail(actionDetail);
        ctx.addAction(action);
        // 2️⃣ 判断是否可立即释放技能
        boolean canRelease = this.canReleaseSkill();

        // 3️⃣ 如果能释放技能，则取消已有的气力恢复事件
        if (canRelease) {
            ctx.removeEventsByEntity(HeroEvent.class, enity); // uid + position 匹配
        }

        // 4️⃣ 安排下一次 HeroEvent
        long delay = canRelease ? 0 : 1000; // 立即执行 or 1秒后
        ctx.scheduleEvent(new HeroEvent(ctx.getCurrentTime() + delay, enity, target));
    }



}
