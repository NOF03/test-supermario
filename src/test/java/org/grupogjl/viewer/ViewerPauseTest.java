package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.buttons.Button;
import org.grupogjl.model.game.elements.pause.PauseModel;
import org.grupogjl.state.StatePause;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.mockito.Mockito.*;

class ViewerPauseTest {

    @Test
    void testDraw() throws Exception {
        // Arrange
        GeneralGui mockGui = mock(GeneralGui.class);
        StatePause mockStatePause = mock(StatePause.class);
        PauseModel mockModel = mock(PauseModel.class);

        ViewerPause viewerPause = new ViewerPause();

        Button resumeButton = new Button("Resume", null); // Concrete Button
        Button exitButton = new Button("Exit to Menu", null); // Concrete Button

        when(mockStatePause.getModel()).thenReturn(mockModel);
        when(mockModel.getButtons()).thenReturn(new Vector<>(java.util.List.of(resumeButton, exitButton)));
        when(mockModel.getSelectedButton()).thenReturn((byte) 1);

        // Act
        viewerPause.draw(mockStatePause, mockGui);

        // Assert
        verify(mockGui).drawMenuText((416 - "Resume".length() * 8) / 2 + 1, 89, "Resume", "");
        verify(mockGui).drawMenuText((416 - "Exit to Menu".length() * 8) / 2 + 1, 113, "Exit to Menu", "#ea9e22");
        verify(mockGui).refresh();

        verifyNoMoreInteractions(mockGui);
    }
}
