package org.grupogjl.gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpriteBuilder {
    private final Map<String, BufferedImage> cache;
    public SpriteBuilder() {
        this.cache = new HashMap<>();
    }
    public boolean isInCache(String name) {
        return cache.containsKey(name);
    }
    public void setToCache(String name, BufferedImage image) {
        cache.put(name, image);
    }
    public BufferedImage loadImage(String filename) {
        if (isInCache(filename)) return cache.get(filename);
        BufferedImage image = null;
        try {
            URL resource = SpriteBuilder.class.getResource("/Sprites/" + filename);
            if (resource != null) {
                image = ImageIO.read(resource);
                setToCache(filename, image);
            } else {
                throw new IOException("Resource not found: " + filename);
            }
        } catch (IOException e) {
            Logger.getLogger(SpriteBuilder.class.getName()).log(Level.SEVERE, "Error reading image: " + filename, e);
        }
        return image;
    }
}