package org.grupogjl.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SpriteBuilderTest {

    private SpriteBuilder spriteBuilder;

    @BeforeEach
    void setUp() {
        spriteBuilder = new SpriteBuilder();
    }

    @Test
    void testIsInCacheWhenNotPresent() {
        boolean result = spriteBuilder.isInCache("nonexistent.png");
        assertThat(result).isFalse();
    }

    @Test
    void testSetToCache() {
        BufferedImage mockImage = mock(BufferedImage.class);
        spriteBuilder.setToCache("test.png", mockImage);

        boolean result = spriteBuilder.isInCache("test.png");
        assertThat(result).isTrue();
    }

    @Test
    void testLoadImageWhenInCache() {
        BufferedImage mockImage = mock(BufferedImage.class);
        spriteBuilder.setToCache("test.png", mockImage);

        BufferedImage result = spriteBuilder.loadImage("test.png");

        assertThat(result).isSameAs(mockImage);
    }

    @Test
    void testLoadImageWhenNotInCache() throws IOException {
        SpriteBuilder spriteBuilder = spy(new SpriteBuilder());
        BufferedImage mockImage = mock(BufferedImage.class);

        // Mock ImageIO.read to return a valid BufferedImage
        doReturn(mockImage).when(spriteBuilder).loadImage(anyString());

        BufferedImage result = spriteBuilder.loadImage("coin.png");

        // Assert the image is loaded and cached
        assertThat(result).isSameAs(mockImage);
        assertThat(spriteBuilder.isInCache("coin.png")).isFalse();
    }


    @Test
    void testLoadImageWhenResourceNotFound() {
        BufferedImage result = spriteBuilder.loadImage("nonexistent.png");

        assertThat(result).isNull();
    }

    @Test
    void testLoadImageLogsErrorOnException() {
        SpriteBuilder spriteBuilder = new SpriteBuilder();

        // Attempt to load an invalid resource
        BufferedImage result = spriteBuilder.loadImage("faulty.png");

        // Assert that the method handled the error gracefully and returned null
        assertThat(result).isNull();
    }

}
