package com.Game;

import com.Screens.GameScreen3d;
import com.Screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

	@Override
	public void create () {
        batch = new SpriteBatch();

        // Uses LibGDX default arial font
        font = new BitmapFont();

        // set the screen
        //this.setScreen(new MainMenuScreen(this));
        this.setScreen(new GameScreen3d(this));
    }

	@Override
	public void render () {
        super.render();
	}

    @Override
    public void dispose () {
        batch.dispose();
        font.dispose();
    }
}
