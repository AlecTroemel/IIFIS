package com.Screens;

import com.Game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * The main menu screen of the game
 *
 * Created by alect on 1/23/2016.
 */
public class MainMenuScreen implements Screen {

    final Main game;

    OrthographicCamera camera;

    public MainMenuScreen(final Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,563,1000); // 16/9 ratio
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // update camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "If I Fits I Sits", Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);
        game.font.draw(game.batch, "Tap anywhere to begin", Gdx.graphics.getWidth()/4, (Gdx.graphics.getHeight()/2)-20);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game,"Levels/test.lvl")); //GameScreen(game, "Levels/test.xml"));
            dispose();
        }
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

    }
}
