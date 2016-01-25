package com.Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by alect on 1/21/2016.
 * The Abstract class tile encompasses a tile in the game that may be displayed, moved to or from
 */
public abstract class Tile
{
    // the sprite associated with this tile
    private Sprite sprite;

    // the location of the tile in the map grid
    private Vector2 location;
    private int size;

    public Tile(TextureRegion textureRegion, Vector2 location, int size) {
        // set the sprite to the given texture region
        this.sprite = new Sprite(textureRegion);
        sprite.setSize(size, size);
        this.sprite.setPosition(location.x * size,location.y * size);
        this.location = location;

    }

    public void setSize(int size)
    {
        sprite.setSize(size, size);
        this.size = size;
    }

    public Vector2 getLocation()
    {
        return location;
    }

    public void setSprite(TextureRegion textureRegion)
    {
        sprite.setRegion(textureRegion);
    }

    public void rotate90(boolean clockwise) {
        sprite.rotate90(clockwise);

    }

    public void draw(SpriteBatch batch) {
        /* update the location of the sprite */
        sprite.setPosition(location.x,location.y);
        sprite.draw(batch);
    }

    /**
     * does the appropriate thing when something moves to this tiles location
     * @param direction: moving from
     */
    public abstract void onMoveFrom(Vector2 direction);

    /**
     * does the appropriate thing when this tile moves in the given location
     * @param direction: moving to
     */
    public abstract void onMoveTo(Vector2 direction);

    /**
     *
     * @param direction
     * @return true if something can move from this tiles location, false otherwise
     */
    public abstract boolean canMoveFrom(Vector2 direction);

    /**
     *
     * @param direction
     * @return true if something can move to this tiles location, false otherwise
     */
    public abstract boolean canMoveTo(Vector2 direction);
}
