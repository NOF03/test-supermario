package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class Mushroom1UPTest {

    @Test
    void testActivate() {
        Mushroom1UP mushroom1UP = new Mushroom1UP(10, 20);
        Mario mockMario = mock(Mario.class);
        when(mockMario.getLives()).thenReturn(3);

        mushroom1UP.activate(mockMario);

        verify(mockMario).setLives(4); // Adds 1 life
    }

    @Test
    void testGetVirtX() {
        Mushroom1UP mushroom1UP = new Mushroom1UP(10, 20);
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5f);

        assertThat(mushroom1UP.getVirtX(mockCamera)).isEqualTo(5f); // X - leftCamLimit
    }

    @Test
    void testGetVirtY() {
        Mushroom1UP mushroom1UP = new Mushroom1UP(10, 20);
        assertThat(mushroom1UP.getVirtY()).isEqualTo(20);
    }

    @Test
    void testGetImage() {
        Mushroom1UP mushroom1UP = new Mushroom1UP(10, 20);
        assertThat(mushroom1UP.getImage()).isEqualTo("lifeMushroom.png");
    }
}
