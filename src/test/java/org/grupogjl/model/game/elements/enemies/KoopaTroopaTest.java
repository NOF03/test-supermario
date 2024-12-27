package org.grupogjl.model.game.elements.enemies;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class KoopaTroopaTest {

    @Test
    void testKoopaTroopaInitialization() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);

        // Verify initial properties
        assertThat(koopa.getX()).isEqualTo(10);
        assertThat(koopa.getY()).isEqualTo(20);
        assertThat(koopa.getWidth()).isEqualTo(5);
        assertThat(koopa.getHeight()).isEqualTo(5);
        assertThat(koopa.getLives()).isEqualTo(2); // Overridden lives
    }

    @Test
    void testMoveLeftWithTwoLives() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);
        koopa.moveLeft();

        // Verify velocity when lives = 2
        assertThat(koopa.getVx()).isEqualTo(-0.2f);
    }

    @Test
    void testMoveLeftWithOneLife() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);
        koopa.setLives(1);
        koopa.moveLeft();

        // Verify velocity when lives = 1
        assertThat(koopa.getVx()).isEqualTo(-2.0f);
    }

    @Test
    void testMoveRightWithTwoLives() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);
        koopa.moveRight();

        // Verify velocity when lives = 2
        assertThat(koopa.getVx()).isEqualTo(0.2f);
    }

    @Test
    void testMoveRightWithOneLife() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);
        koopa.setLives(1);
        koopa.moveRight();

        // Verify velocity when lives = 1
        assertThat(koopa.getVx()).isEqualTo(2.0f);
    }

    @Test
    void testGetVirtX() {
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5.0f);

        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);

        // Verify virtual X coordinate
        assertThat(koopa.getVirtX(mockCamera)).isEqualTo(5.0f); // X - camera's left limit
    }

    @Test
    void testGetVirtYWithTwoLives() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);

        // Verify virtual Y coordinate when lives = 2
        assertThat(koopa.getVirtY()).isEqualTo(19.6875f); // Y - 0.3125
    }

    @Test
    void testGetVirtYWithOneLife() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);
        koopa.setLives(1);

        // Verify virtual Y coordinate when lives = 1
        assertThat(koopa.getVirtY()).isEqualTo(20);
    }

    @Test
    void testGetImageWithTwoLives() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);

        // Verify image when lives = 2
        assertThat(koopa.getImage()).isEqualTo("koopaTroopa.png");
    }

    @Test
    void testGetImageWithOneLife() {
        KoopaTroopa koopa = new KoopaTroopa(10, 20, 5, 5);
        koopa.setLives(1);

        // Verify image when lives = 1
        assertThat(koopa.getImage()).isEqualTo("koopaShell.png");
    }
}
