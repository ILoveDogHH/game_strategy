package jedi.game.skill.base;

import jedi.game.enums.TargetType;
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
                return enemy.getAllSoldiers().stream()
                        .filter(IEntity::isAlive)
                        .collect(Collectors.toList());

            case RANDOM:
                List<IEntity> alive = enemy.getAllSoldiers().stream()
                        .filter(IEntity::isAlive)
                        .collect(Collectors.toList());
                if (alive.isEmpty()) return Collections.emptyList();
                return Collections.singletonList(alive.get(new Random().nextInt(alive.size())));

            case LOWEST_HP:
                return enemy.getAllSoldiers().stream()
                        .filter(IEntity::isAlive)
                        .min(Comparator.comparingInt(s -> s.getCurrentHp()))
                        .map(Collections::singletonList)
                        .orElse(Collections.emptyList());

            case PRIORITY_FRONT:
                if (enemy.frontSoldier.isAlive()) return Collections.singletonList(enemy.frontSoldier);
                if (enemy.backSoldier.isAlive()) return Collections.singletonList(enemy.backSoldier);
                return Collections.emptyList();
        }

        return Collections.emptyList();
    }

}
