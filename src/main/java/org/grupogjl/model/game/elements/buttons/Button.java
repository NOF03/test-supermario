package org.grupogjl.model.game.elements.buttons;

import org.grupogjl.Game;
import org.grupogjl.commands.GameCommand;

public class Button {
    private GameCommand command;
    private String text;
    public Button(String text, GameCommand command) {
        this.command = command;
        this.text = text;
    }
    public void OnPressed(Game game) {
        command.execute(game);
    }
    public String getText() {
        return text;
    }
}
