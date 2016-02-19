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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private Texture tiles;

    private BitmapFont font;
    private SpriteBatch batch;

    private TiledMapTileLayer currentLayer;
    private TextureAtlas.AtlasRegion currentTile;
    private int currentTileIndex;
    // UI
    Stage stage;
    Skin skin;



    private int width, height;

    /**
     *
     * @param game
     */
    public LevelEditorScreen(final Main game, Skin skn) {
        this.game = game;
        this.width = 10;
        this.height = 10;
        initPuzzle();

        camera = new MyCamera(new MyVector(width/2,height/2), false, 1.0f);
        //Gdx.input.setInputProcessor(new GestureDetector(new LevelEditorGestureListener(camera, this)));

        initUI(skn);

        InputProcessor inputProcessor = new GestureDetector(new LevelEditorGestureListener(camera, this));
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessor);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);



        font = new BitmapFont();
        batch = new SpriteBatch();

    }


    TextureAtlas atlas;
    public void initPuzzle() {
        this.tiles = new Texture(Gdx.files.internal("SpriteSheet/programmer2.png"));
        this.map = new TmxMapLoader().load("Levels/empty_level.tmx");

        atlas = new TextureAtlas(Gdx.files.internal("SpriteSheet/programmer2.atlas"));

        // set defaults
        currentLayer = (TiledMapTileLayer)map.getLayers().get("floor");
        currentTile = atlas.findRegion("floor");
        currentTileIndex = 0;
        renderer = new OrthogonalTiledMapRenderer(map);

    }

    /**
     * Construct the UI
     */

    Image tileThumbnail;
    TextButton moveLeft, moveRight, back, save;
    public void initUI(Skin skn) {
        this.skin = skn;

        stage = new Stage();
       // Gdx.input.setInputProcessor(stage);

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
                currentTileIndex = currentTile.index-1;
                if (currentTileIndex < 0) currentTileIndex = atlas.getRegions().size-1;
                currentTile = atlas.getRegions().get(currentTileIndex);

                tileThumbnail.setDrawable(
                    new SpriteDrawable(
                        new Sprite(map.getTileSets().getTileSet(0).getTile(currentTileIndex).getTextureRegion())
                    )
                );

                System.out.println("touchDown 2");
                return false;
            }
        });
        table.add(moveLeft).width(100).height(100);

        // thumbnail
        tileThumbnail = new Image(currentTile);
        table.add(tileThumbnail).width(100).height(100);

        // cycle right
        moveRight = new TextButton("-->", skin);
        moveRight.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentTileIndex = currentTile.index+1;
                if (currentTileIndex  == atlas.getRegions().size) currentTileIndex = 0;
                currentTile = atlas.getRegions().get(currentTileIndex);
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
        table.add(save).width(400).height(100).colspan(4);
    }



    /**
     *
     * @param name
     * @param width
     * @param height
     */
    private void createLayer(String name, int width, int height) {
        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, 32, 32);
        layer.setName(name);
        layers.add(layer);
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
                cell.setTile(new StaticTiledMapTile(atlas.findRegion("grid")));
                layer.setCell(x, y, cell);
            }
        }
        layer.setName("grid");
        //layer.setOpacity(0.5f);
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

    /**
     *
     * @param position
     */
    public void addTile(MyVector position) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(map.getTileSets().getTileSet(0).getTile(currentTileIndex));
        //cell.setTile(new StaticTiledMapTile(currentTile));
        currentLayer.setCell(position.x, position.y, cell);
    }

    private void save() throws IOException {
        TiledWritter.saveToFile(map);
    }

}
