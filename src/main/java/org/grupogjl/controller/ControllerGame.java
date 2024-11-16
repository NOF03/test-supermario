package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.commands.ExitToMenuCommand;
import org.grupogjl.commands.GameCommand;
import org.grupogjl.commands.GameOverCommand;
import org.grupogjl.commands.PauseCommand;
import org.grupogjl.controller.game.ControllerLevel;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.state.StateGame;

import java.io.IOException;

import static org.grupogjl.gui.GeneralGui.ACTION.QUIT;
import static org.grupogjl.gui.GeneralGui.ACTION.SELECT;

public class ControllerGame implements Controller {
    private GameCommand gameOverCommand = new GameOverCommand();
    private GameCommand exitToMenuCommand = new ExitToMenuCommand();
    private GameCommand pauseCommand = new PauseCommand();
    private ControllerLevel controllerLevel = new ControllerLevel();
    public GameCommand getGameOverCommand() {
        return gameOverCommand;
    }
    public GameCommand getExitToMenuCommand() {
        return exitToMenuCommand;
    }
    public GameCommand getPauseCommand() {
        return pauseCommand;
    }
    public ControllerLevel getControllerLevel() {
        return controllerLevel;
    }
    @Override
    public void step(Game game, GeneralGui.ACTION action, long time) throws IOException {
        Level model = game.getStateGame().getModel();
        Mario mario = model.getMario();
        int lives = mario.getLives();
        if (lives == 0) {
            getGameOverCommand().execute(game);
            if (action == SELECT) {
                getExitToMenuCommand().execute(game);
            }
        } else if (action == QUIT) {
            getPauseCommand().execute(game);
        } else {
            StateGame gameState = game.getStateGame();
            Level level = gameState.getModel();
            getControllerLevel().step(level, action, time);
        }
    }
}
