package com.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by alect on 1/21/2016.
 */
public class CatBodyTile extends Tile
{
    public CatBodyTile(TextureRegion textureRegion, Vector2 location, int size)
    {
        super(textureRegion, location, size);
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

    /**
     *
     * @param direction
     * @return false, cannot move from another body part
     */
    public boolean canMoveFrom(Vector2 direction)
    {
        return false;
    }

    /**
     *
     * @param direction
     * @return false, cannot move to another body part
     */
    public boolean canMoveTo(Vector2 direction)
    {
        return false;
    }


}
