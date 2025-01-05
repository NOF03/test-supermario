package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.buttons.Button;
import org.grupogjl.model.game.elements.menu.MenuModel;
import org.grupogjl.state.StateMenu;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import static org.mockito.Mockito.*;

class ViewerMenuTest {

    @Test
    public void testDrawWithSelectedOption() throws IOException {
        // Arrange
        GeneralGui mockGui = mock(GeneralGui.class);
        StateMenu mockStateMenu = mock(StateMenu.class);
        MenuModel mockMenuModel = mock(MenuModel.class);

        ViewerMenu viewerMenu = new ViewerMenu();

        when(mockStateMenu.getModel()).thenReturn(mockMenuModel);
        when(mockMenuModel.isSelectedOption()).thenReturn(true);
        when(mockMenuModel.getTextOption()).thenReturn("Line1\nLine2\nLine3");

        List<String> lines = List.of("Line1", "Line2", "Line3");

        // Act
        viewerMenu.draw(mockStateMenu, mockGui);

        // Assert
        verify(mockGui).clear();

        // Dynamically calculate the y positions
        int y = 1;
        for (String line : lines) {
            verify(mockGui).drawMenuText((416 - line.length() * 8) / 2 + 1, y * 12, line, "");
            y++;
        }

        String backText = "press enter to go back to menu";
        verify(mockGui).drawMenuText((416 - backText.length() * 8) / 2 + 1, ++y * 12, backText, "");

        verify(mockGui).refresh();
        verifyNoMoreInteractions(mockGui);
    }

    @Test
    public void testDrawWithButtons() throws IOException {
        // Arrange
        GeneralGui mockGui = mock(GeneralGui.class);
        StateMenu mockStateMenu = mock(StateMenu.class);
        MenuModel mockMenuModel = mock(MenuModel.class);

        ViewerMenu viewerMenu = new ViewerMenu();

        Vector<Button> buttons = new Vector<>();
        buttons.add(new Button("Start Game", null));
        buttons.add(new Button("Exit", null));

        when(mockStateMenu.getModel()).thenReturn(mockMenuModel);
        when(mockMenuModel.isSelectedOption()).thenReturn(false);
        when(mockMenuModel.getButtons()).thenReturn(buttons);
        when(mockMenuModel.getSelectedButton()).thenReturn((byte) 1);

        // Act
        viewerMenu.draw(mockStateMenu, mockGui);

        // Assert
        verify(mockGui).clear();

        // Verify buttons are drawn
        verify(mockGui).drawMenuText((416 - "Start Game".length() * 8) / 2 + 1, 121, "Start Game", "");
        verify(mockGui).drawMenuText((416 - "Exit".length() * 8) / 2 + 1, 145, "Exit", "#ea9e22");

        // Verify background image
        verify(mockGui).drawMenuImage(121, 17, "MenuScreen.png");

        verify(mockGui).refresh();
        verifyNoMoreInteractions(mockGui);
    }

    @Test
    public void testDrawWithEmptyButtons() throws IOException {
        // Arrange
        GeneralGui mockGui = mock(GeneralGui.class);
        StateMenu mockStateMenu = mock(StateMenu.class);
        MenuModel mockMenuModel = mock(MenuModel.class);

        ViewerMenu viewerMenu = new ViewerMenu();

        when(mockStateMenu.getModel()).thenReturn(mockMenuModel);
        when(mockMenuModel.isSelectedOption()).thenReturn(false);
        when(mockMenuModel.getButtons()).thenReturn(new Vector<>());

        // Act
        viewerMenu.draw(mockStateMenu, mockGui);

        // Assert
        verify(mockGui).clear();

        // Verify background image
        verify(mockGui).drawMenuImage(121, 17, "MenuScreen.png");

        verify(mockGui).refresh();
        verifyNoMoreInteractions(mockGui);
    }
}
