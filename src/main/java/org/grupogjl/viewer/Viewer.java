package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.state.State;

import java.io.IOException;

public interface Viewer {
    void draw(State state, GeneralGui Gui) throws IOException;
}
