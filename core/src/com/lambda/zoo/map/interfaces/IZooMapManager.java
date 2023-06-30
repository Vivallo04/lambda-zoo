package com.lambda.zoo.map.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;

public interface IZooMapManager {
    void loadMap(String newMapName);
    TiledMap getCurrentMap();
}
