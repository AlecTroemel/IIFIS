package com.Objects;

import com.MyUtils.MyVector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Map that holds all information about the level
 * handles objects within the maze in addition of cats
 *
 * Created by alect on 1/22/2016.
 */
public class Map {

    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;
    private ArrayList<MovableObject> objects;


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

        // create the list of objects
        objects = new ArrayList<MovableObject>();

        // create cat
        MovableObject cat = new Cat(this);
        objects.add(cat);

        // create all interactable layers
        XmlReader reader = new XmlReader();
        XmlReader.Element root;

        try {
            root = reader.parse(Gdx.files.internal(level));


            Array<XmlReader.Element> objectList = root.getChildByName("objects").getChildrenByName("object");
            for (XmlReader.Element obj : objectList) {
                objects.add(new PushBlock(this, obj));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }





    }

    /**
     * Draw the map
     * @param camera that will be used for rendering
     */
    public void render(OrthographicCamera camera) {
        // update the movable objects
        for(MovableObject mo : objects) {
            mo.update();
        }

        // render maze
        renderer.setView(camera);
        renderer.render();
    }

    /**
     * Clean up
     */
    public void dispose() {
        map.dispose();
        renderer.dispose();

        for(MovableObject mo : objects) {
            mo.dispose();
        }
    }

    /**
     * decide if an object can move to the given position from the given position
     * @param from: starting position of the cell
     * @param to: ending position of the tile
     * @return whether or not the object can move to the given locaiton
     */
    public boolean canMoveTo(MyVector from, MyVector to) {
        // check collision with walls
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("floor");
        TiledMapTileLayer.Cell cell = layer.getCell(to.x,to.y);
        if (cell != null && cell.getTile() != null) {
            // wall tile
            if (cell.getTile().getProperties().get("type").equals("wall")) {
                return false;
            }
            // hole tile
            else if (cell.getTile().getProperties().get("type").equals("hole")) {
                return true;
            }


        }

        // see if the object calling this method can move to the place on the map if it is ocupied by another
        for (MovableObject obj : objects) {
            if (obj.hasAt(to)) {
                if (!obj.allowMapMoveTo(from, to)) {
                    return false;
                }
            }
        }

        // if you reach here, you can move
        return true;

        /*


        // check collision with all the objects in the map
        layer = (TiledMapTileLayer)map.getLayers().get("interact");
        cell = layer.getCell(to.x,to.y);
        if (cell != null && cell.getTile() != null) {
            if (cell.getTile().getProperties().get("type").equals("wall")) {

            }
            return false;
        }
        // check collision with the cat

*/

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
        return Integer.parseInt(map.getProperties().get("catLength", String.class));
    }

    /**
     * @return the layer the cat belongs to in the map
     */
    public TiledMapTileLayer getInteractLayer() {
        return (TiledMapTileLayer)this.map.getLayers().get("interact");
    }

    /**
     * @return scale of the map
     */
    public float getScale() {
        return this.scale;
    }
}
