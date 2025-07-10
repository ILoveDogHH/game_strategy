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



}