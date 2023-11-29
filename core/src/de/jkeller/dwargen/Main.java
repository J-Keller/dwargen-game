package de.jkeller.dwargen;

import com.badlogic.gdx.Game;
import de.jkeller.dwargen.screens.MainMenu;

/**
 * Main class that manages the order of events in the game
 */
public class Main extends Game {


	@Override
	public void create() {
		setScreen(new MainMenu());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
