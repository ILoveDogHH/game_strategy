package jedi.game.mysql;

import java.util.Arrays;
import java.util.Objects;

public class Keys {


    private final Object[] key;
    private int hash;

    public Keys(Object[] key) {
        this.key = key;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Keys) {
            return Objects.deepEquals(this.key, ((Keys) obj).key);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int h = hash;
        if (h == 0 && key.length > 0) {
            h += Arrays.hashCode(key);
            hash = h;
        }
        return h;
    }


    public Object[] getKey() {
        return key;
    }
}
