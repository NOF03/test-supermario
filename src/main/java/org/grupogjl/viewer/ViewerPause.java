package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.state.State;
import org.grupogjl.state.StatePause;

import java.io.IOException;

public class ViewerPause implements Viewer {
    @Override
    public void draw(State state, GeneralGui gui) throws IOException {
        StatePause statePause = (StatePause) state;
        for (int y = 0; y < statePause.getModel().getButtons().size(); y++) {
            String color;
            if (y == statePause.getModel().getSelectedButton()) color = "#ea9e22";
            else color = "";
            String text = statePause.getModel().getButtons().elementAt(y).getText();
            gui.drawMenuText((416 - text.length() * 8) / 2 + 1, 89 + 24 * y, text, color);
        }
        gui.refresh();
    }
}
