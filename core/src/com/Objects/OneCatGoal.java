package com.Objects;

/**
 * Created by alect on 2/3/2016.
 */
public class OneCatGoal extends Goal {

    private int spacesLeft;

    @Override
    public void init(Map map) {
        spacesLeft = map.getCatLength();
    }


    @Override
    public void score() {

    }

    @Override
    public boolean isGoal() {
        return spacesLeft == 0;
    }

    @Override
    public void update(Map map) {
        this.spacesLeft = map.getCat().getSpacesLeft();
    }

    @Override
    public String toString() {
        return "spaces left: " + this.spacesLeft;
    }

}
