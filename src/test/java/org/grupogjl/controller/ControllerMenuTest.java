package org.grupogjl.controller;

import org.grupogjl.Game;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.menu.MenuModel;
import org.grupogjl.state.StateMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerMenuTest {

    private static final long MOCK_TIME = 123L;

    @Mock
    private Game mockGame;

    @Mock
    private StateMenu mockStateMenu;

    @Mock
    private MenuModel mockMenu;

    @InjectMocks
    @Spy
    private ControllerMenu controllerMenu;

    @BeforeEach
    void setUp() {
        when(mockGame.getStateMenu()).thenReturn(mockStateMenu);
        when(mockStateMenu.getModel()).thenReturn(mockMenu);
    }

    @Test
    void shouldDeselectOptionWhenOptionIsSelectedAndActionIsSelect() {
        // Given
        when(mockMenu.isSelectedOption()).thenReturn(true);

        // When
        controllerMenu.step(mockGame, GeneralGui.ACTION.SELECT, MOCK_TIME);

        // Then
        verify(mockMenu).setSelectedOption(false);
        verify(mockMenu, never()).nextPosition();
        verify(mockMenu, never()).lastPosition();
        verify(mockMenu, never()).execute(any());
    }

    @Test
    void shouldDeselectOptionWhenOptionIsSelectedAndActionIsNotSelect() {
        // Given
        when(mockMenu.isSelectedOption()).thenReturn(true);

        // When
        controllerMenu.step(mockGame, GeneralGui.ACTION.DOWN, MOCK_TIME);

        // Then
        verify(mockMenu, never()).setSelectedOption(false);
        verify(mockMenu, never()).nextPosition();
        verify(mockMenu, never()).lastPosition();
        verify(mockMenu, never()).execute(any());
    }

    @Test
    void shouldGoToNextPositionWhenOptionIsNotSelectedAndActionIsDown() {
        // Given
        when(mockMenu.isSelectedOption()).thenReturn(false);

        // When
        controllerMenu.step(mockGame, GeneralGui.ACTION.DOWN, MOCK_TIME);

        // Then
        verify(mockMenu).nextPosition();
        verify(mockMenu, never()).lastPosition();
        verify(mockMenu, never()).setSelectedOption(anyBoolean());
        verify(mockMenu, never()).execute(any());
    }

    @Test
    void shouldGoToLastPositionWhenOptionIsNotSelectedAndActionIsUp() {
        // Given
        when(mockMenu.isSelectedOption()).thenReturn(false);

        // When
        controllerMenu.step(mockGame, GeneralGui.ACTION.UP, MOCK_TIME);

        // Then
        verify(mockMenu).lastPosition();
        verify(mockMenu, never()).nextPosition();
        verify(mockMenu, never()).setSelectedOption(anyBoolean());
        verify(mockMenu, never()).execute(any());
    }

    @Test
    void shouldExecuteOptionWhenOptionIsNotSelectedAndActionIsSelect() {
        // Given
        when(mockMenu.isSelectedOption()).thenReturn(false);

        // When
        controllerMenu.step(mockGame, GeneralGui.ACTION.SELECT, MOCK_TIME);

        // Then
        verify(mockMenu).execute(mockGame);
        verify(mockMenu, never()).nextPosition();
        verify(mockMenu, never()).lastPosition();
        verify(mockMenu, never()).setSelectedOption(anyBoolean());
    }
}