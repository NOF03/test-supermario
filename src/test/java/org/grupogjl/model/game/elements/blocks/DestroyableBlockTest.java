package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DestroyableBlockTest {

    @Test
    public void testDestroyableBlockFunctionality() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        DestroyableBlock block = new DestroyableBlock(10, 20, 5, 5);

        // Test initial strength
        assertThat(block.getStrenght()).isEqualTo(1);

        // Test setting strength
        block.setStrenght(0);
        assertThat(block.getStrenght()).isEqualTo(0);

        // Test virtual coordinates
        assertThat(block.getVirtX(camera)).isEqualTo(5.0f);
        assertThat(block.getVirtY()).isEqualTo(20);

        // Test image
        assertThat(block.getImage()).isEqualTo("breakableBlock.png");

        // Test hitting the block with Mario
        Mario mario = mock(Mario.class);
        when(mario.isStateBig()).thenReturn(true);
        when(mario.isStateFire()).thenReturn(false);

        block.gotHit(mario);
        assertThat(block.getStrenght()).isEqualTo(0); // Strength reduced to 0
    }

    @Test
    public void testGotHitWithMarioStateFireNoStateBig() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        DestroyableBlock block = new DestroyableBlock(10, 20, 5, 5);

        // Test hitting the block with Mario
        Mario mario = mock(Mario.class);
        when(mario.isStateBig()).thenReturn(false);
        when(mario.isStateFire()).thenReturn(true);

        block.gotHit(mario);
        assertThat(block.getStrenght()).isEqualTo(0); // Strength reduced to 0
    }

    @Test
    public void testGotHitWithMarioStateFireStateBig() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        DestroyableBlock block = new DestroyableBlock(10, 20, 5, 5);

        // Test hitting the block with Mario
        Mario mario = mock(Mario.class);
        when(mario.isStateBig()).thenReturn(false);
        when(mario.isStateFire()).thenReturn(true);

        block.gotHit(mario);
        assertThat(block.getStrenght()).isEqualTo(0); // Strength reduced to 0
    }

    @Test
    public void testGotHitWithMarioNoStateFireNoStateBig() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        DestroyableBlock block = new DestroyableBlock(10, 20, 5, 5);
        block.setStrenght(2);
        // Test hitting the block with Mario
        Mario mario = mock(Mario.class);
        when(mario.isStateBig()).thenReturn(false);
        when(mario.isStateFire()).thenReturn(false);

        block.gotHit(mario);
        assertThat(block.getStrenght()).isEqualTo(2); // Strength reduced to 0
    }
}
