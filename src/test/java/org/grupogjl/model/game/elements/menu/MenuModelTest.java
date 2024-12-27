package org.grupogjl.model.game.elements.menu;

import org.grupogjl.commands.ExitGameCommand;
import org.grupogjl.commands.InstructionsCommand;
import org.grupogjl.commands.StartGameCommand;
import org.grupogjl.model.game.elements.buttons.Button;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

class MenuModelTest {

    @Test
    void testMenuModelInitialization() {
        MenuModel menuModel = new MenuModel();

        Vector<Button> buttons = menuModel.getButtons();
        assertThat(buttons).hasSize(3);
        assertThat(buttons.get(0).getText()).isEqualTo("start game");
        assertThat(buttons.get(1).getText()).isEqualTo("instructions screen");
        assertThat(buttons.get(2).getText()).isEqualTo("exit game");
    }
}
