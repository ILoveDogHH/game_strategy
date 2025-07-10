package jedi.game.player;

import jedi.game.enums.PositionType;
import jedi.game.enums.TargetType;
import jedi.game.skill.base.ISkill;
import jedi.game.skill.base.SkillManager;

import jedi.game.enums.PositionType;

public abstract class AbstractEntity implements IEntity {
    protected int uid;
    protected String name;
    protected PositionType positionType;
    protected Player owner;
    protected int projectileTs;
    protected TargetType targetType = TargetType.PRIORITY_FRONT;

    public AbstractEntity(PositionType positionType, String name, Player owner) {
        this.positionType = positionType;
        this.name = name;
        this.owner = owner;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PositionType getPosition() {
        return positionType;
    }

    public Player getOwner() {
        return owner;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public int getProjectileTs() {
        return projectileTs;
    }
}
