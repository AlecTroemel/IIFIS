package com.Screens;

import com.Game.Main;
import com.MyUtils.GameGestureListener;
import com.MyUtils.MyCamera;
import com.MyUtils.MyVector;
import com.Objects.Cat;
import com.Objects.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;


/**
 * The main game screen, handles the GUI and map
 *
 * Created by alect on 1/23/2016.
 */
public class GameScreen implements Screen {

    private MyCamera camera;
    protected Map map;
    final Main game;

    // for debugging
    private SpriteBatch batch;
    private BitmapFont xyPos;
    private BitmapFont press;
    private String debug;
    private String debug2;

    public GameScreen (final Main game, String levelPath) {
        this.game = game;

        // create map
        map = new Map(levelPath);

        // create my camera
        camera = new MyCamera(map.getStartingPosition(), false, map.getScale());

        // font for debugging
        xyPos = new BitmapFont();
        xyPos.setColor(Color.RED);
        press = new BitmapFont();
        press.setColor(Color.RED);
        batch = new SpriteBatch();
        debug = " ";
        debug2 = " ";

        // set up gesture listener for input
        Gdx.input.setInputProcessor(new GestureDetector(new GameGestureListener(camera,map)));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        //camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.getCamera().combined);


       // cat2.update();
        map.render(delta, camera);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        batch.dispose();
    }
}
