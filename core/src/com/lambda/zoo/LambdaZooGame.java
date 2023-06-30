package com.lambda.zoo;

import com.badlogic.gdx.Game;
import com.lambda.zoo.screens.MainGameScreen;

public class LambdaZooGame extends Game {
	public static final MainGameScreen mainGameScreen = new MainGameScreen();

	@Override
	public void create () {
		setScreen(mainGameScreen);
	}

	@Override
	public void dispose () {
		mainGameScreen.dispose();
	}
}
