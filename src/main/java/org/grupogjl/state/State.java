package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.Controller;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.viewer.Viewer;

import java.io.IOException;

public abstract class State<T> {
    protected T model;
    protected Controller controller;
    protected Viewer viewer;
    public abstract T getModel();
    public abstract int getState();
    public abstract void step(Game game, LanternaGui gui, long StartTime) throws IOException;
    public Controller getController() {
        return controller;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public Viewer getViewer() {
        return viewer;
    }
    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }
}
