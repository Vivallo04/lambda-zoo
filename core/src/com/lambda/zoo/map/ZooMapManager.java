package com.lambda.zoo.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.lambda.zoo.map.interfaces.IZooMapManager;
import com.lambda.zoo.util.Utility;

import java.util.HashMap;

public class ZooMapManager implements IZooMapManager {

    private static final String TAG = ZooMapManager.class.getSimpleName();


    // All the maps for the game
    private HashMap<String, String> mapHashMap;
    private HashMap<String, Vector2> playerStartLocationHashMap;

    // Map
    private final static String DEFAULT_MAP = "DEFAULT_MAP";


    private Vector2 playerStart;
    private TiledMap currentMap;
    private String currentMapName;

    public final static float UNIT_SCALE = 1 / 16f;


    public ZooMapManager() {
        playerStart = new Vector2(0, 0);
        mapHashMap = new HashMap<>();
        mapHashMap.put(DEFAULT_MAP, "maps/default.tmx");

        playerStartLocationHashMap = new HashMap<>();
    }

    public void loadMap(String newMapName) {
        playerStart.set(0, 0);

        String mapFullPath = mapHashMap.get(newMapName);

        if (mapFullPath == null || mapFullPath.isEmpty()) {
            Gdx.app.debug(TAG, "The current map is invalid: " + mapFullPath);
            return;
        }

        if (currentMap != null) {
            currentMap.dispose();
        }

        Utility.loadMapAsset(mapFullPath);
        if (Utility.isAssetLoaded(mapFullPath)) {
            currentMap = Utility.getMapAsset(mapFullPath);
            currentMapName = newMapName;
        } else {
            Gdx.app.debug(TAG, "Map not loaded");
            return;
        }

        Gdx.app.debug(TAG, "Player start: ( " + playerStart.x + "," + playerStart.y + ")");
    }

    public TiledMap getCurrentMap() {
        if (currentMap == null) {
            currentMapName = DEFAULT_MAP;
            loadMap(currentMapName);
        }
        return currentMap;
    }
}
