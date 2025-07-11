package jedi.game.action;



import java.util.ArrayList;
import java.util.List;

public class Action {
    public final long currentTime;

    List<ActionDetail> actionDetails = new ArrayList<>();


    public Action(long currentTime) {
        this.currentTime = currentTime;
    }

    public void addActionDetail(ActionDetail actionDetail){
        this.actionDetails.add(actionDetail);
    }



}