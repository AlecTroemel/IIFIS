package com.Tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by alect on 1/21/2016.
 */
public class FloorTile extends Tile
{

    public FloorTile(TextureRegion textureRegion, Vector2 location)
    {
        super(textureRegion, location,32);
    }

    public void onMoveFrom(Vector2 direction)
    {
        //TODO
        // nothing happens yet

        //animation?

        // sounds?
    }

    public void onMoveTo(Vector2 direction)
    {
        //TODO
        // nothing happens yet

        //animation?

        // sounds?
    }

    public boolean canMoveFrom(Vector2 direction)
    {
        return true;
    }

    public boolean canMoveTo(Vector2 direction)
    {
        return true;
    }
}
