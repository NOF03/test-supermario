package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.ControllerPause;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.model.game.elements.pause.PauseModel;
import org.grupogjl.state.StateGame;
import org.grupogjl.state.StatePause;
import org.grupogjl.viewer.ViewerPause;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StatePauseTest {

    @Test
    void testGetModel() {
        // Arrange
        StateGame mockParent = mock(StateGame.class);
        StatePause statePause = new StatePause(mockParent);

        // Act
        PauseModel model = statePause.getModel();

        // Assert
        assertThat(model).isNotNull(); // Verify the model is initialized
        assertThat(model.getButtons()).hasSize(2); // Verify default buttons
    }

    @Test
    void testGetState() {
        // Arrange
        StateGame mockParent = mock(StateGame.class);
        StatePause statePause = new StatePause(mockParent);

        // Act
        int state = statePause.getState();

        // Assert
        assertThat(state).isEqualTo(3); // StatePause's state value
    }

    @Test
    void testGetParent() {
        // Arrange
        StateGame mockParent = mock(StateGame.class);
        StatePause statePause = new StatePause(mockParent);

        // Act
        StateGame parent = statePause.getParent();

        // Assert
        assertThat(parent).isEqualTo(mockParent); // Verify parent is returned correctly
    }

    @Test
    void testStepCallsControllerParentAndViewer() throws IOException {
        // Arrange
        Game mockGame = mock(Game.class);
        LanternaGui mockGui = mock(LanternaGui.class);
        StateGame mockParent = mock(StateGame.class);
        ControllerPause mockController = mock(ControllerPause.class);
        ViewerPause mockViewer = mock(ViewerPause.class);
        StatePause statePause = new StatePause(mockParent);

        statePause.setController(mockController);
        statePause.setViewer(mockViewer);

        when(mockGui.getNextAction()).thenReturn(GeneralGui.ACTION.SELECT);

        // Act
        statePause.step(mockGame, mockGui, 123L);

        // Assert
        verify(mockController).step(eq(mockGame), eq(GeneralGui.ACTION.SELECT), eq(123L));
        verify(mockParent).draw(eq(mockGui));
        verify(mockViewer).draw(eq(statePause), eq(mockGui));
    }

    @Test
    void testStepThrowsIOException() throws IOException {
        // Arrange
        Game mockGame = mock(Game.class);
        LanternaGui mockGui = mock(LanternaGui.class);
        StateGame mockParent = mock(StateGame.class);
        ControllerPause mockController = mock(ControllerPause.class);
        ViewerPause mockViewer = mock(ViewerPause.class);
        StatePause statePause = new StatePause(mockParent);

        statePause.setController(mockController);
        statePause.setViewer(mockViewer);

        when(mockGui.getNextAction()).thenThrow(new IOException("Mock IOException"));

        // Act & Assert
        try {
            statePause.step(mockGame, mockGui, 123L);
        } catch (IOException e) {
            assertThat(e).hasMessage("Mock IOException");
        }

        verifyNoInteractions(mockController);
        verifyNoInteractions(mockViewer);
        verifyNoInteractions(mockParent);
    }

    @Test
    void testSetController() {
        // Arrange
        StateGame mockParent = mock(StateGame.class);
        StatePause statePause = new StatePause(mockParent);
        ControllerPause mockController = mock(ControllerPause.class);

        // Act
        statePause.setController(mockController);

        // Assert
        assertThat(statePause.getController()).isEqualTo(mockController);
    }

    @Test
    void testSetViewer() {
        // Arrange
        StateGame mockParent = mock(StateGame.class);
        StatePause statePause = new StatePause(mockParent);
        ViewerPause mockViewer = mock(ViewerPause.class);

        // Act
        statePause.setViewer(mockViewer);

        // Assert
        assertThat(statePause.getViewer()).isEqualTo(mockViewer);
    }
}
