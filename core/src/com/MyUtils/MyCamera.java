package com.MyUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Custom camera rapper class
 * has functions for camera operations and conversions
 *
 * Created by alect on 1/30/2016.
 */
public class MyCamera {

    private OrthographicCamera camera;
    private Vector2 movingTo;
    private Vector2 center;

    private final int tileWidth = 32;
    private final float lerp = 5.0f;

    private boolean free;
    private float scale;

    /**
     *
     * @param center: location the camera is centered on
     * @param free: ... not really used yet
     * @param scale: the scale the map tiles have been increased to
     */
    public MyCamera(MyVector center, boolean free, float scale) {
        this.free = free;
        this.scale = scale;
        this.center = new Vector2(actualPos(center.x), actualPos(center.y));

        // move camera to initial position (center)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(actualPos(this.center.x), actualPos(this.center.y),0 );
        movingTo = this.center;
    }

    /**
     * returns the screen pixel location
     *
     * @param value to convert
     * @return the actual location of the value
     */
    public float actualPos(float value) {
        return (value * tileWidth * scale) + (tileWidth / 2);
    }

    /**
     * tell the camera to begin moving to the given location
     *
     * @param location: in actual pixels NOT map grid
     */
    public void moveTo(Vector2 location) {
        movingTo = location;
    }

    /**
     * called every frame
     * smooth transitions towards the movingTo location
     *
     * @param delta time passed since last frame
     */
    public void update(float delta) {
        if (!free) {
            // smooth movement to destination
            Vector3 position = camera.position;
            position.x += (movingTo.x - position.x) * lerp * delta;
            position.y += (movingTo.y - position.y) * lerp * delta;

            camera.position.set(position);
        }
        camera.update();
    }

    /**
     * @return the camera this class utalizes
     */
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    /**
     * @return thte center location of the camera what it will sbnap to
     */
    public Vector2 getCenter() {
        return this.center;
    }

    /**
     * @return the location the camera is moving towards
     */
    public Vector2 getMovingTo() {
        return this.movingTo;
    }

    /**
     * @param newCenter: the new location the camera will center on
     */
    public void setCenter(Vector2 newCenter) {
        this.center = newCenter;
    }

}
