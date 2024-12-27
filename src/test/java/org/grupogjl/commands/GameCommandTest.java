package org.grupogjl.commands;

import org.grupogjl.Game;
import org.grupogjl.model.game.elements.menu.MenuModel;
import org.grupogjl.state.StateGame;
import org.grupogjl.state.StateMenu;
import org.grupogjl.state.StatePause;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameCommandTest {

    @Test
    void testGameOverCommand() {
        Game mockGame = mock(Game.class);
        StateGame mockStateGame = mock(StateGame.class);

        when(mockGame.getStateGame()).thenReturn(mockStateGame);

        GameOverCommand command = new GameOverCommand();
        command.execute(mockGame);

        verify(mockStateGame).setGameOver(true);
    }

    @Test
    void testInstructionsCommand() {
        // Mock the Game and its associated StateMenu and MenuModel
        Game mockGame = mock(Game.class);
        StateMenu mockStateMenu = mock(StateMenu.class);
        MenuModel mockMenuModel = mock(MenuModel.class);

        // Configure mock behavior
        when(mockGame.getStateMenu()).thenReturn(mockStateMenu);
        when(mockStateMenu.getModel()).thenReturn(mockMenuModel);

        // Execute the command
        InstructionsCommand command = new InstructionsCommand();
        command.execute(mockGame);

        // Verify interactions
        verify(mockMenuModel).setSelectedOption(true);
    }


    @Test
    void testPauseCommand() {
        Game mockGame = mock(Game.class);

        PauseCommand command = new PauseCommand();
        command.execute(mockGame);

        verify(mockGame).setStatePause();
    }

    @Test
    void testResumeCommand() {
        Game mockGame = mock(Game.class);
        StatePause mockStatePause = mock(StatePause.class);
        StateGame mockParentState = mock(StateGame.class);

        when(mockGame.getStatePause()).thenReturn(mockStatePause);
        when(mockStatePause.getParent()).thenReturn(mockParentState);

        ResumeCommand command = new ResumeCommand();
        command.execute(mockGame);

        verify(mockGame).setStateGame(mockParentState);
    }

    @Test
    void testStartGameCommand() throws IOException {
        Game mockGame = mock(Game.class);
        StateGame mockStateGame = mock(StateGame.class);

        when(mockGame.getStateGame()).thenReturn(mockStateGame);

        StartGameCommand command = new StartGameCommand();
        command.execute(mockGame);

        verify(mockGame).setStateGame();
        verify(mockStateGame).setGameOver(false);
    }

    @Test
    void testStartGameCommandException() {
        // Mock the Game class
        Game mockGame = mock(Game.class);

        // Configure the mock to throw an IOException when setStateGame() is called
        try {
            doThrow(new IOException("Simulated IOException")).when(mockGame).setStateGame();
        } catch (IOException e) {
            fail("IOException should not be thrown while setting up mocks.");
        }

        // Create the command
        StartGameCommand command = new StartGameCommand();

        // Verify the exception is thrown as a RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> command.execute(mockGame));
        assertEquals("Simulated IOException", exception.getCause().getMessage());

        // Verify no interactions with setGameOver (because setStateGame throws before it can be called)
        verify(mockGame, never()).getStateGame();
    }

    @Test
    void testExitGameCommand() {
        Game mockGame = mock(Game.class);

        ExitGameCommand command = new ExitGameCommand();
        command.execute(mockGame);

        verify(mockGame).setStateNull();
    }

    @Test
    void testExitToMenuCommand() {
        Game mockGame = mock(Game.class);

        ExitToMenuCommand command = new ExitToMenuCommand();
        command.execute(mockGame);

        verify(mockGame).setStateMenu();
    }
}
