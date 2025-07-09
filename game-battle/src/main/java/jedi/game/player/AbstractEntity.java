package jedi.game.player;

import jedi.game.enums.PositionType;
import jedi.game.skill.base.ISkill;
import jedi.game.skill.base.SkillManager;

/**
 * 抽象实体类，封装通用字段与方法。
 */
public abstract class AbstractEntity implements IEntity {
    protected int uid;
    protected PositionType positionType;
    protected String name;
    protected Player owner;
    protected int projectileTs;
    protected long stun_ts;
    protected int vulnerable;
    protected int burn;

    protected SkillManager buffManager = new SkillManager();

    public AbstractEntity(PositionType positionType, String name, Player owner) {
        this.positionType = positionType;
        this.name = name;
        this.owner = owner;
    }


    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public int getPosition() {
        return positionType.getValue();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public SkillManager getSkillManager() {
        return buffManager;
    }



    @Override
    public int getProjectileTs() {
        return projectileTs;
    }


    @Override
    public void setProjectileTs(int projectileTs) {
        this.projectileTs = projectileTs;
    }



    @Override
    public void heal(int amount) {}

    @Override
    public void setCurrentEnergy(int currentEnergy) {}

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    @Override
    public void setStunTs(long stunTs) {
        this.stun_ts = stunTs;
    }

    @Override
    public void addVulnerable(long vulnerable) {
        this.vulnerable += vulnerable;
    }

    @Override
    public void addBurn(int burn) {
        this.burn += burn;
    }
}