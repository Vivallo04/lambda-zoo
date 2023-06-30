package com.lambda.zoo.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Utility {

    public static final AssetManager assetManager = new AssetManager();
    private static final String TAG = Utility.class.getSimpleName();
    private static final InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

    /***
     * Check to see whether the asset is loaded, if so, unload the asset.
     * @param assetFileNamePath Asset to unload
     */
    public static void unloadAsset(String assetFileNamePath) {
        if (assetManager.isLoaded(assetFileNamePath)) {
            assetManager.unload(assetFileNamePath);
            return;
        }
        Gdx.app.debug(TAG, "The asset is not loaded; Nothing to unload: " + assetFileNamePath);
    }

    public static float loadCompleted() {
        return assetManager.getProgress();
    }

    public static int numberOfAssetsQueued() {
        return assetManager.getQueuedAssets();
    }

    public static boolean updateAssetsLoading() {
        return assetManager.update();
    }

    public static boolean isAssetLoaded(String assetFileNamePath) {
        return assetManager.isLoaded(assetFileNamePath);
    }

    public static void loadTiles(String tileSetPath) {
        if (tileSetPath == null || tileSetPath.isEmpty()) {
            return;
        }

        if (filePathResolver.resolve(tileSetPath).exists()) {
            assetManager.load(tileSetPath, Texture.class);
            assetManager.finishLoadingAsset(tileSetPath);

            Gdx.app.debug(TAG, "Tiles loaded: " + tileSetPath);
            return;
        }
        Gdx.app.debug(TAG, "The tile files don't exist: " + tileSetPath);
    }

    public static TextureRegion getTileTextures(Texture tileSet, int tileIndex, int tileSize) {
        int tilesPerRow = tileSet.getWidth() / tileSize;

        int startX = (tileIndex % tilesPerRow) * tileSize;
        int startY = (tileIndex / tilesPerRow) * tileSize;

        return new TextureRegion(tileSet, startX, startY, tileSize, tileSize);
    }

    public static void loadMapAsset(String mapFileNamePath) {
        if (mapFileNamePath == null || mapFileNamePath.isEmpty()) {
            return;
        }

        // Load the Asset
        if (filePathResolver.resolve(mapFileNamePath).exists()) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));

            assetManager.load(mapFileNamePath, TiledMap.class);

            assetManager.finishLoadingAsset(mapFileNamePath);
            Gdx.app.debug(TAG, "Map loaded: "+ mapFileNamePath);
        } else {
            Gdx.app.debug(TAG, "The map doesn't exist: " + mapFileNamePath);
        }
    }

    public static TiledMap getMapAsset(String mapFileNamePath) {
        TiledMap map = null;

        if (assetManager.isLoaded(mapFileNamePath)) {
            map = assetManager.get(mapFileNamePath, TiledMap.class);

        } else {
            Gdx.app.debug(TAG, "The map is not loaded: " + mapFileNamePath);
        }

        return map;
    }

    public static void loadTextureAsset(String textureFileNamePath) {
        if (textureFileNamePath == null || textureFileNamePath.isEmpty()) {
            return;
        }

        // Load Texture asset
        if (filePathResolver.resolve(textureFileNamePath).exists()) {
            assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));

            assetManager.load(textureFileNamePath, Texture.class);

            // TODO:  Add a loading screen

            assetManager.finishLoadingAsset(textureFileNamePath);
        } else {
            Gdx.app.debug(TAG, "Texture doesn't exist: " + textureFileNamePath);
        }
    }

    public static Texture getTextureAsset(String textureFileNamePath) {
        Texture texture = null;

        if (assetManager.isLoaded(textureFileNamePath)) {
            texture = assetManager.get(textureFileNamePath, Texture.class);

        } else {
            Gdx.app.debug(TAG, "Texture is not loaded: " + textureFileNamePath);
        }

        return texture;
    }
}
