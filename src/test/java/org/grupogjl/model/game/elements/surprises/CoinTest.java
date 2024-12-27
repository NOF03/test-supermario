package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CoinTest {

    @Test
    void testInitialization() {
        Coin coin = new Coin(10, 20);

        assertThat(coin.getX()).isEqualTo(10);
        assertThat(coin.getY()).isEqualTo(20);
        assertThat(coin.getActivationTimer()).isEqualTo(8);
    }

    @Test
    void testSetAndGetActivationTimer() {
        Coin coin = new Coin(10, 20);
        coin.setActivationTimer(5);
        assertThat(coin.getActivationTimer()).isEqualTo(5);
    }

    @Test
    void testBorn() {
        Coin coin = new Coin(10, 20);
        coin.born();
        assertThat(coin.getVy()).isEqualTo(0.7f);
        assertThat(coin.isJumping()).isTrue();
    }

    @Test
    void testActivate() {
        Coin coin = new Coin(10, 20);
        Mario mario = mock(Mario.class);
        when(mario.getCoins()).thenReturn(5);

        coin.activate(mario);

        verify(mario).setCoins(6); // Adds 1 coin
    }

    @Test
    void testHandleCollision() {
        Coin coin = new Coin(10, 20);
        coin.handleCollision(null, 'U');

        assertThat(coin.getVy()).isEqualTo(0);
        assertThat(coin.getVx()).isEqualTo(0);
        assertThat(coin.isFalling()).isFalse();
        assertThat(coin.isJumping()).isFalse();
    }

    @Test
    void testGetImage() {
        Coin coin = new Coin(10, 20);
        assertThat(coin.getImage()).isEqualTo("coin.png");
    }

    @Test
    void testGetVirtX() {
        Coin coin = new Coin(10, 20);
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5f);

        assertThat(coin.getVirtX(mockCamera)).isEqualTo(5f); // X - leftCamLimit
    }

    @Test
    void testGetVirtY() {
        Coin coin = new Coin(10, 20);
        assertThat(coin.getVirtY()).isEqualTo(20);
    }
}
