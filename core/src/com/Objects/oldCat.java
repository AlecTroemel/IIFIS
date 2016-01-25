package com.Objects;

import com.Screens.GameScreen;
import com.Tiles.CatBodyTile;
import com.Tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Stack;

/**
 * Created by alect on 1/24/2016.
 */
public class oldCat extends TileObject {


    private int size;

    private Stack<Vector2> pastMoves;
    private Stack<Tile> pastTailTiles;

    public oldCat(int size, Vector2 startingPosition, float scale, GameScreen gameScreen) {
        // do all the parent class things
        super(gameScreen);
        this.size = size;

        pastMoves = new Stack<Vector2>();
        pastTailTiles = new Stack<Tile>();

        // load the cat texture atlas
        // right now it is just my programmer one
        this.textureAtlas = new TextureAtlas(Gdx.files.internal("SpriteSheet/programmer.atlas"));

        setWidth((int)(width*scale));


        // create cat parts
        Vector2 currentPosition = new Vector2(startingPosition.x,startingPosition.y);
        for(int i = 0; i < size; i++) {
            String tileType;

            if(i == 0) tileType = "head";
            else if(i == size-1) tileType = "tail";
            else tileType = "body_straight";

            // create new tile and add it to the list
            parts.add(new CatBodyTile(textureAtlas.findRegion(tileType),new Vector2(currentPosition), width));

            //get next position
            currentPosition.add(0.0f,1.0f);
        }
    }

    public Vector2 getHeadPosition() {
        return new Vector2(Math.round(parts.get(0).getLocation().x / width), Math.round(parts.get(0).getLocation().y / width));
    }


    public boolean tryMoveUp() {
        Vector2 newLocation = new Vector2(this.parts.get(0).getLocation());
        newLocation.add(0.0f, width);
        return tryMove(newLocation, true);
    }


    public boolean tryMoveDown() {
        Vector2 newLocation = new Vector2(this.parts.get(0).getLocation());
        newLocation.add(0.0f, -width);
        return tryMove(newLocation, true);
    }


    public boolean tryMoveLeft() {
        Vector2 newLocation = new Vector2(this.parts.get(0).getLocation());
        newLocation.add(-width, 0.0f);
        return tryMove(newLocation, false);
    }


    public boolean tryMoveRight() {
        Vector2 newLocation = new Vector2(this.parts.get(0).getLocation());
        newLocation.add(width,0.0f);
        return tryMove(newLocation, false);
    }

    private boolean tryMove(Vector2 newLocation, boolean upDown) {
        return true;
    }
        /*boolean movingBackwards = false;

        // check if moving backwards
        if (pastMoves.size() != 0) {
            if (newLocation.equals(pastMoves.peek())) {
                pastMoves.pop();
                movingBackwards = true;
            }
        }

        // move backwards if true
        if (movingBackwards) {
            // remove first tile
            parts.removeFirst();

            // push old tile into stack
            parts.add(pastTailTiles.pop());

            // update 2 tiles texture
            setupTexture(parts.get(2).getLocation(),
                    parts.get(0).getLocation(),
                    parts.get(1));

            return true;
        }
        // else we are moving forwards
        else {
            Vector2 direction = new Vector2(parts.getFirst().getLocation());
            direction.sub(newLocation);

            // collide with self?
            if (this.hasAt(newLocation)) {
                return false;
            }
            if (gameScreen.getMap().canMoveTo(parts.getFirst().getLocation(), newLocation) == false) {
                return false;
            }


            // can move from current map tile?
            if (gameScreen.getMap().hasAt(parts.getFirst().getLocation())) {
                if (gameScreen.getMap().getAt(parts.getFirst().getLocation()).canMoveFrom(direction) == false) {
                    return false;
                }
            }
            // Can move to next map tile?
            if (gameScreen.getMap().hasAt(newLocation)) {
                if (gameScreen.getMap().getAt(newLocation).canMoveTo(direction) == false) {
                    return false;
                }
            }
            // add the new locations

            // remember the heads old location
            pastMoves.push(parts.getFirst().getLocation());

            // create new tile
            Tile temp = new CatBodyTile(
                    textureAtlas.findRegion("head"),
                    new Vector2(newLocation),width);
            if (upDown) temp.rotate90(true);
            parts.addFirst(temp);

            // update 2 tiles texture
            setupTexture(parts.get(2).getLocation(),
                    parts.get(0).getLocation(),
                    parts.get(1));

            // remove the end and remember its location
            pastTailTiles.push(parts.removeLast());

            return true;
        }
    } */

    private void setupTexture(Vector2 prev, Vector2 next, Tile tile) {
        TextureRegion region;
        float x = tile.getLocation().x;
        float y = tile.getLocation().y;

        // vertical line
        if(next.x == prev.x) {
            tile.setSprite(textureAtlas.findRegion("body_straight"));
        }
        else if (next.y == prev.y) {
            tile.setSprite(textureAtlas.findRegion("body_straight"));
            tile.rotate90(true);
        }

        else {
            tile.setSprite(textureAtlas.findRegion("body_angle"));

            // prev left
            if (prev.x < x) {
                // next up
                if (next.y > y) {}
                // next down
                else {tile.rotate90(false);}
            }
            // prev right
            else if (prev.x > x) {
                // next up
                if (next.y > y) {tile.rotate90(true);}
                // next down
                else {tile.rotate90(true); tile.rotate90(true);}
            }
            // prev up
            else if (prev.y > y) {
                // next right
                if (next.x > x) {tile.rotate90(true);}
                // next left
                else {}
            }
            // prev down
            else {
                // next right
                if (next.x > x) {tile.rotate90(true); tile.rotate90(true);}
                // next left
                else {tile.rotate90(false);}
            }
        }
    }

}
