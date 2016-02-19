package com.Screens;

import com.Game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created by alect on 2/4/2016.
 */
public class LevelSelectionMenuScreen implements Screen {

    OrthographicCamera camera;
    final Main game;
    Skin skin;
    Stage stage;
    Table table;

    public LevelSelectionMenuScreen(final Main game, Skin s) {
        this.game = game;
        this.skin = s;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

       // table.setDebug(true); // This is optional, but enables debug lines for tables.

        //lets add a button
        TextButton button = new TextButton("Test Level", skin);
        button.setWidth(Gdx.graphics.getWidth() / 1.5f);
        button.setHeight(button.getWidth() / 3);
        button.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth()/2), Gdx.graphics.getHeight() / 2);
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchDown 1");
                game.setScreen(new GameScreen(game, "Levels/test.lvl"));
                return false;
            }
        });
        table.add(button).expandX().height(100).width(300);
        table.row();

        final TextField nameText = new TextField("", skin);
        table.add(nameText).expandX().height(50).width(300).padTop(50);
        table.row();
        TextButton button2 = new TextButton("open Text lvl", skin);
        button2.setWidth(Gdx.graphics.getWidth() / 1.5f);
        button2.setHeight(button.getWidth() / 3);
        button2.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth()/2), Gdx.graphics.getHeight() / 2);
        button2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchDown 1");
                game.setScreen(new GameScreen(game, "Levels/" + nameText.getText() + ".lvl"));
                return false;
            }
        });
        table.add(button2).expandX().height(100).width(300);
    }


    /**
     * Called when this screen becomes the current screen for a .
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     */
    @Override
    public void pause() {

    }

    /**
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
