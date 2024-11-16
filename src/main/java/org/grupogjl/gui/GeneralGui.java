package org.grupogjl.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface GeneralGui {
    void drawMenuText(int x, int y, String text);
    void drawMenuText(int x, int y, String text, String color);
    void clear();
    void refresh() throws IOException;
    void drawImage(float x, float y, String filename);
    boolean isTransparent(BufferedImage image, int x, int y);
    void drawPixel(int x, int y, String color);
    void drawMenuImage(int i, int i1, String image);
    void drawMenuImage(int x, int y, String filename, String dcolor);
    void drawGameOver() throws IOException;
    enum ACTION {UP, RIGHT, DOWN, LEFT, NONE, QUIT, SELECT, THROWBALL}
}
