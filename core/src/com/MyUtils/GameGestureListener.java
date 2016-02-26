package com.MyUtils;

import com.Objects.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by alec Troemel on 1/25/2016.
 *
 * Handles all input for the game screen
 */
public class GameGestureListener implements GestureListener {

    private MyVector initialTouchPosition;
    private float initialScale;
    private boolean movingCat;
    private MyCamera camera;
    private Map map;

    private int tileWidth = 32;

    /**
     * constructor
     * @param camera that we will be working with
     * @param map the level
     */
    public GameGestureListener(MyCamera camera, Map map) {
        this.camera = camera;
        this.map = map;
        this.movingCat = false;
        this.initialScale = 1;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // get the map cell of where the user touches
        Vector3 temp = camera.getCamera().unproject(new Vector3(x,y,0));
        initialTouchPosition = getGridPosition(temp.x, temp.y);
        movingCat = map.getCatHeadPosition().equals(initialTouchPosition);

        // for zoom
        initialScale = camera.getCamera().zoom;

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // moving cat
        if (movingCat) {
            Vector3 temp = camera.getCamera().unproject(new Vector3(x, y, 0));
            MyVector newTouchPosition = getGridPosition(temp.x, temp.y);
            MyVector direction = new MyVector(newTouchPosition);
            direction.sub(initialTouchPosition);

            Gdx.app.log("pan", direction.x + " " + direction.y);

            if (validDirection(direction)) {
                boolean tempbool = map.getCat().tryMove(initialTouchPosition, newTouchPosition);
                initialTouchPosition = newTouchPosition;
                Gdx.app.log("pan", "x: " + newTouchPosition.x + " y:" + newTouchPosition.y + " " + tempbool);
            }
        }
        // panning camera
        else {
            Vector2 position = new Vector2(camera.getMovingTo());
            position.add(-deltaX, deltaY);

            Gdx.app.log("pan", "x: " + position.x + " y:" + position.y);
            camera.moveTo(position);
        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        // new center of camera is wherever the cat head is
        if (movingCat) {
            camera.setCenter(new Vector2(camera.actualPos(map.getCatHeadPosition().x), camera.actualPos(map.getCatHeadPosition().y)));
        }

        camera.moveTo(camera.getCenter());
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        float ratio = initialDistance/distance;
        camera.getCamera().zoom = initialScale * ratio;
        return false;
    }

    /**
     * Get the grid position at the given point
     * @param x position of grid
     * @param y position of grid
     * @return MyVector that is the map grid position
     */
    private MyVector getGridPosition(float x, float y) {
        return new MyVector((int)(x / (tileWidth * map.getScale())), (int)(((y) / (tileWidth * map.getScale()))));
    }

    /**
     * Helper method for touch input
     * checks if direction is either up down left or right
     * @param dir: direction to check
     * @return true if it is a valid direction
     */
    private boolean validDirection(MyVector dir) {
        if (dir.equals(0, 1)) return true;
        if (dir.equals(0, -1)) return true;
        if (dir.equals(1, 0)) return true;
        if (dir.equals(-1, 0)) return true;
        return false;
    }

    // ***************  NOT USED *************** \\
    @Override
    public boolean tap(float x, float y, int count, int button) {
        //Gdx.app.log("input", "tap");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        //Gdx.app.log("input", "long press");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //Gdx.app.log("input", "fling");
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
