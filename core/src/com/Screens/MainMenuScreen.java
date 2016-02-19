package com.Screens;

import com.Game.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * The main menu screen of the game
 *
 * Created by alect on 1/23/2016.
 */
public class MainMenuScreen implements Screen {

    OrthographicCamera camera;
    final Main game;

    // UI stuff
    Skin skin;
    Stage stage;
    private Table table;
    SpriteBatch batch;


    /**
     *
     * @param game
     */
    public MainMenuScreen(final Main game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false,563,1000); // 16/9 ratio

        // UI stuff
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(true); // This is optional, but enables debug lines for tables.

        // set new font
        //        freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("pixelmix.ttf"));
        //        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //        parameter.size = 8;
        //        newDefaultFont = freeTypeFontGenerator.generateFont(parameter);

        // set skin
        skin = new Skin();
        skin.add("default-font", BitmapFont.class);
        FileHandle fileHandle = Gdx.files.internal("uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);

        // Add widgets to the table here.

        // label "welcome"
        Label welcomeLabel = new Label( "If I Fits I Sits!", skin );
        welcomeLabel.setFontScale(3);
        welcomeLabel.setPosition((Gdx.graphics.getWidth() / 2) - (welcomeLabel.getWidth()), Gdx.graphics.getHeight() * 0.90f); //.x = ( ( width - welcomeLabel.width ) / 2 );
        stage.addActor( welcomeLabel );



        //lets add a button
        TextButton button = new TextButton("Play Game!", skin);
        button.setWidth(Gdx.graphics.getWidth() / 1.5f);
        button.setHeight(button.getWidth() / 3);
        button.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth()/2), Gdx.graphics.getHeight() / 2);
        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchDown 1");
                game.setScreen(new LevelSelectionMenuScreen(game, skin));
                return false;
            }
        });
        //table.add(button);
        stage.addActor(button);

        //lets add another button
        TextButton button2 = new TextButton("Level Editor", skin);
        button2.setWidth(Gdx.graphics.getWidth() / 1.5f);
        button2.setHeight(button.getWidth() / 3);
        button2.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth()/2), Gdx.graphics.getHeight() / 4);
        button2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("touchDown 1");
                game.setScreen(new LevelEditorScreen(game, skin));
                return false;
            }
        });
        //table.add(button);
        stage.addActor(button2);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        Gdx.gl.glClearColor(0,0,0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        stage.act(Gdx.graphics.getDeltaTime());
//        stage.draw();
//
//
//        // update camera
//        camera.update();
//        game.batch.setProjectionMatrix(camera.combined);
//
//        game.batch.begin();
//        game.font.draw(game.batch, "If I Fits I Sits", Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/2);
//        game.font.draw(game.batch, "Tap anywhere to begin", Gdx.graphics.getWidth()/4, (Gdx.graphics.getHeight()/2)-20);
//        game.batch.end();
//
//        if (Gdx.input.isTouched()) {
//            game.setScreen(new GameScreen(game,"Levels/test.lvl")); //GameScreen(game, "Levels/test.xml"));
//            dispose();
//        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
