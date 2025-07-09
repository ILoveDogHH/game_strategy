package jedi.game.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Random {


    /**
     * 判断是否命中一个 [0.0, 1.0] 的概率（如 0.25 表示 25%）
     */
    public static boolean isRand(double probability) {
        if (probability <= 0) return false;
        if (probability >= 1) return true;
        return ThreadLocalRandom.current().nextDouble() < probability;
    }



    /**
     * 返回一个 0 到 1 之间的随机浮点数
     */
    public static double random() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * 返回一个 [min, max) 区间的随机整数
     */
    public static int randomInt(int minInclusive, int maxExclusive) {
        return ThreadLocalRandom.current().nextInt(minInclusive, maxExclusive);
    }

}
