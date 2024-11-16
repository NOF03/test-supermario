package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.ControllerPause;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.model.game.elements.pause.PauseModel;
import org.grupogjl.viewer.ViewerPause;

import java.io.IOException;

public class StatePause extends State<PauseModel> {
    private final StateGame parent;
    private final PauseModel pauseModel = new PauseModel();
    public StatePause(StateGame parent) {

        this.parent = parent;
        controller = new ControllerPause();
        viewer = new ViewerPause();
    }
    @Override
    public PauseModel getModel() {
        return pauseModel;
    }
    @Override
    public int getState() {
        return 3;
    }
    public StateGame getParent() {
        return parent;
    }
    @Override
    public void step(Game game, LanternaGui gui, long StartTime) throws IOException {
        GeneralGui.ACTION action = gui.getNextAction();
        controller.step(game, action, StartTime);
        parent.draw(gui);
        viewer.draw(this, gui);
    }
}
