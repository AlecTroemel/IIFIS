package com.Objects;

import com.MyUtils.MyVector;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by alect on 1/22/2016.
 */
public class Cat {

    private LinkedList<MyVector> positions;
    private Stack<MyVector> pastPositions;
    private TextureRegion[][] splitTiles;
    private Texture tiles;
    private Map map;

    //TODO: put this is a java properties file
    private int tileWidth = 32;

    /**
     * Constructor of cat
     * @param map: the map the cat belongs to
     */
    public Cat(Map map) {
        this.map = map;
        this.pastPositions = new Stack<MyVector>();
        this.positions = new LinkedList<MyVector>();

        // set up tiles of cat
        tiles = new Texture(Gdx.files.internal("SpriteSheet/programmer.png"));
        splitTiles = TextureRegion.split(tiles, tileWidth, tileWidth);

        // get cat attributes from the map
        MyVector startingPosition = map.getStartingPosition();

        // Add cat to map
        for(int i = 0; i < map.getCatLength(); i++) {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(splitTiles[0][1]));
            map.getCatLayer().setCell(startingPosition.x,startingPosition.y - i,cell);
            positions.addLast(new MyVector(startingPosition.x, startingPosition.y - i));
        }
    }

    /**
     *  Update all things related to the cat
     *  handle input and move the cat
     *  TODO: play animations
     */
    private MyVector currentTouch;
    private boolean movingCat;
    public void update() {
        MyVector to = new MyVector(positions.getFirst());
        float pixelWidth = tileWidth * map.getScale();

        // touch input
        if (Gdx.input.justTouched()) {
            currentTouch = new MyVector((int)(Gdx.input.getX() / pixelWidth ), (int)((Gdx.graphics.getHeight() - Gdx.input.getY()) / pixelWidth));
            if(positions.getFirst().equals(currentTouch)) {
                movingCat = true;
            }
            else {
                movingCat = false;
            }
        }
        else if (Gdx.input.isTouched()) {
            MyVector newTouch = new MyVector((int)(Gdx.input.getX() / pixelWidth), (int)((Gdx.graphics.getHeight() - Gdx.input.getY()) / pixelWidth));

            if (movingCat) {
                MyVector direction = new MyVector(newTouch);
                direction.sub(currentTouch);

                if (validDirection(direction)) {
                    to.add(direction);

                    if(tryMove(positions.getFirst(),to)) {
                        currentTouch = newTouch;
                    }
                }
            }
        }

        // keyboard input
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            to.add(-1,0);
            tryMove(positions.getFirst(),to);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            to.add(1,0);
            tryMove(positions.getFirst(),to);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            to.add(0,1);
            tryMove(positions.getFirst(),to);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            to.add(0,-1);
            tryMove(positions.getFirst(),to);
        }
    }

    /**
     * Helper method for touch input
     * @param dir
     * @return
     */
    private boolean validDirection(MyVector dir) {
        if(dir.equals(0,1)) return true;
        if(dir.equals(0,-1)) return true;
        if(dir.equals(1,0)) return true;
        if(dir.equals(-1,0)) return true;
        return false;
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
    public boolean tryMove(MyVector from, MyVector to) {

        // check if going backwards
        if (!pastPositions.isEmpty() && to.equals(positions.get(1))) {
            // remove head
            removeTile(positions.removeFirst());

            // create new tail from past moves
            setTile(pastPositions.peek(), new StaticTiledMapTile(splitTiles[0][1]));
            positions.addLast(pastPositions.pop());
            return true;
        }
        // check if map allows movement
        if (!map.canMoveTo(from, to)) {
            return false;
        }

        // if we reach here, we can move to the next tile
        setTile(to, new StaticTiledMapTile(splitTiles[0][1]));
        positions.addFirst(to);
        pastPositions.push(positions.removeLast());
        removeTile(pastPositions.peek());
        return true;
    }

    /**
     * Set the map tile
     * @param pos: position of cat part
     * @param tile: tile to change that part to
     */
    private void setTile(MyVector pos, StaticTiledMapTile tile) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tile);
        map.getCatLayer().setCell(pos.x,pos.y,cell);
    }

    /**
     * remove the cat tile at the given location
     * @param pos of part to remove
     */
    private void removeTile(MyVector pos) {
        map.getCatLayer().getCell(pos.x,pos.y).setTile(null);
    }

    public int getLength() {
        return this.positions.size();
    }

}
