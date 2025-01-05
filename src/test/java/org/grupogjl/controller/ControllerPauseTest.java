package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.pause.PauseModel;
import org.grupogjl.state.StatePause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerPauseTest {

    private static final long MOCK_TIME = 123L;

    @Mock
    private Game mockGame;

    @Mock
    private StatePause mockStatePause;

    @Mock
    private PauseModel mockMenu;

    @InjectMocks
    @Spy
    private ControllerPause controllerPause;

    @BeforeEach
    public void setUp() {
        when(mockGame.getStatePause()).thenReturn(mockStatePause);
        when(mockStatePause.getModel()).thenReturn(mockMenu);
    }

    @Test
    public void shouldGoToNextPositionWhenActionIsDown() {
        // When
        controllerPause.step(mockGame, GeneralGui.ACTION.DOWN, MOCK_TIME);

        // Then
        verify(mockMenu).nextPosition();
        verify(mockMenu, never()).lastPosition();
        verify(mockMenu, never()).execute(any());
    }

    @Test
    public void shouldGoToLastPositionWhenActionIsUp() {
        // When
        controllerPause.step(mockGame, GeneralGui.ACTION.UP, MOCK_TIME);

        // Then
        verify(mockMenu).lastPosition();
        verify(mockMenu, never()).nextPosition();
        verify(mockMenu, never()).execute(any());
    }

    @Test
    public void shouldExecuteOptionWhenActionIsSelect() {
        // When
        controllerPause.step(mockGame, GeneralGui.ACTION.SELECT, MOCK_TIME);

        // Then
        verify(mockMenu).execute(mockGame);
        verify(mockMenu, never()).nextPosition();
        verify(mockMenu, never()).lastPosition();
    }
}
