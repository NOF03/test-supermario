package org.grupogjl.model.game.elements.pause;

import org.grupogjl.commands.ExitToMenuCommand;
import org.grupogjl.commands.ResumeCommand;
import org.grupogjl.model.game.elements.buttons.Button;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

class PauseModelTest {

    @Test
    void testPauseModelInitialization() {
        PauseModel pauseModel = new PauseModel();

        Vector<Button> buttons = pauseModel.getButtons();
        assertThat(buttons).hasSize(2);
        assertThat(buttons.get(0).getText()).isEqualTo("resume");
        assertThat(buttons.get(1).getText()).isEqualTo("exit to menu");

    }
}
