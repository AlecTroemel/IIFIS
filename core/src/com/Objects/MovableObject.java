package com.Objects;

import com.Screens.GameScreen;

/**
 * Created by alect on 1/21/2016.
 *
 * Abstract class for objects that can move in the game
 */
public abstract class MovableObject extends TileObject
{
    public MovableObject(GameScreen gameScreen)
    {
        super(gameScreen);
    }

    public abstract boolean tryMoveUp();

    public abstract boolean tryMoveDown();

    public abstract boolean tryMoveLeft();

    public abstract boolean tryMoveRight();
}
