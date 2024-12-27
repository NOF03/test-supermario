package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GoalBlockTest {

    @Test
    void testGoalBlockFunctionality() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        GoalBlock block = new GoalBlock(10, 20, 5, 5);

        // Test virtual coordinates
        assertThat(block.getVirtX(camera)).isEqualTo(5.0f);
        assertThat(block.getVirtY()).isEqualTo(16); // Y - (Height - 1)

        // Test image
        assertThat(block.getImage()).isEqualTo("goalBlock.png");
    }
}
