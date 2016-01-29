package com.Objects;

import com.MyUtils.MyVector;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.XmlReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * Created by alect on 1/27/2016.
 */
public class PushBlock extends MovableObject {

    ArrayList<MyVector> positions;


    public PushBlock(Map map) {
        super(map, "SpriteSheet/programmer.png");

    }

    public PushBlock(Map map, XmlReader.Element obj) {
        super(map, "SpriteSheet/programmer.png");

        // parse the xml element and create the pushblock!
        positions = new ArrayList<MyVector>();

        for(XmlReader.Element cell : obj.getChildrenByName("cell")) {
            int x = Integer.parseInt(cell.getAttribute("x"));
            int y = Integer.parseInt(cell.getAttribute("y"));
            this.addPart(x,y);
        }
    }

    private void addPart(int x, int y) {
        MyVector temp = new MyVector(x,y);
        positions.add(temp);
        StaticTiledMapTile tile = new StaticTiledMapTile(splitTiles[2][1]);
        tile.setId(1);
        this.setTile(temp, tile);
    }


    /**
     * Try to move the cat from the given tile to the next tile
     *
     * @param from : where the cats head is
     * @param to   : where the cat is trying to go
     * @return boolean true if succeeded in moving
     */
    @Override
    public boolean tryMove(MyVector from, MyVector to) {

        // calculate direction
        MyVector direction = new MyVector(to);
        direction.sub(from);

        // for each part see if you can move it in the given direction
        for (MyVector spot : positions) {
            MyVector newSpot = new MyVector(spot);
            newSpot.add(direction);

            // check if it hits itself, and ignore if it does
            boolean outer = true;
            if (this.hasAt(newSpot)) outer = false;


            if (outer) {
                // check if map allows movement
                if (!map.canMoveTo(spot, newSpot)) {
                    return false;
                }
            }
        }

        // if we reach here, we can move
        ArrayList<MyVector> newPositions = new ArrayList<MyVector>();
        for (MyVector spot : positions) {

            // the tile in the direction of the current spot
            MyVector nextSpot = new MyVector(spot);
            nextSpot.add(direction);

            // the tile in the opposite direction
            MyVector spotOppositeDirection = new MyVector(spot);
            spotOppositeDirection.sub(direction);

            // only modifly edge tiles
            //if (isEdgeTile(spot)) {
                // only add the new tile ("move the tile") if there is nothing in the direction of it
                if (!this.hasAt(nextSpot)) {
                    // add new tile
                    setTile(nextSpot, new StaticTiledMapTile(splitTiles[2][1]));
                    newPositions.add(nextSpot);
                }
                // only remove the tile if there is no tile in the opposite direction
                if (!this.hasAt(spotOppositeDirection)) {
                    // remove old one
                    removeTile(spot);
                    newPositions.remove(spot);
                }
                else {
                    newPositions.add(spot);
                }

           // else {
            //    newPositions.add(spot);
            //}
        }

        positions.clear();
        positions.addAll(newPositions);
        return true;
    }

    /**
     * Check if the tile exists on the edge of the shape
     *
     * TODO: try to delete this...probably not needed
     * @param position: check if is center tile
     */
    private boolean isCenterTile(MyVector position) {
        MyVector tester = new MyVector(position);

        // test right
        tester.add(1,0);
        if (this.hasAt(tester)) return false;

        // test left
        tester.sub(2,0);
        if (this.hasAt(tester)) return false;

        // test up
        tester.add(1,1);
        if (this.hasAt(tester)) return true;

        // test down
        tester.sub(0,2);
        if (this.hasAt(tester)) return true;

        // if we reach here the tile is surrounded on all 4 sides and is not an edge;
        return false;
    }

    /**
     * undo the move (pop from stack)
     */
    @Override
    public void undo() {

    }

    @Override
    public void update() {

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
        return tryMove(from, to);
    }

}
