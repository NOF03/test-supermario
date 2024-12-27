package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UnbreakableBlockTest {

    @Test
    void testUnbreakableBlockFunctionality() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        UnbreakableBlock block = new UnbreakableBlock(10, 20, 5, 5);

        // Test virtual coordinates
        assertThat(block.getVirtX(camera)).isEqualTo(5.0f);
        assertThat(block.getVirtY()).isEqualTo(20); // No offset in Y

        // Test image
        assertThat(block.getImage()).isEqualTo("unbreakableBlock.png");
    }
}
