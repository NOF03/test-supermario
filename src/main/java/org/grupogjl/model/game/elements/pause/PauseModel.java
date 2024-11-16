package org.grupogjl.model.game.elements.pause;

import org.grupogjl.commands.ExitToMenuCommand;
import org.grupogjl.commands.ResumeCommand;
import org.grupogjl.model.game.elements.buttons.Button;
import org.grupogjl.model.game.elements.menu.MenuPauseModel;

import java.util.Arrays;
import java.util.Vector;

public class PauseModel extends MenuPauseModel {
    public PauseModel() {
        buttons = new Vector<>(Arrays.asList(new Button("resume", new ResumeCommand()), new Button("exit to menu", new ExitToMenuCommand())));
    }
}
