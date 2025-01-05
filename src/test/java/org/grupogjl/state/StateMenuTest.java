package org.grupogjl.state;

import org.grupogjl.Game;
import org.grupogjl.controller.ControllerMenu;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.gui.LanternaGui;
import org.grupogjl.model.game.elements.menu.MenuModel;
import org.grupogjl.viewer.ViewerMenu;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StateMenuTest {

    @Test
    public void testGetModel() {
        // Arrange
        StateMenu stateMenu = new StateMenu();

        // Act
        MenuModel model = stateMenu.getModel();

        // Assert
        assertThat(model).isNotNull(); // Ensure the model is initialized
        assertThat(model.getButtons()).hasSize(3); // Verify default buttons
    }

    @Test
    public void testGetState() {
        // Arrange
        StateMenu stateMenu = new StateMenu();

        // Act
        int state = stateMenu.getState();

        // Assert
        assertThat(state).isEqualTo(1); // StateMenu's state value
    }

    @Test
    public void testStepCallsControllerAndViewer() throws IOException {
        // Arrange
        Game mockGame = mock(Game.class);
        LanternaGui mockGui = mock(LanternaGui.class);
        ControllerMenu mockController = mock(ControllerMenu.class);
        ViewerMenu mockViewer = mock(ViewerMenu.class);
        StateMenu stateMenu = new StateMenu();

        stateMenu.setController(mockController);
        stateMenu.setViewer(mockViewer);

        when(mockGui.getNextAction()).thenReturn(GeneralGui.ACTION.SELECT);

        // Act
        stateMenu.step(mockGame, mockGui, 123L);

        // Assert
        verify(mockController).step(eq(mockGame), eq(GeneralGui.ACTION.SELECT), eq(123L));
        verify(mockViewer).draw(eq(stateMenu), eq(mockGui));
    }

    @Test
    public void testStepThrowsIOException() throws IOException {
        // Arrange
        Game mockGame = mock(Game.class);
        LanternaGui mockGui = mock(LanternaGui.class);
        ControllerMenu mockController = mock(ControllerMenu.class);
        ViewerMenu mockViewer = mock(ViewerMenu.class);
        StateMenu stateMenu = new StateMenu();

        stateMenu.setController(mockController);
        stateMenu.setViewer(mockViewer);

        when(mockGui.getNextAction()).thenThrow(new IOException("Mock IOException"));

        // Act & Assert
        try {
            stateMenu.step(mockGame, mockGui, 123L);
        } catch (IOException e) {
            assertThat(e).hasMessage("Mock IOException");
        }

        verifyNoInteractions(mockController);
        verifyNoInteractions(mockViewer);
    }

    @Test
    public void testSetController() {
        // Arrange
        StateMenu stateMenu = new StateMenu();
        ControllerMenu mockController = mock(ControllerMenu.class);

        // Act
        stateMenu.setController(mockController);

        // Assert
        assertThat(stateMenu.getController()).isEqualTo(mockController);
    }

    @Test
    public void testSetViewer() {
        // Arrange
        StateMenu stateMenu = new StateMenu();
        ViewerMenu mockViewer = mock(ViewerMenu.class);

        // Act
        stateMenu.setViewer(mockViewer);

        // Assert
        assertThat(stateMenu.getViewer()).isEqualTo(mockViewer);
    }
}
