package com.Objects;

import com.MyUtils.MyVector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


/**
 * Created by alect on 1/22/2016.
 */
public class Map {

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private float scale;

    //TODO: put this is a java properties file
    private int tileWidth = 32;

    /**
     * constructor to create level
     * @param level: tmx file to load
     */
    public Map(String level) {
        // create map
        map = new TmxMapLoader().load(level);

        // adjust map rendering to screen size
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(0);
        scale = Gdx.graphics.getWidth()/((float)layer.getWidth() * tileWidth) - 0.001f;

        // set up renderer
        renderer = new OrthogonalTiledMapRenderer(map,scale);
    }

    /**
     * Draw the map
     * @param camera
     */
    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    /**
     * Clean up
     */
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    /**
     * decide if an object can move to the given position from the given position
     * @param from: starting position of the cell
     * @param to: ending position of the tile
     * @return whether or not the object can move to the given locaiton
     */
    public boolean canMoveTo(MyVector from, MyVector to) {
        // check collision with walls
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("walls");
        TiledMapTileLayer.Cell cell = layer.getCell(to.x,to.y);
        if (cell != null && cell.getTile() != null) return false;

        // check collision with the cat
        layer = (TiledMapTileLayer)map.getLayers().get("cat");
        cell = layer.getCell(to.x,to.y);
        if (cell != null && cell.getTile() != null) return false;

        // if you reach here, you can move
        return true;
    }

    /**
     * @return the starting position of the cat
     */
    public MyVector getStartingPosition() {
        return new MyVector(Integer.parseInt(map.getProperties().get("startPositionX",String.class)),
                Integer.parseInt(map.getProperties().get("startPositionY",String.class)));
    }

    /**
     * @return the length of the cat
     */
    public int getCatLength() {
        int temp = Integer.parseInt(map.getProperties().get("catLength", String.class));
        return temp;
    }

    /**
     * @return the layer the cat belongs to in the map
     */
    public TiledMapTileLayer getCatLayer() {
        return (TiledMapTileLayer)this.map.getLayers().get("cat");
    }

    /**
     * @return scale of the map
     */
    public float getScale() {
        return this.scale;
    }
}
