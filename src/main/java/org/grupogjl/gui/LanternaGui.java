package org.grupogjl.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaGui implements GeneralGui {
    private Screen screen;
    private SpriteBuilder spriteBuilder;
    public LanternaGui(Screen screen) {
        this.screen = screen;
    }
    public LanternaGui(int width, int height) throws IOException, URISyntaxException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = loadSquareFont();
        Terminal terminal = createTerminal(width, height, fontConfig);
        this.screen = createScreen(terminal);
        this.spriteBuilder = new SpriteBuilder();
    }
    public void setSpriteBuilder(SpriteBuilder spriteBuilder) {
        this.spriteBuilder = spriteBuilder;
    }
    public void setScreen(Screen screen) {
        this.screen = screen;
    }
    private Screen createScreen(Terminal terminal) throws IOException {
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        screen.doResizeIfNecessary();
        return screen;
    }
    private Terminal createTerminal(int width, int height, AWTTerminalFontConfiguration fontConfig) throws IOException {
        TerminalSize terminalsSize = new TerminalSize(width, height + 1);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalsSize);
        terminalFactory.setForceAWTOverSwing(true);
        terminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
        Terminal terminal = terminalFactory.createTerminal();
        return terminal;
    }
    private AWTTerminalFontConfiguration loadSquareFont() throws URISyntaxException, IOException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("Fonts/square.ttf");
        File fontFile = new File(resource.toURI());
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);

        Font loadedFont = font.deriveFont(Font.PLAIN, 3);
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);
        return fontConfig;
    }
    public ACTION getNextAction() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) return ACTION.NONE;
        if (keyStroke.getKeyType() == KeyType.EOF) return ACTION.QUIT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') return ACTION.QUIT;
        if (keyStroke.getKeyType() == KeyType.ArrowUp) return ACTION.UP;
        if (keyStroke.getKeyType() == KeyType.ArrowRight) return ACTION.RIGHT;
        if (keyStroke.getKeyType() == KeyType.ArrowDown) return ACTION.DOWN;
        if (keyStroke.getKeyType() == KeyType.ArrowLeft) return ACTION.LEFT;
        if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'b') return ACTION.THROWBALL;
        if (keyStroke.getKeyType() == KeyType.Enter) return ACTION.SELECT;
        return ACTION.NONE;
    }
    @Override
    public void drawImage(float x, float y, String filename) {
        BufferedImage image = spriteBuilder.loadImage(filename);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color c = new Color(image.getRGB(i, j));
                String color = "#" + Integer.toHexString(c.getRGB()).substring(2);
                if (!isTransparent(image, i, j)) {
                    drawPixel((int) Math.floor(x * 16 + i), (int) Math.floor(y * 16 + j), color);
                }
            }
        }
    }
    @Override
    public boolean isTransparent(BufferedImage image, int x, int y) {
        int pixel = image.getRGB(x, y);
        return (pixel >> 24) == 0x00;
    }
    @Override
    public void drawPixel(int x, int y, String color) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setForegroundColor(TextColor.Factory.fromString(color));
        tg.setBackgroundColor(TextColor.Factory.fromString(color));
        tg.putString(x, y, " ");
    }
    @Override
    public void drawMenuImage(int x, int y, String filename) {
        drawMenuImage(x, y, filename, "");
    }
    @Override
    public void drawMenuImage(int x, int y, String filename, String dcolor) {
        BufferedImage image = spriteBuilder.loadImage(filename);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                String color;
                if (dcolor.equals("")) {
                    Color c = new Color(image.getRGB(i, j));
                    color = "#" + Integer.toHexString(c.getRGB()).substring(2);
                } else {
                    color = dcolor;
                }
                if (!isTransparent(image, i, j)) {
                    drawPixel((int) Math.floor(x + i), (int) Math.floor(y + j), color);
                }
            }
        }
    }
    @Override
    public void drawMenuText(int x, int y, String text) {
        drawMenuText(x, y, text, "");
    }
    @Override
    public void drawMenuText(int x, int y, String text, String color) {
        int newx = x;
        String filename;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                newx += 8;
            } else if (text.charAt(i) == '.') {
                filename = "/Letters/dot.png";
                drawMenuImage(newx, y, filename, color);
                newx += 8;
            } else if (text.charAt(i) == ':') {
                filename = "/Letters/doubledot.png";
                drawMenuImage(newx, y, filename, color);
                newx += 8;
            } else if (text.charAt(i) == '-') {
                filename = "/Letters/hifen.png";
                drawMenuImage(newx, y, filename, color);
                newx += 8;
            } else if (text.charAt(i) == '!') {
                filename = "/Letters/exclamationMark.png";
                drawMenuImage(newx, y, filename, color);
                newx += 8;
            } else {
                filename = "/Letters/" + text.charAt(i) + ".png";
                drawMenuImage(newx, y, filename, color);
                newx += 8;
            }
        }
    }
    @Override
    public void drawGameOver() throws IOException {
        clear();
        drawMenuImage(65, 89, "gameOver.png");
        refresh();
    }
    @Override
    public void clear() {
        TextGraphics tg = screen.newTextGraphics();
        tg.setBackgroundColor(TextColor.Factory.fromString("#6c9cfc"));
        tg.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');
    }
    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }
}
