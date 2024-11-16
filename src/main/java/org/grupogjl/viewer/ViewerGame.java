package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.state.State;
import org.grupogjl.state.StateGame;

import java.io.IOException;

public class ViewerGame implements Viewer {
    @Override
    public void draw(State state, GeneralGui gui) throws IOException {
        gui.clear();
        StateGame stateg = (StateGame) state;
        Mario mario = stateg.getModel().getMario();
        Camera camera = stateg.getModel().getCamera();
        if (stateg.isGameOver()) {
            gui.drawGameOver();
        } else {
            for (GameObject object : stateg.getModel().getObjects()) {
                if (object.getX() < camera.getLeftCamLimit() - 1 || object.getX() > camera.getRightCamLimit()) continue;
                gui.drawImage(object.getVirtX(camera), object.getVirtY(), object.getImage());
            }
            gui.drawImage(mario.getVirtX(camera), mario.getVirtY(), mario.getImage());
            gui.drawMenuText(1, 8, "coins: " + mario.getCoins());
            gui.drawMenuText(1, 3 * 8, "lives: " + mario.getLives());
        }
    }
}
