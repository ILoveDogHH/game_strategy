package jedi.game.player;

import jedi.game.action.Action;
import jedi.game.action.ActionDetail;
import jedi.game.battle.BattleContext;
import jedi.game.enums.ActionType;
import jedi.game.enums.EventPriority;
import jedi.game.enums.PositionType;
import jedi.game.event.HeroEvent;

public class Hero extends AbstractEntity {
    public int maxEnergy;
    public int currentEnergy;
    public int energyRecoverPerSecond;


    public Hero(PositionType positionType, String name, int maxEnergy, int energyRecoverPerSecond, Player owner, int projectileTs) {
        super(positionType, name, owner);
        this.positionType = positionType;
        this.name = name;
        this.maxEnergy = maxEnergy;
        this.energyRecoverPerSecond = energyRecoverPerSecond;
        this.owner = owner;
        this.currentEnergy = 0;
        this.projectileTs = projectileTs;
    }


    @Override
    public boolean isAlive() { return true; } // 武将无HP



    @Override
    public double getDodgeRate() {
        return 0;
    }

    @Override
    public void receiveDamage(int damage) { /* 不受伤 */ }


    @Override
    public int getCurrentHp() {
        return 0;
    }

    @Override
    public void addHp(int hp) {

    }

    @Override
    public double getAttackSpeed() {
        return 0;
    }

    @Override
    public int addFreeze(int freeze) {
        return 0;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public void addShield(int shield) {

    }

    @Override
    public int getLostHp() {
        return 0;
    }


    @Override
    public int getenergyRecoverPerSecond() {
        return energyRecoverPerSecond;
    }

    @Override
    public void heal(int amount) {
        // 武将不需要治疗
    }

    @Override
    public boolean canReleaseSkill(){
        return currentEnergy >= maxEnergy;
    }

    @Override
    public void setCurrentEnergy(int currentEnergy){
        this.currentEnergy = Math.max(0, currentEnergy);
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
