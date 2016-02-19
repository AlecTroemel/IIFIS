package com.MyUtils;

import com.Screens.LevelEditorScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by alect on 2/5/2016.
 */
public class LevelEditorGestureListener implements GestureListener {

    private MyCamera camera;
    private MyVector initialTouchPosition;
    private float scale;

    private boolean creating;

    private LevelEditorScreen screen;

    private int tileWidth = 32;


    public LevelEditorGestureListener(MyCamera camera, LevelEditorScreen screen) {
        this.camera = camera;
        this.scale = 1;
        this.screen = screen;
        this.creating = true;
    }

    /**
     * @param x
     * @param y
     * @param pointer
     * @param button
     */
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        // get the map cell of where the user touches
        Vector3 temp = camera.getCamera().unproject(new Vector3(x, y, 0));
        initialTouchPosition = getGridPosition(temp.x, temp.y);

        // for zoom
        scale = camera.getCamera().zoom;

        // adding tiles to the map
        if (creating) {
            screen.addTile(initialTouchPosition);
        }

        return false;
    }

    /**
     * Get the grid position at the given point
     * @param x position of grid
     * @param y position of grid
     * @return MyVector that is the map grid position
     */
    private MyVector getGridPosition(float x, float y) {
        return new MyVector((int)(x / (tileWidth * scale)), (int)(((y) / (tileWidth * scale))));
    }

    /**
     * Called when a tap occured. A tap happens if a touch went down on the screen and was lifted again without moving outside
     * of the tap square. The tap square is a rectangular area around the initial touch position as specified on construction
     * time of the
     *
     * @param x
     * @param y
     * @param count  the number of taps.
     * @param button
     */
    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    /**
     * Called when the user dragged a finger over the screen and lifted it. Reports the last known velocity of the finger in
     * pixels per second.
     *
     * @param velocityX velocity on x in seconds
     * @param velocityY velocity on y in seconds
     * @param button
     */
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    /**
     * Called when the user drags a finger over the screen.
     *
     * @param x
     * @param y
     * @param deltaX the difference in pixels to the last drag event on x.
     * @param deltaY the difference in pixels to the last drag event on y.
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Vector2 position = new Vector2(camera.getMovingTo());
        position.add(-deltaX, deltaY);

        Vector3 temp = camera.getCamera().unproject(new Vector3(x, y, 0));
        MyVector gridPosition = getGridPosition(temp.x, temp.y);

        Gdx.app.log("pan", "x: " + gridPosition.x + " y:" + gridPosition.y);
        if (creating) {
            screen.addTile( gridPosition);
        }
        else {
            camera.moveTo(position);
        }


        return false;
    }

    /**
     * Called when no longer panning.
     *
     * @param x
     * @param y
     * @param pointer
     * @param button
     */
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        camera.moveTo(camera.getCenter());
        return false;
    }

    /**
     * Called when the user performs a pinch zoom gesture. The original distance is the distance in pixels when the gesture
     * started.
     *
     * @param initialDistance distance between fingers when the gesture started.
     * @param distance        current distance between fingers.
     */
    @Override
    public boolean zoom(float initialDistance, float distance) {
        float ratio = initialDistance/distance;
        camera.getCamera().zoom = scale * ratio;
        return false;
    }

    /**
     * Called when a user performs a pinch zoom gesture. Reports the initial positions of the two involved fingers and their
     * current positions.
     *
     * @param initialPointer1
     * @param initialPointer2
     * @param pointer1
     * @param pointer2
     */
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
