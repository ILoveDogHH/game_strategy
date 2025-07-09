package jedi.game.battle;

import jedi.game.action.Action;
import jedi.game.enums.EventPriority;
import jedi.game.enums.SkillTriggerType;
import jedi.game.event.HeroEvent;
import jedi.game.event.SkillEvent;
import jedi.game.event.SoliderEvent;
import jedi.game.event.api.IEvent;
import jedi.game.player.IEntity;
import jedi.game.player.Player;
import jedi.game.skill.base.ISkill;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class BattleTeam {
    private long currentTime = 0;
    private final long maxDuration = 60_000;
    private final PriorityQueue<IEvent> eventQueue = new PriorityQueue<>();
    private final Player playerA;
    private final Player playerB;

    private double speedCoefficient = 1.0;

    public BattleTeam(Player a, Player b) {
        this.playerA = a;
        this.playerB = b;
    }

    public long getCurrentTime() { return currentTime; }
    public double getSpeedCoefficient() { return speedCoefficient; }
    public void scheduleEvent(IEvent event) { eventQueue.add(event); }

    public List<String> run() {
        System.out.println("战斗开始！");
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerA.frontSoldier, playerB));
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerB.frontSoldier, playerA));
//        // 初始化普攻事件
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerA.backSoldier, playerB));
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerB.backSoldier, playerA));


//        // 初始化武将气力恢复事件
        scheduleEvent(new HeroEvent(1000, EventPriority.SKILL, playerA.general, playerB));
        scheduleEvent(new HeroEvent(1000, EventPriority.SKILL, playerB.general, playerA));

        triggerInitialTickableSkills(playerA, playerB);


        BattleContext context = new BattleContext(this);

        while (!isBattleEnd() && currentTime <= maxDuration) {
            if (eventQueue.isEmpty()) break;
            IEvent event = eventQueue.poll();
            currentTime = event.getExecuteTime();

            if (currentTime >= 30_000) {
                speedCoefficient = 1.5;
            }

            event.execute(context);
        }

        for(Action action : context.getActionList()){
            System.out.println(action.toVisualString());
        }
        List<String> result = context.getActionList().stream().map(Action::toVisualString).collect(Collectors.toList());
        if (playerA.isAllDead() && playerB.isAllDead()) {
            result.add("战斗结束，双方同归于尽，平局！");
            System.out.println("战斗结束，双方同归于尽，平局！");
        } else if (playerA.isAllDead()) {
            result.add("战斗结束，" + playerB.name + " 获胜！");
            System.out.println("战斗结束，" + playerB.name + " 获胜！");
        } else if (playerB.isAllDead()) {
            result.add("战斗结束，" + playerA.name + " 获胜！");
            System.out.println("战斗结束，" + playerA.name + " 获胜！");
        } else {
            result.add("战斗结束，达到时间上限，按规则判定胜负（未实现）");
            System.out.println("战斗结束，达到时间上限，按规则判定胜负（未实现）");
        }

        return result;
    }

    private boolean isBattleEnd() {
        return playerA.isAllDead() || playerB.isAllDead();
    }


    public void triggerInitialTickableSkills(Player atker, Player target) {
        for (IEntity unit : atker.getAllEnity()) {
            for (ISkill skill : unit.getSkillManager().getSkills()) {
                if(skill.getTriggerType() == SkillTriggerType.TICKABLE){
                    long next = skill.getTick();
                    if(next <= 0){
                        continue;
                    }
                    scheduleEvent(new SkillEvent(next, EventPriority.BUFF_TICK, unit, target, skill));
                }
            }
        }
    }



    public PriorityQueue<IEvent> getEventQueue() {
        return eventQueue;
    }
}
