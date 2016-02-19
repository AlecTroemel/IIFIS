package com.Objects;

/**
 * Created by alect on 2/3/2016.
 */
public abstract class Goal {

    public abstract void init(Map map);

    public abstract void score();

    public abstract boolean isGoal();

    public abstract void update(Map map);

    public abstract String toString();

}
