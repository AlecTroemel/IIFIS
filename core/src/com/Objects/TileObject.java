package com.Objects;

import com.Screens.GameScreen;
import com.Tiles.Tile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * Created by alect on 1/21/2016.
 *
 * Abstract class for any thing that has multiple tiles
 */
public abstract class TileObject
{
    protected LinkedList<Tile> parts;
    protected TextureAtlas textureAtlas;
    protected GameScreen gameScreen; // the gameManager this tileOBject belongs to

    protected int width;

    public TileObject(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
        parts = new LinkedList<Tile>();
        width = 32;
    }

    public void setWidth(int width) {
        this.width = width;
        for(Tile t : parts) {
            t.setSize(width);
        }

    }

    public void addTile(Tile t)
    {
        parts.add(t);
    }

    public void render(SpriteBatch batch) {
        for(Tile t : parts) {
            t.draw(batch);
        }
    }

    public boolean hasAt(Vector2 location) {
        boolean match = false;
        for(Tile t : parts) {
            if (t.getLocation().equals(location)) {
                match = true;
            }
        }
        return match;
    }

    public Tile getAt(Vector2 location) {
        Tile temp = null;
        for(Tile t : parts) {
            if (t.getLocation().equals(location)) {
                temp = t;
            }
        }
        return temp;
    }

    public void dispose() {
        textureAtlas.dispose();
    }
}
