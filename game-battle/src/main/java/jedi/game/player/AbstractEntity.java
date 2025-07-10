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


    protected SkillManager buffManager = new SkillManager();

    public AbstractEntity(PositionType positionType, String name) {
        this.positionType = positionType;
        this.name = name;
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
    public SkillManager getSkillManager() {
        return buffManager;
    }



}