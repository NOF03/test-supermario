package org.grupogjl.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GeneralGuiTest {

    private GeneralGui gui;

    @BeforeEach
    public void setUp() {
        // Mock GeneralGui interface
        gui = mock(GeneralGui.class);
    }

    @Test
    public void testDrawMenuTextWithoutColor() {
        gui.drawMenuText(10, 20, "Hello");

        // Verify that drawMenuText was called with the correct parameters
        verify(gui).drawMenuText(10, 20, "Hello");
    }

    @Test
    public void testDrawMenuTextWithColor() {
        gui.drawMenuText(10, 20, "Hello", "red");

        // Verify that drawMenuText was called with the correct parameters
        verify(gui).drawMenuText(10, 20, "Hello", "red");
    }

    @Test
    public void testClear() {
        gui.clear();

        // Verify that clear was called
        verify(gui).clear();
    }

    @Test
    public void testRefresh() throws IOException {
        gui.refresh();

        // Verify that refresh was called
        verify(gui).refresh();
    }

    @Test
    public void testDrawImage() {
        gui.drawImage(10.5f, 20.5f, "image.png");

        // Verify that drawImage was called with the correct parameters
        verify(gui).drawImage(10.5f, 20.5f, "image.png");
    }

    @Test
    public void testIsTransparent() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(gui.isTransparent(mockImage, 5, 10)).thenReturn(true);

        boolean result = gui.isTransparent(mockImage, 5, 10);

        // Verify the behavior and return value
        assertThat(result).isTrue();
        verify(gui).isTransparent(mockImage, 5, 10);
    }

    @Test
    public void testDrawPixel() {
        gui.drawPixel(10, 20, "blue");

        // Verify that drawPixel was called with the correct parameters
        verify(gui).drawPixel(10, 20, "blue");
    }

    @Test
    public void testDrawMenuImageWithoutColor() {
        gui.drawMenuImage(10, 20, "menuImage.png");

        // Verify that drawMenuImage was called with the correct parameters
        verify(gui).drawMenuImage(10, 20, "menuImage.png");
    }

    @Test
    public void testDrawMenuImageWithColor() {
        gui.drawMenuImage(10, 20, "menuImage.png", "yellow");

        // Verify that drawMenuImage was called with the correct parameters
        verify(gui).drawMenuImage(10, 20, "menuImage.png", "yellow");
    }

    @Test
    public void testDrawGameOver() throws IOException {
        gui.drawGameOver();

        // Verify that drawGameOver was called
        verify(gui).drawGameOver();
    }

    @Test
    public void testActionEnumValues() {
        GeneralGui.ACTION[] actions = GeneralGui.ACTION.values();

        // Verify all enum values
        assertThat(actions).containsExactly(
                GeneralGui.ACTION.UP,
                GeneralGui.ACTION.RIGHT,
                GeneralGui.ACTION.DOWN,
                GeneralGui.ACTION.LEFT,
                GeneralGui.ACTION.NONE,
                GeneralGui.ACTION.QUIT,
                GeneralGui.ACTION.SELECT,
                GeneralGui.ACTION.THROWBALL
        );
    }
}
