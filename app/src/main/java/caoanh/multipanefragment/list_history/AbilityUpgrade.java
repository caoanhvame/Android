package caoanh.multipanefragment.list_history;

import java.io.Serializable;

/**
 * Created by anhhpc on 10/4/2017.
 */

public class AbilityUpgrade implements Serializable{
    private int ability;
    private int time;
    private int level;

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
