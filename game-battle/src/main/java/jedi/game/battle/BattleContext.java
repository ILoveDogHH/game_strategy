package jedi.game.battle;

import jedi.game.action.Action;
import jedi.game.event.api.IEvent;
import jedi.game.event.api.IUnitLinkedEvent;
import jedi.game.player.IEntity;

import java.util.ArrayList;
import java.util.List;

public class BattleContext {
    private BattleTeam battle;
    private final List<Action> actionList = new ArrayList<>();


    public BattleContext(BattleTeam battle) {
        this.battle = battle;
    }

    public long getCurrentTime() {
        return battle.getCurrentTime();
    }

    public double getSpeedCoefficient() {
        return battle.getSpeedCoefficient();
    }

    public void scheduleEvent(IEvent event) {
        battle.scheduleEvent(event);
    }


    public void addAction(Action action){
        actionList.add(action);
    }


    public List<Action> getActionList() {
        return actionList;
    }

    public int removeEventsByEntity(
            Class<? extends IEvent> clazz,
            IEntity targetEntity
    ) {
        int beforeSize = battle.getEventQueue().size();
        battle.getEventQueue().removeIf(evt ->
                clazz.isInstance(evt) &&
                        evt instanceof IUnitLinkedEvent &&
                        sameEntity(((IUnitLinkedEvent) evt).getEntity(), targetEntity)
        );
        return beforeSize - battle.getEventQueue().size();
    }

    private boolean sameEntity(IEntity a, IEntity b) {
        return a.getUid() == b.getUid() && a.getPosition() == b.getPosition();
    }

}
