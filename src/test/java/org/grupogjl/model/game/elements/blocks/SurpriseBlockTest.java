package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.surprises.Surprise;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SurpriseBlockTest {

    @Test
    void testInitialState() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);

        // Verify initial state
        assertThat(block.isUsed()).isFalse();
        assertThat(block.getOpen()).isFalse();
        assertThat(block.getSurprise()).isNull();
    }

    @Test
    void testSettersAndGetters() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);

        // Test 'used' property
        block.setUsed(true);
        assertThat(block.isUsed()).isTrue();

        // Test 'isOpen' property
        block.setOpen(true);
        assertThat(block.getOpen()).isTrue();

        // Test setting and getting a Surprise
        Surprise surprise = mock(Surprise.class);
        block.setSurprise(surprise);
        assertThat(block.getSurprise()).isEqualTo(surprise);
    }

    @Test
    void testGetImageWhenUnused() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);
        block.setUsed(false);

        // Verify image when block is unused
        assertThat(block.getImage()).isEqualTo("surpriseBlock.png");
    }

    @Test
    void testGetImageWhenUsed() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);
        block.setUsed(true);

        // Verify image when block is used
        assertThat(block.getImage()).isEqualTo("emptySurpriseBlock.png");
    }

    @Test
    void testGotHitWithUnusedBlock() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);
        block.setUsed(false);

        // Mock surprise and Mario
        Surprise surprise = mock(Surprise.class);
        Mario mario = mock(Mario.class);
        block.setSurprise(surprise);

        // Hit the block
        block.gotHit(mario);

        // Verify the block state and interactions
        assertThat(block.isUsed()).isTrue(); // Block is now used
        verify(surprise).setY(anyFloat());
        verify(surprise).setActivated(true);
        verify(surprise).born();
    }

    @Test
    void testGotHitWithAlreadyUsedBlock() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);
        block.setUsed(true);

        // Mock surprise and Mario
        Surprise surprise = mock(Surprise.class);
        Mario mario = mock(Mario.class);
        block.setSurprise(surprise);

        // Hit the block
        block.gotHit(mario);

        // Verify that the surprise was not reactivated
        verify(surprise, never()).setY(anyFloat());
        verify(surprise, never()).setActivated(true);
        verify(surprise, never()).born();
    }

    @Test
    void testFreeSurprise() {
        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);

        // Mock surprise
        Surprise surprise = mock(Surprise.class);
        block.setSurprise(surprise);

        // Set up surprise mock
        when(surprise.getY()).thenReturn(10.0f);

        // Free the surprise
        block.freeSurprise();

        // Verify the surprise interactions
        verify(surprise).setY(9.0f); // Current Y - 1
        verify(surprise).setActivated(true);
        verify(surprise).born();
    }

    @Test
    void testVirtualCoordinates() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        SurpriseBlock block = new SurpriseBlock(10, 20, 5, 5);

        // Verify virtual coordinates
        assertThat(block.getVirtX(camera)).isEqualTo(5.0f); // X - camera's left limit
        assertThat(block.getVirtY()).isEqualTo(20); // Y remains the same
    }
}
