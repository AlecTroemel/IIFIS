package com.Screens;

import com.Game.Main;
import com.MyUtils.GameGestureListener;
import com.MyUtils.MyCamera;
import com.MyUtils.MyVector;
import com.Objects.Cat;
import com.Objects.Goal;
import com.Objects.Map;
import com.Objects.OneCatGoal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
    private Goal goal;
    protected Map map;
    final Main game;

    // for debugging
    private SpriteBatch batch;
    private BitmapFont goalText;

    // TODO: this is the WRONG place to be getting the image, but i dont care
    Texture win;

    public GameScreen (final Main game, String levelPath) {
        this.game = game;

        // create map
        map = new Map(levelPath);

        // create my camera
        camera = new MyCamera(map.getStartingPosition(), false, map.getScale());

        // stuff for debugging
        goalText = new BitmapFont();
        goalText.setColor(Color.RED);
        batch = new SpriteBatch();
        win = new Texture(Gdx.files.internal("images/win.jpg"));


        // set up gesture listener for input
        Gdx.input.setInputProcessor(new GestureDetector(new GameGestureListener(camera,map)));

        // create goal TODO: will be a param for this function later
        goal = new OneCatGoal();
        goal.init(this.map);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (goal.isGoal()) {
            this.end();
        }

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.getCamera().combined);

        // Render the map
        map.render(delta, camera);

        // update goal
        goal.update(map);

        // Render the UI
        batch.begin();
        goalText.draw(batch, goal.toString(),Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() - 10);
        batch.end();
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

    private void end() {
        batch.begin();
        batch.draw(win, 0, Gdx.graphics.getHeight()/2);
        batch.end();
    }
}
