package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.commands.GameCommand;
import org.grupogjl.controller.game.ControllerLevel;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.state.StateGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerGameTests {

    private static final long MOCK_TIME = 123L;

    @Mock
    private Game mockGame;

    @Mock
    private StateGame mockStateGame;

    @Mock
    private Level mockLevel;

    @Mock
    private Mario mockMario;

    // Name the mocks to match the fields in ControllerGame
    @Mock
    private GameCommand gameOverCommand;

    @Mock
    private GameCommand exitToMenuCommand;

    @Mock
    private GameCommand pauseCommand;

    @Mock
    private ControllerLevel controllerLevel;

    @InjectMocks
    @Spy
    private ControllerGame controllerGame;

    @BeforeEach
    void setUp() {
        when(mockGame.getStateGame()).thenReturn(mockStateGame);
        when(mockStateGame.getModel()).thenReturn(mockLevel);
        when(mockLevel.getMario()).thenReturn(mockMario);
    }

    @Test
    void shouldExecuteGameOverCommandWhenLivesAreZero() throws IOException {
        // Given
        when(mockMario.getLives()).thenReturn(0);

        // When
        controllerGame.step(mockGame, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        verify(gameOverCommand).execute(mockGame);
        verify(exitToMenuCommand, never()).execute(mockGame);
        verify(pauseCommand, never()).execute(mockGame);
        verify(controllerLevel, never()).step(any(), any(), anyLong());
    }

    @Test
    void shouldExecuteExitToMenuCommandWhenLivesAreZeroAndActionIsSelect() throws IOException {
        // Given
        when(mockMario.getLives()).thenReturn(0);

        // When
        controllerGame.step(mockGame, GeneralGui.ACTION.SELECT, MOCK_TIME);

        // Then
        verify(gameOverCommand).execute(mockGame);
        verify(exitToMenuCommand).execute(mockGame);
        verify(pauseCommand, never()).execute(mockGame);
        verify(controllerLevel, never()).step(any(), any(), anyLong());
    }

    @Test
    void shouldExecutePauseCommandWhenActionIsQuitAndLivesAreNotZero() throws IOException {
        // Given
        when(mockMario.getLives()).thenReturn(3);

        // When
        controllerGame.step(mockGame, GeneralGui.ACTION.QUIT, MOCK_TIME);

        // Then
        verify(gameOverCommand, never()).execute(mockGame);
        verify(exitToMenuCommand, never()).execute(mockGame);
        verify(pauseCommand).execute(mockGame);
        verify(controllerLevel, never()).step(any(), any(), anyLong());
    }

    @Test
    void shouldDelegateToControllerLevelWhenActionIsNotQuitAndLivesAreNotZero() throws IOException {
        // Given
        when(mockMario.getLives()).thenReturn(3);

        // When
        controllerGame.step(mockGame, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        verify(gameOverCommand, never()).execute(mockGame);
        verify(exitToMenuCommand, never()).execute(mockGame);
        verify(pauseCommand, never()).execute(mockGame);
        verify(controllerLevel).step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);
    }
}