package org.grupogjl;

import org.grupogjl.audio.WavAudioPlayer;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.state.State;
import org.grupogjl.state.StateGame;
import org.grupogjl.state.StateMenu;
import org.grupogjl.state.StatePause;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Game {
    private static Game instance;
    private final LanternaGui gui;
    private State state = new StateMenu();
    private Game() throws FontFormatException, IOException, URISyntaxException {
        this.gui = new LanternaGui(416, 207);
        WavAudioPlayer.playSound("gameSound.wav");

    }
    public Game(LanternaGui gui) { // for testing
        this.gui = gui;

    }
    public static synchronized Game getInstance() throws FontFormatException, IOException, URISyntaxException {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
    public State getState() {
        return state;
    }
    public LanternaGui getGui() {
        return gui;
    }
    public void run() throws InterruptedException, IOException {
        int FPS = 60;
        long targetFrameTime = 1000 / FPS;
        while (state != null) {
            long startTime = System.currentTimeMillis();
            state.step(this, gui, startTime);
            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = targetFrameTime - elapsedTime;
            if (sleepTime > 0) {
                Thread.sleep(sleepTime);
            }
        }
        System.exit(0);
    }
    public void setStateMenu() {
        state = new StateMenu();
    }
    public void setStateGame() throws IOException {
        state = new StateGame();
    }
    public void setStatePause() {
        state = new StatePause((StateGame) state);
    }
    public void setStateNull() {
        this.state = null;
    }
    public StateGame getStateGame() { //testing
        return (StateGame) state;
    }
    public void setStateGame(State parent) {
        state = parent;
    }
    public StateMenu getStateMenu() {
        return (StateMenu) state;
    }
    public StatePause getStatePause() {
        return (StatePause) state;
    }
}
