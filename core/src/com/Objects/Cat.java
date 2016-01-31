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
 * The cat that can be clicked and dragged
 *
 * Created by alect on 1/22/2016.
 */
public class Cat extends MovableObject {
    protected LinkedList<MyVector> positions;
    protected Stack<MyVector> pastPositions;


    /**
     * Constructor of cat
     * @param map: the map the cat belongs to
     */
    public Cat(Map map) {
        super(map, "SpriteSheet/programmer.png");

        this.pastPositions = new Stack<MyVector>();
        this.positions = new LinkedList<MyVector>();

        // get cat attributes from the map
        MyVector startingPosition = map.getStartingPosition();

        // Add cat to map
        MyVector currentPos = new MyVector(startingPosition);

        // add the first tile
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(splitTiles[0][1]));
        map.getInteractLayer().setCell(currentPos.x,currentPos.y,cell);
        positions.addLast(new MyVector(currentPos.x, currentPos.y));

        currentPos.sub(0,1);
        for(int i = 1; i < map.getCatLength(); i++) {
            TiledMapTileLayer.Cell innercell = new TiledMapTileLayer.Cell();
            innercell.setTile(new StaticTiledMapTile(splitTiles[0][1]));
            map.getInteractLayer().setCell(currentPos.x,currentPos.y, innercell);
            positions.addLast(new MyVector(currentPos.x, currentPos.y));

            MyVector nextPos = getNextLoadingPosition(currentPos);

            currentPos.set(nextPos);
        }

        Gdx.app.log("cat done", "the cat is done being made");
    }

    private MyVector getNextLoadingPosition(MyVector lastPos) {
        MyVector position = new MyVector(lastPos);

        // try move up
        position.add(0,1);
        if (map.canMoveTo(lastPos, position) && !hasAt(position)) {
            return position;
        }
        // try to move left
        position.sub(1,1);
        if (map.canMoveTo(lastPos, position) && !hasAt(position)) {
            return position;
        }
        // try to move right
        position.add(2,0);
        if (map.canMoveTo(lastPos, position) && !hasAt(position)) {
            return position;
        }

        //else move down
        position.sub(1,1);
        return position;
    }

    /**
     *  Update all things related to the cat
     *  handle input and move the cat
     *  TODO: play animations
     */
    public void update() {
        MyVector to = new MyVector(positions.getFirst());

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
     * Try to move the cat from the given tile to the next tile
     * @param from: where the cats head is
     * @param to: where the cat is trying to go
     * @return boolean true if succeeded in moving
     */
    public boolean tryMove(MyVector from, MyVector to) {

        // check if going backwards
        if (!pastPositions.isEmpty() && to.equals(positions.get(1))) {
            this.undo();
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
     * @return the vector location of the cats head
     */
    public MyVector getHeadLocation() {
        return this.positions.getFirst();
    }

    /**
     * undo the move (pop from stack)
     */
    @Override
    public void undo() {
        // remove head
        removeTile(positions.removeFirst());

        // create new tail from past moves
        setTile(pastPositions.peek(), new StaticTiledMapTile(splitTiles[0][1]));
        positions.addLast(pastPositions.pop());
    }

    /**
     * @return the length of the cat
     */
    public int getLength() {
        return this.positions.size();
    }

    @Override
    public boolean hasAt(MyVector position) {
        for (MyVector spot : this.positions) {
            if (position.equals(spot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean allowMapMoveTo(MyVector from, MyVector to) {
        return false;
    }


}
