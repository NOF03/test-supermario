package org.grupogjl.model.game.elements.menu;

import org.grupogjl.Game;
import org.grupogjl.commands.GameCommand;
import org.grupogjl.model.game.elements.buttons.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MenuPauseModelTest {

    private MenuPauseModel menuPauseModel;
    private Game mockGame;

    @BeforeEach
    public void setUp() {
        mockGame = mock(Game.class);

        menuPauseModel = new MenuPauseModel() {
            {
                buttons = new Vector<>();
                GameCommand mockCommand1 = mock(GameCommand.class);
                GameCommand mockCommand2 = mock(GameCommand.class);
                buttons.add(new Button("Option 1", mockCommand1));
                buttons.add(new Button("Option 2", mockCommand2));
            }
        };
    }

    @Test
    public void testSetAndGetSelectedOption() {
        menuPauseModel.setSelectedOption(true);
        assertThat(menuPauseModel.isSelectedOption()).isTrue();
    }

    @Test
    public void testSetAndGetTextOption() {
        menuPauseModel.setTextOption("New Option");
        assertThat(menuPauseModel.getTextOption()).isEqualTo("New Option");
    }

    @Test
    public void testGetButtons() {
        assertThat(menuPauseModel.getButtons()).hasSize(2);
    }

    @Test
    public void testGetSelectedButton() {
        assertThat(menuPauseModel.getSelectedButton()).isEqualTo((byte) 0);
    }

    @Test
    public void testNextPosition() {
        menuPauseModel.nextPosition();
        assertThat(menuPauseModel.getSelectedButton()).isEqualTo((byte) 1);

        menuPauseModel.nextPosition(); // Try to go beyond the limit
        assertThat(menuPauseModel.getSelectedButton()).isEqualTo((byte) 1); // Should stay at 1
    }

    @Test
    public void testLastPosition() {
        menuPauseModel.nextPosition(); // Move to position 1
        menuPauseModel.lastPosition();
        assertThat(menuPauseModel.getSelectedButton()).isEqualTo((byte) 0);

        menuPauseModel.lastPosition(); // Try to go below 0
        assertThat(menuPauseModel.getSelectedButton()).isEqualTo((byte) 0); // Should stay at 0
    }

    @Test
    public void testExecute() {
        Button mockButton = mock(Button.class);
        menuPauseModel.getButtons().set(0, mockButton);

        menuPauseModel.execute(mockGame);
        verify(mockButton).OnPressed(mockGame);
    }
}
