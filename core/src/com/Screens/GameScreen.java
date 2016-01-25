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
 * Created by alect on 1/23/2016.
 */
public class GameScreen implements Screen {

    final Main game;
    OrthographicCamera camera;
    private Cat cat;
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

        // create cat
        cat = new Cat(map);


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

        cat.update();
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
        cat.dispose();
    }

    public Map getMap()
    {
        return this.map;
    }


    private Vector2 oldPosition;
    private boolean movingCat;
    private void handleInput()
    {
       /*
        if (Gdx.input.justTouched()) {
            ;
            oldPosition = new Vector2(Math.round(Gdx.input.getX() / 64), Math.round((Gdx.graphics.getHeight() - Gdx.input.getY()) / 64));
            if(cat.getHeadPosition().equals(oldPosition))
            {
                movingCat = true;
                //debug2 = "clicked on cat";
            }
            else {
                movingCat = false;
                //debug2 = "missed cat head";
            }
        }
        else if(Gdx.input.isTouched())
        {
            Vector2 newPosition = new Vector2();
            int x = Math.round(Gdx.input.getX() / 64);
            int y = Math.round((Gdx.graphics.getHeight() - Gdx.input.getY()) / 64);

            newPosition.set(x,y);
            //debug = "Point Position x: " + x + " y: " + y;
            //debug2 = "dragging";

            if (movingCat) {
                if (!newPosition.equals(oldPosition)) {
                    Vector2 direction = new Vector2(newPosition);
                    direction.sub(oldPosition);

                    //debug = "Direction x: " + direction.x + " y: " + direction.y;
                    // Left
                    if (direction.equals(new Vector2(-1.0f,0.0f)))
                    {
                        if (cat.tryMoveLeft()) {
                            oldPosition.set(newPosition);
                        }
                    }
                    // Right
                    else if (direction.equals(new Vector2(1.0f,0)))
                    {
                        if (cat.tryMoveRight()) {
                            oldPosition.set(newPosition);
                        }
                    }
                    // up
                    else if (direction.equals(new Vector2(0,1.0f)))
                    {
                        if (cat.tryMoveUp()) {
                            oldPosition.set(newPosition);
                        }
                    }
                    // down
                    else if (direction.equals(new Vector2(0,-1.0f)))
                    {
                        if (cat.tryMoveDown()) {
                            oldPosition.set(newPosition);
                        }
                    }
                }
            }
            // TODO: use camera
            // camera.unproject(newPosition);
        }
        else {
            //debug2 = "released";
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) cat.tryMoveLeft();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) cat.tryMoveRight();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) cat.tryMoveUp();
            else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) cat.tryMoveDown();
        } */
    }
}
