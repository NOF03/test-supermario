package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.state.State;
import org.grupogjl.state.StateMenu;

import java.io.IOException;
import java.util.List;

public class ViewerMenu implements Viewer {
    @Override
    public void draw(State state, GeneralGui gui) throws IOException {
        gui.clear();
        StateMenu stateMenu = (StateMenu) state;
        if (stateMenu.getModel().isSelectedOption()) {
            int y = 1;
            List<String> stringList = stateMenu.getModel().getTextOption().lines().toList();
            for (String s : stringList) {
                gui.drawMenuText((416 - s.length() * 8) / 2 + 1, y * 12, s, "");
                y++;
            }
            String text = "press enter to go back to menu";
            gui.drawMenuText((416 - text.length() * 8) / 2 + 1, ++y * 12, text, "");
        } else {
            String color;
            for (int y = 0; y < stateMenu.getModel().getButtons().size(); y++) {
                if (y == stateMenu.getModel().getSelectedButton()) color = "#ea9e22";
                else color = "";
                String text = stateMenu.getModel().getButtons().elementAt(y).getText();
                gui.drawMenuText((416 - text.length() * 8) / 2 + 1, 121 + 24 * y, text, color);
            }
            gui.drawMenuImage(121, 17, "MenuScreen.png");
        }
        gui.refresh();
    }
}
