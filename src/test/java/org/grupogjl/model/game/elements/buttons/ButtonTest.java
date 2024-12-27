package org.grupogjl.model.game.elements.buttons;

import org.grupogjl.Game;
import org.grupogjl.commands.GameCommand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ButtonTest {

    @Test
    void testButtonInitialization() {
        GameCommand mockCommand = mock(GameCommand.class);
        Button button = new Button("Start", mockCommand);

        // Test button text
        assertThat(button.getText()).isEqualTo("Start");
    }

    @Test
    void testButtonOnPressed() {
        GameCommand mockCommand = mock(GameCommand.class);
        Game mockGame = mock(Game.class);
        Button button = new Button("Start", mockCommand);

        // Simulate button press
        button.OnPressed(mockGame);

        // Verify command execution
        verify(mockCommand).execute(mockGame);
    }
}
