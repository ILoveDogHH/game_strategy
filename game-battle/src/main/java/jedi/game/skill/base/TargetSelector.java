package jedi.game.skill.base;

import jedi.game.enums.TargetType;
import jedi.game.player.IBattleUnit;
import jedi.game.player.IEntity;
import jedi.game.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TargetSelector {


    public static List<IEntity> selectTargets(IEntity atker, Player enemy, TargetType type) {
        switch (type) {
            case SELF:
                return Arrays.asList(atker);
            case FRONT:
                if (enemy.frontSoldier.isAlive()) return Collections.singletonList(enemy.frontSoldier);
                return Collections.emptyList();

            case BACK:
                if (enemy.backSoldier.isAlive()) return Collections.singletonList(enemy.backSoldier);
                return Collections.emptyList();

            case ALL:
                return enemy.getAllEnity().stream()
                        .filter(e -> e instanceof IBattleUnit)
                        .map(e -> (IBattleUnit) e)
                        .filter(IBattleUnit::isAlive)
                        .map(e -> (IEntity) e)
                        .collect(Collectors.toList());

            case RANDOM:
                List<IBattleUnit> aliveUnits = enemy.getAllEnity().stream()
                        .filter(e -> e instanceof IBattleUnit)
                        .map(e -> (IBattleUnit) e)
                        .filter(IBattleUnit::isAlive)
                        .collect(Collectors.toList());

                if (aliveUnits.isEmpty()) return Collections.emptyList();
                return Collections.singletonList(aliveUnits.get(new Random().nextInt(aliveUnits.size())));

            case LOWEST_HP:
                return enemy.getAllEnity().stream()
                        .filter(e -> e instanceof IBattleUnit)
                        .map(e -> (IBattleUnit) e)
                        .filter(IBattleUnit::isAlive)
                        .min(Comparator.comparingInt(IBattleUnit::getCurrentHp))
                        .map(e -> Collections.singletonList((IEntity) e))
                        .orElse(Collections.emptyList());

            case PRIORITY_FRONT:
                if (enemy.frontSoldier.isAlive()) return Collections.singletonList(enemy.frontSoldier);
                if (enemy.backSoldier.isAlive()) return Collections.singletonList(enemy.backSoldier);
                return Collections.emptyList();
        }

        return Collections.emptyList();
    }

}
