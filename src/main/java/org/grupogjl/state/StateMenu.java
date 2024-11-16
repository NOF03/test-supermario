package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.ControllerMenu;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.model.game.elements.menu.MenuModel;
import org.grupogjl.viewer.ViewerMenu;

import java.io.IOException;

public class StateMenu extends State<MenuModel> {
    private MenuModel menu = new MenuModel();
    public StateMenu() {
        controller = new ControllerMenu();
        viewer = new ViewerMenu();
    }
    @Override
    public MenuModel getModel() {
        return menu;
    }
    @Override
    public int getState() {
        return 1;
    }
    @Override
    public void step(Game game, LanternaGui gui, long StartTime) throws IOException {
        GeneralGui.ACTION action = gui.getNextAction();
        controller.step(game, action, StartTime);
        viewer.draw(this, gui);
    }
}
