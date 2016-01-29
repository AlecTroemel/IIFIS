package com.Screens;

import com.Game.Main;
import com.Objects.Cat;
import com.Objects.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * The main game screen, handles the GUI and map
 *
 * Created by alect on 1/23/2016.
 */
public class GameScreen implements Screen {

    final Main game;
    OrthographicCamera camera;

   // private Cat cat2;

    protected Map map;

    private BitmapFont xyPos;
    private BitmapFont press;
    private SpriteBatch batch;
    private String debug;
    private String debug2;

    public GameScreen (final Main game, String levelPath) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //camera.translate(0,  -(Gdx.graphics.getHeight()/2.5f));

        // create map
        map = new Map(levelPath);


        //cat2 = new Cat(map,0,3);




        // font for debugging
        xyPos = new BitmapFont();
        xyPos.setColor(Color.RED);
        press = new BitmapFont();
        press.setColor(Color.RED);
        batch = new SpriteBatch();
        debug = " ";
        debug2 = " ";
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
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);


       // cat2.update();
        map.render(camera);
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
    }

    public Map getMap()
    {
        return this.map;
    }

}
