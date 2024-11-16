package org.grupogjl.model.game.elements.menu;

import org.grupogjl.commands.ExitGameCommand;
import org.grupogjl.commands.InstructionsCommand;
import org.grupogjl.commands.StartGameCommand;
import org.grupogjl.model.game.elements.buttons.Button;

import java.util.Arrays;
import java.util.Vector;

public class MenuModel extends MenuPauseModel {
    public MenuModel() {
        buttons = new Vector<Button>(Arrays.asList(new Button("start game", new StartGameCommand()), new Button("instructions screen", new InstructionsCommand()), new Button("exit game", new ExitGameCommand())));
    }
}
