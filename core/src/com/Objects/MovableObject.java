package com.Objects;

import com.MyUtils.MyVector;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.LinkedList;
import java.util.Stack;

/**
 * abstract class that is any movable object within the maze
 *
 * including cat(s), boxes, other stuff
 *
 * Created by alect on 1/27/2016.
 */
public abstract class MovableObject {

    protected TextureRegion[][] splitTiles;
    protected Texture tiles;
    protected Map map;

    //TODO: put this is a java properties file
    protected int tileWidth = 32;

    public MovableObject(Map map,String tileSheet) {
        this.map = map;

        tiles = new Texture(Gdx.files.internal(tileSheet));
        splitTiles = TextureRegion.split(tiles, tileWidth, tileWidth);
    }


    /**
     * clean up method
     */
    public void dispose() {
        tiles.dispose();
    }

    /**
     * Try to move the cat from the given tile to the next tile
     * @param from: where the cats head is
     * @param to: where the cat is trying to go
     * @return boolean true if succeeded in moving
     */
    public abstract boolean tryMove(MyVector from, MyVector to);

    /**
     * undo the move (pop from stack)
     */
    public abstract void undo();

    /**
     * called each frame
     */
    public abstract void update();

    /**
     * Set the map tile
     * @param pos: position of cat part
     * @param tile: tile to change that part to
     */
    protected void setTile(MyVector pos, StaticTiledMapTile tile) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tile);
        map.getInteractLayer().setCell(pos.x,pos.y,cell);
    }

    /**
     * remove the cat tile at the given location
     * @param pos of part to remove
     */
    protected void removeTile(MyVector pos) {
        map.getInteractLayer().getCell(pos.x,pos.y).setTile(null);
    }

    protected abstract boolean hasAt(MyVector position);

    protected  abstract boolean allowMapMoveTo(MyVector from, MyVector to);
}
