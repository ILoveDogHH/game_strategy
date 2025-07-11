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

    public List<Action> run() {
        System.out.println("战斗开始！");
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerA.frontSoldier, playerB));
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerB.frontSoldier, playerA));
        // 初始化普攻事件
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerA.backSoldier, playerB));
        scheduleEvent(new SoliderEvent(0, EventPriority.ATTACK, playerB.backSoldier, playerA));


       // 初始化武将气力恢复事件
        scheduleEvent(new HeroEvent(1000, playerA.general, playerB));
        scheduleEvent(new HeroEvent(1000, playerB.general, playerA));

        // triggerInitialTickableSkills(playerA, playerB);

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
        return context.getActionList();
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
                    scheduleEvent(new SkillEvent(next, unit, target, skill));
                }
            }
        }
    }



    public PriorityQueue<IEvent> getEventQueue() {
        return eventQueue;
    }
}
