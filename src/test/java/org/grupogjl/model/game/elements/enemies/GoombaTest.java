package org.grupogjl.model.game.elements.enemies;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoombaTest {

    @Test
    public void testGoombaInitialization() {
        Goomba goomba = new Goomba(10, 20, 5, 5);

        // Verify initial properties
        assertThat(goomba.getX()).isEqualTo(10);
        assertThat(goomba.getY()).isEqualTo(20);
        assertThat(goomba.getWidth()).isEqualTo(5);
        assertThat(goomba.getHeight()).isEqualTo(5);
        assertThat(goomba.getLives()).isEqualTo(1); // Default lives from `Enemy`
    }

    @Test
    public void testGetVirtX() {
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5.0f);

        Goomba goomba = new Goomba(10, 20, 5, 5);

        // Verify virtual X coordinate
        assertThat(goomba.getVirtX(mockCamera)).isEqualTo(5.0f); // X - camera's left limit
    }

    @Test
    public void testGetVirtY() {
        Goomba goomba = new Goomba(10, 20, 5, 5);

        // Verify virtual Y coordinate
        assertThat(goomba.getVirtY()).isEqualTo(20);
    }

    @Test
    public void testGetImage() {
        Goomba goomba = new Goomba(10, 20, 5, 5);

        // Verify image
        assertThat(goomba.getImage()).isEqualTo("goomba.png");
    }
}
