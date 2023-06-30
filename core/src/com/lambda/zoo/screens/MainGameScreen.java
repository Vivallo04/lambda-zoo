package com.lambda.zoo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lambda.zoo.map.ZooMapManager;
import com.lambda.zoo.player.PlayerController;
import com.lambda.zoo.screens.interfaces.IMainGameScreen;

public class MainGameScreen implements Screen, IMainGameScreen {

    private final String TAG = this.getClass().getSimpleName();


    private PlayerController playerController;
    private TiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private final ZooMapManager zooMapManager;

    private static class VIEWPORT {
        static float viewPortWidth;
        static float viewPortHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    public MainGameScreen() {
        this.zooMapManager = new ZooMapManager();
    }

    public void setupViewport(int width, int height) {
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        VIEWPORT.viewPortWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewPortHeight = VIEWPORT.virtualHeight;

        // Display physical dimensions
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        VIEWPORT.aspectRatio = (VIEWPORT.virtualWidth / VIEWPORT.virtualHeight);

        if (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRatio) {
            VIEWPORT.viewPortWidth = VIEWPORT.viewPortHeight * (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight);
        } else {
            VIEWPORT.viewPortWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewPortHeight = VIEWPORT.viewPortWidth * (VIEWPORT.physicalHeight / VIEWPORT.physicalWidth);
        }

        Gdx.app.debug(TAG, "Virtual WorldRenderer: ( " + VIEWPORT.virtualWidth + "," + VIEWPORT.virtualHeight + ")");
        Gdx.app.debug(TAG, "Viewport WorldRenderer: ( " + VIEWPORT.viewPortWidth + "," + VIEWPORT.viewPortHeight + ")");
        Gdx.app.debug(TAG, "Physical WorldRenderer: ( " + VIEWPORT.physicalWidth + "," + VIEWPORT.physicalHeight + ")");
    }

    @Override
    public void show() {
        // Set up the camera
        setupViewport(10, 10);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT.viewPortWidth, VIEWPORT.viewPortHeight);

        mapRenderer = new IsometricTiledMapRenderer(zooMapManager.getCurrentMap(), ZooMapManager.UNIT_SCALE);
        mapRenderer.setView(camera);

        playerController = new PlayerController();
        Gdx.input.setInputProcessor(playerController);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);

        mapRenderer.setView(camera);
        mapRenderer.render();


        camera.update();

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

    }
}
