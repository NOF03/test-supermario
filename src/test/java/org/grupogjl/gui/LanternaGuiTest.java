package org.grupogjl.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LanternaGuiTest {

    private LanternaGui gui;
    private Screen mockScreen;
    private SpriteBuilder mockSpriteBuilder;
    private TextGraphics mockTextGraphics;

    @BeforeEach
    void setUp() throws IOException {
        mockScreen = mock(Screen.class);
        mockSpriteBuilder = mock(SpriteBuilder.class);
        mockTextGraphics = mock(TextGraphics.class);

        gui = new LanternaGui(mockScreen);
        gui.setSpriteBuilder(mockSpriteBuilder);

        when(mockScreen.newTextGraphics()).thenReturn(mockTextGraphics);

        // Mock the `loadImage` method to return a valid BufferedImage
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth()).thenReturn(16); // Set a dummy width
        when(mockImage.getHeight()).thenReturn(16); // Set a dummy height
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(Color.RED.getRGB());
        when(mockSpriteBuilder.loadImage(anyString())).thenReturn(mockImage);
    }

    @Test
    void testSetScreenAndBehavior() throws IOException {
        // Arrange
        Screen newScreen = mock(Screen.class);

        // Act
        gui.setScreen(newScreen);
        gui.refresh(); // Call a method that uses the screen

        // Assert
        verify(newScreen).refresh(); // Verify the new screen is used
    }

    @Test
    void testDrawMenuTextWithoutColor() {
        gui.drawMenuText(10, 20, "Hello");

        // Verify the `loadImage` method is called for each character
        verify(mockSpriteBuilder, times(5)).loadImage(anyString());
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), eq(20), anyString());
    }

    @Test
    void testDrawMenuTextWithColor() {
        gui.drawMenuText(10, 20, "Hi", "yellow");

        verify(mockSpriteBuilder, times(2)).loadImage(anyString());
    }

    @Test
    void testDrawMenuText() {
        gui.drawMenuText(10, 20, "Hello: My name is Mario! - How are you? I am fine.");

        // Verify the `loadImage` method is called for each character
        verify(mockSpriteBuilder, times(39)).loadImage(anyString());
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), eq(20), anyString());
    }


    @Test
    void testDrawPixel() {
        gui.drawPixel(5, 10, "#00ff00");

        verify(mockTextGraphics).setForegroundColor(TextColor.Factory.fromString("#00ff00"));
        verify(mockTextGraphics).putString(5, 10, " ");
    }

    @Test
    void testIsTransparent() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getRGB(0, 0)).thenReturn(0x00FFFFFF); // Fully transparent pixel

        boolean result = gui.isTransparent(mockImage, 0, 0);

        assertThat(result).isTrue();
        verify(mockImage).getRGB(0, 0);
    }

    @Test
    void testDrawImage() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth()).thenReturn(2);
        when(mockImage.getHeight()).thenReturn(2);
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(Color.RED.getRGB());
        when(mockSpriteBuilder.loadImage("test.png")).thenReturn(mockImage);

        gui.drawImage(1.5f, 2.5f, "test.png");

        verify(mockSpriteBuilder).loadImage("test.png");
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
    }

    @Test
    void testDrawImageWithTransparentPixels() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth()).thenReturn(2);
        when(mockImage.getHeight()).thenReturn(2);
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(0x00FFFFFF); // Fully transparent pixel
        when(mockSpriteBuilder.loadImage("test.png")).thenReturn(mockImage);

        gui.drawImage(1.5f, 2.5f, "test.png");

        // Verify the image is loaded
        verify(mockSpriteBuilder).loadImage("test.png");

        // Verify that no pixel is drawn (since all are transparent)
        verify(mockTextGraphics, never()).putString(anyInt(), anyInt(), anyString());
    }

    @Test
    void testDrawMenuImageWithoutColor() {
        gui.drawMenuImage(5, 10, "menu.png");

        // Verify that the image is loaded and drawn
        verify(mockSpriteBuilder).loadImage("menu.png");
        verify(mockTextGraphics, atLeastOnce()).putString(anyInt(), anyInt(), anyString());
    }

    @Test
    void testDrawMenuImageWithTransparentPixels() {
        BufferedImage mockImage = mock(BufferedImage.class);
        when(mockImage.getWidth()).thenReturn(2);
        when(mockImage.getHeight()).thenReturn(2);
        when(mockImage.getRGB(anyInt(), anyInt())).thenReturn(0x00FFFFFF); // Fully transparent pixel
        when(mockSpriteBuilder.loadImage("test.png")).thenReturn(mockImage);

        gui.drawMenuImage(2, 3, "test.png");

        // Verify the image is loaded
        verify(mockSpriteBuilder).loadImage("test.png");

        // Verify that no pixel is drawn (since all are transparent)
        verify(mockTextGraphics, never()).putString(anyInt(), anyInt(), anyString());
    }

    @Test
    void testDrawGameOver() throws IOException {
        gui.drawGameOver();

        verify(mockSpriteBuilder).loadImage("gameOver.png");
        verify(mockScreen).refresh();
    }

    @Test
    void testClear() {
        gui.clear();

        verify(mockScreen).newTextGraphics();
        verify(mockTextGraphics).fillRectangle(any(), any(), anyChar());
    }

    @Test
    void testRefresh() throws IOException {
        gui.refresh();

        verify(mockScreen).refresh();
    }

    @Test
    void testGetNextActionWithArrowKeys() throws IOException {
        KeyStroke mockKeyStroke = mock(KeyStroke.class);
        when(mockKeyStroke.getKeyType()).thenReturn(KeyType.ArrowUp);
        when(mockScreen.pollInput()).thenReturn(mockKeyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.UP);
    }

    @Test
    void testGetNextAction_NullKeyStroke() throws IOException {
        when(mockScreen.pollInput()).thenReturn(null);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.NONE);
    }

    @Test
    void testGetNextAction_EOFKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.EOF);
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.QUIT);
    }

    @Test
    void testGetNextAction_QuitKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke.getCharacter()).thenReturn('q');
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.QUIT);
    }

    @Test
    void testGetNextAction_ArrowUpKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowUp);
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.UP);
    }

    @Test
    void testGetNextAction_ArrowRightKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowRight);
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.RIGHT);
    }

    @Test
    void testGetNextAction_ArrowDownKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowDown);
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.DOWN);
    }

    @Test
    void testGetNextAction_ArrowLeftKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.ArrowLeft);
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.LEFT);
    }

    @Test
    void testGetNextAction_ThrowBallKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke.getCharacter()).thenReturn('b');
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.THROWBALL);
    }

    @Test
    void testGetNextAction_SelectKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.SELECT);
    }

    @Test
    void testGetNextAction_UnknownKeyStroke() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Tab); // Example of an unknown key
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.NONE);
    }

    @Test
    void testGetNextAction_ThrowBallKeyStrokeFailed1() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);
        when(keyStroke.getCharacter()).thenReturn('b');
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.SELECT);
    }

    @Test
    void testGetNextAction_ThrowBallKeyStrokeFailed2() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(keyStroke.getCharacter()).thenReturn('d');
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.NONE);
    }

    @Test
    void testGetNextAction_ThrowBallKeyStrokeFailed3() throws IOException {
        KeyStroke keyStroke = mock(KeyStroke.class);
        when(keyStroke.getKeyType()).thenReturn(KeyType.Enter);
        when(keyStroke.getCharacter()).thenReturn('d');
        when(mockScreen.pollInput()).thenReturn(keyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.SELECT);
    }


    @Test
    void testGetNextActionWithQuit() throws IOException {
        KeyStroke mockKeyStroke = mock(KeyStroke.class);
        when(mockKeyStroke.getKeyType()).thenReturn(KeyType.Character);
        when(mockKeyStroke.getCharacter()).thenReturn('q');
        when(mockScreen.pollInput()).thenReturn(mockKeyStroke);

        GeneralGui.ACTION action = gui.getNextAction();

        assertThat(action).isEqualTo(GeneralGui.ACTION.QUIT);
    }

}
