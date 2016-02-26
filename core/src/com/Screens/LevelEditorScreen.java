package com.Screens;

import com.Game.Main;
import com.MyUtils.LevelEditorGestureListener;
import com.MyUtils.MyCamera;
import com.MyUtils.MyVector;
import com.MyUtils.TiledWritter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.io.IOException;

/**
 * Created by alect on 2/4/2016.
 */
public class LevelEditorScreen implements Screen {

    private TiledMap map;
    private Main game;

    private TiledMapRenderer renderer;
    private MyCamera camera;

    private BitmapFont font;
    private SpriteBatch batch;

    private TiledMapTileLayer currentLayer;
    private int currentTileIndex;

    // UI
    Stage stage;
    Skin skin;

    private int width, height;

    /**
     *  constructor for the screen
     * @param game: the main game object
     * @param skn: the skin for the ui being used
     */
    public LevelEditorScreen(final Main game, Skin skn) {
        this.game = game;
        this.width = 10;
        this.height = 10;

        initPuzzle();

        camera = new MyCamera(new MyVector(width/2,height/2), false, 1.0f);

        initUI(skn);

        // set up input listeners
        InputProcessor inputProcessor = new GestureDetector(new LevelEditorGestureListener(camera, this));
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        font = new BitmapFont();
        batch = new SpriteBatch();
    }

    /**
     * Construct tha maze (based on empty maze) and tile pointers
     */
    public void initPuzzle() {
        // create maze from default empty maze file
        this.map = new TmxMapLoader().load("Levels/empty_level.tmx");

        // set defaults
        currentLayer = (TiledMapTileLayer)map.getLayers().get("floor");
        currentTileIndex = 1;
        renderer = new OrthogonalTiledMapRenderer(map);

    }

    /**
     * Construct the UI and handle button inputs
     */
    Image tileThumbnail;
    TextButton moveLeft, moveRight, back, save;
    TextField nameText;
    TextField valueX, valueY;
    TextField lengthValue;
    public void initUI(Skin skn) {
        this.skin = skn;
        this.stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true); // This is optional, but enables debug lines for tables.
        table.top();

        // back
        back = new TextButton("back", skin);
        back.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return false;
            }
        });
        table.add(back).width(100).height(100);

        // cycle left
        moveLeft = new TextButton("<--", skin);
        moveLeft.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentTileIndex = currentTileIndex-1;
                if (currentTileIndex < 1) currentTileIndex = map.getTileSets().getTileSet(0).size()-1;

                tileThumbnail.setDrawable(
                    new SpriteDrawable(
                        new Sprite(map.getTileSets().getTileSet(0).getTile(currentTileIndex).getTextureRegion())
                    )
                );
                return false;
            }
        });
        table.add(moveLeft).width(100).height(100);

        // thumbnail
        tileThumbnail = new Image(map.getTileSets().getTileSet(0).getTile(currentTileIndex).getTextureRegion());
        table.add(tileThumbnail).width(100).height(100);

        // cycle right
        moveRight = new TextButton("-->", skin);
        moveRight.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentTileIndex = currentTileIndex+1;
                if (currentTileIndex  == map.getTileSets().getTileSet(0).size()) currentTileIndex = 1;
                tileThumbnail.setDrawable(
                        new SpriteDrawable(
                                new Sprite(map.getTileSets().getTileSet(0).getTile(currentTileIndex).getTextureRegion())
                        )
                );
                return false;
            }
        });
        table.add(moveRight).width(100).height(100);
        table.row();

        // map name input field
        Label nameLabel = new Label("Level Name: ", skin);
        table.add(nameLabel);
        nameText = new TextField("Level Name", skin);
        table.add(nameText).width(300).colspan(3);
        table.row();

        // cat x and y starting positions
        Label labelX = new Label("start X: ", skin);
        table.add(labelX);

        valueX = new TextField("", skin);
        table.add(valueX);

        Label labelY = new Label("start Y: ", skin);
        table.add(labelY);

        valueY = new TextField("", skin);
        table.add(valueY);
        table.row();


        Label lengthLabel = new Label("Cat Length", skin);
        table.add(lengthLabel).colspan(2);
        lengthValue = new TextField("", skin);
        table.add(lengthValue).colspan(2);
        table.row();

        // save button
        save = new TextButton("Save level", skin);
        save.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                try {
                    save();
                } catch (IOException e) {
                    Gdx.app.error("save error", "failure saving level");
                    e.printStackTrace();
                }
                return false;
            }
        });
        table.add(save).width(400).height(50).colspan(4);
        table.row();
    }


    /**
     *
     * @param width
     * @param height
     */
    private void resizeMap(int width, int height) {
        MapLayers layers = map.getLayers();

        this.width = width;
        this.height = height;

        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, 32, 32);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(map.getTileSets().getTileSet(0).getTile(12));//atlas.findRegion("grid")));
                layer.setCell(x, y, cell);
            }
        }
        layer.setName("grid");
        layers.add(layer);
    }

    /**
     * Called when this screen becomes the current screen for a game
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
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update(delta);

        renderer.setView(camera.getCamera());
        renderer.render();
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();

        // render UI
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * @param width
     * @param height
     *
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     *
     */
    @Override
    public void pause() {

    }

    /**
     *
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a Game.
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

    public void changeMapName(String name) {
        map.getProperties().remove("name");
        map.getProperties().put("name", name);
    }

    /**
     * add a tile at the given position
     * the tile will be the tile at the current index
     * @param position
     */
    public void addTile(MyVector position) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(map.getTileSets().getTileSet(0).getTile(currentTileIndex));
        currentLayer.setCell(position.x, position.y, cell);
    }

    private void setCatLength(int length) {
        map.getProperties().remove("catLength");
        map.getProperties().put("catLength", length);
    }

    /**
     * Change the starting position of the cat
     * @param x
     * @param y
     */
    private void setStartingPositions(int x, int y) {
        map.getProperties().remove("startPositionX");
        map.getProperties().put("startPositionX", x);
        map.getProperties().remove("startPositionY");
        map.getProperties().put("startPositionY", y);
    }

    /**
     * save the maze to a file
     * @throws IOException
     */
    private void save() throws IOException {
        // update all the values
        changeMapName(nameText.getText());
        setStartingPositions(Integer.parseInt(valueX.getText()),Integer.parseInt(valueY.getText()));
        setCatLength(Integer.parseInt(lengthValue.getText()));

        // save level
        TiledWritter.saveToFile(map);
    }
}
