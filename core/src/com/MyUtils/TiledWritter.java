package com.MyUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.XmlWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

/**
 * Created by alect on 2/5/2016.
 */
public class TiledWritter {
    public static void saveToFile(TiledMap map) {
        StringWriter writer = new StringWriter();
        XmlWriter xml = new XmlWriter(writer);
        // write it!
        try {
            // write basic data about the map that is always the same
            xml.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.element("map")
                    .attribute("version", "1.0")
                    .attribute("orientation", "orthogonal")
                    .attribute("renderorder", "right-down")
                    .attribute("width", ((TiledMapTileLayer)map.getLayers().get(0)).getWidth())
                    .attribute("height",((TiledMapTileLayer)map.getLayers().get(0)).getHeight())
                    .attribute("tilewidth", map.getTileSets().getTileSet(0).getProperties().get("tilewidth"))
                    .attribute("tileheight", map.getTileSets().getTileSet(0).getProperties().get("tileheight"))
                    .attribute("nextobjectid", "2");

            // add map properties
            xml.element("properties");
            Iterator<String> keys = map.getProperties().getKeys();
            while(keys.hasNext()) {
                String key = keys.next();

                xml.element("property")
                    .attribute("name", key)
                    .attribute("value", map.getProperties().get(key))
                .pop();
            }
            xml.pop();

            // Add the tile sheet and tile data
            TiledMapTileSet tileSet = map.getTileSets().getTileSet(0);
            xml.element("tileset")
                .attribute("firstgid", tileSet.getProperties().get("firstgid"))
                .attribute("name", tileSet.getName())
                .attribute("tilewidth",tileSet.getProperties().get("tilewidth"))
                .attribute("tileheight",tileSet.getProperties().get("tileheight"))
                .attribute("tilecount", "16")
                .attribute("columns", "4");

            xml.element("image")
                .attribute("source", tileSet.getProperties().get("imagesource"))
                .attribute("width", tileSet.getProperties().get("imagewidth"))
                .attribute("height", tileSet.getProperties().get("imageheight"))
            .pop();


            Iterator<TiledMapTile> tiles = tileSet.iterator();
            while (tiles.hasNext()) {
                TiledMapTile tile = tiles.next();


                Iterator<String> tileProperties = tile.getProperties().getKeys();
                if (tileProperties.hasNext()) {
                    xml.element("tile").attribute("id", tile.getId()-1);
                    xml.element("properties");
                    while (tileProperties.hasNext()) {
                        String key = tileProperties.next();
                        xml.element("property")
                            .attribute("name", key)
                            .attribute("value", tile.getProperties().get(key))
                        .pop();
                    }
                    xml.pop().pop();
                }
            }
            xml.pop();

            // add the layer data
            for (MapLayer l : map.getLayers()) {
                TiledMapTileLayer layer = (TiledMapTileLayer) l;

                if (!layer.getName().equals("grid")) {
                     xml.element("layer")
                        .attribute("name",layer.getName())
                        .attribute("width",layer.getWidth())
                        .attribute("height",layer.getHeight());

                        // add the actual tile locations of map
                        xml.element("data").attribute("encoding","csv");
                            String layerData = "";
                            for (int i = 0; i < layer.getHeight(); i++) {
                                for (int j = 0; j < layer.getWidth(); j++) {
                                    // check if the cell is null
                                    TiledMapTileLayer.Cell cell = layer.getCell(j, i);
                                    if (cell != null) layerData += cell.getTile().getId();
                                    else layerData += "0";
                                    if (j != layer.getWidth()-1) layerData += ",";
                                }
                                if (i != layer.getHeight()-1) layerData += ",\n";
                            }
                            xml.text(layerData);
                        xml.pop();
                    xml.pop();
                }
            }
            xml.pop();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // time to write everything to the file!
        try {
            FileHandle handle = Gdx.files.internal("Levels/writeTest.lvl");
            FileWriter fw = new FileWriter(handle.file());
            fw.write(writer.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Gdx.app.log("toString: ", writer.toString());


    }
}
