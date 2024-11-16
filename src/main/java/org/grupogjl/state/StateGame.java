package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.ControllerGame;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.level.LoaderLevelBuilder;
import org.grupogjl.viewer.ViewerGame;

import java.io.IOException;

public class StateGame extends State<Level> {
    private Mario mario;
    private Level level;
    private int leveln;
    private boolean gameOver;
    public StateGame() throws IOException {
        leveln = 1;
        level = new LoaderLevelBuilder(leveln).createLevel();
        mario = new Mario(0, 0, 1, 1);
        mario.setObserver(this);
        level.setMario(mario);
        controller = new ControllerGame();
        viewer = new ViewerGame();
    }
    public Level getLevel() {
        return level;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public int getLeveln() {
        return leveln;
    }
    @Override
    public Level getModel() {
        return level;
    }
    @Override
    public void step(Game game, LanternaGui gui, long StartTime) throws IOException {
        GeneralGui.ACTION action = gui.getNextAction();
        controller.step(game, action, StartTime);
        viewer.draw(this, gui);
        gui.refresh();
    }
    public void draw(GeneralGui gui) {
        try {
            viewer.draw(this, gui);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int getState() {
        return 2;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    public void resetLevel() throws IOException {
        level = new LoaderLevelBuilder(leveln).createLevel();
        mario.setX(0);
        mario.setY(0);
        level.setMario(mario);
    }
    public void nextLevel() throws IOException {
        if (leveln < 2) {
            leveln++;
            level = new LoaderLevelBuilder(leveln).createLevel();
            mario.setX(0);
            mario.setY(0);
            level.setMario(mario);
        } else {
            System.exit(0);
        }
    }
}
