package org.grupogjl.commands;

import org.grupogjl.Game;

public class InstructionsCommand extends GameCommand {
    @Override
    public void execute(Game game) {
        game.getStateMenu().getModel().setSelectedOption(true);
        game.getStateMenu().getModel().setTextOption("""
                upper arrow to jump
                right arrow to move right
                left arrow to move left
                down arrow to enter pipe
                b to throw fireballs
                q to pause
                """);
    }
}
