package org.grupogjl.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SpriteBuilderTest {

    private SpriteBuilder spriteBuilder;

    @BeforeEach
    public void setUp() {
        spriteBuilder = new SpriteBuilder();
    }

    @Test
    public void testIsInCacheWhenNotPresent() {
        boolean result = spriteBuilder.isInCache("nonexistent.png");
        assertThat(result).isFalse();
    }

    @Test
    public void testSetToCache() {
        BufferedImage mockImage = mock(BufferedImage.class);
        spriteBuilder.setToCache("test.png", mockImage);

        boolean result = spriteBuilder.isInCache("test.png");
        assertThat(result).isTrue();
    }

    @Test
    public void testLoadImageWhenInCache() {
        BufferedImage mockImage = mock(BufferedImage.class);
        spriteBuilder.setToCache("test.png", mockImage);

        BufferedImage result = spriteBuilder.loadImage("test.png");

        assertThat(result).isSameAs(mockImage);
    }

    @Test
    public void testLoadImageWhenNotInCache() {
        spriteBuilder = new SpriteBuilder();

        spriteBuilder.loadImage("pipe2.png");

        assertThat(spriteBuilder.isInCache("pipe2.png")).isTrue();
    }


    @Test
    public void testLoadImageWhenResourceNotFound() {
        BufferedImage result = spriteBuilder.loadImage("nonexistent.png");

        assertThat(result).isNull();
    }

    @Test
    public void testLoadImageLogsErrorOnException() {
        SpriteBuilder spriteBuilder = new SpriteBuilder();

        // Attempt to load an invalid resource
        BufferedImage result = spriteBuilder.loadImage("faulty.png");

        // Assert that the method handled the error gracefully and returned null
        assertThat(result).isNull();
    }

}
