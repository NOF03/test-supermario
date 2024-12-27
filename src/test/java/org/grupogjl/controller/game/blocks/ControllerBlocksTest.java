package org.grupogjl.controller.game.blocks;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.level.Level;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ControllerBlocksTest {

    private static final long MOCK_TIME = 123L;

    @Test
    void shouldInvokeStepOnSurpriseAndDestroyableBlocksControllers() {
        // Mock dependencies
        Level mockLevel = mock(Level.class);

        // Spy on ControllerBlocks to monitor internal behavior
        ControllerBlocks controllerBlocks = spy(new ControllerBlocks());

        // When
        controllerBlocks.step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Use verify to ensure that the step methods of the created controllers are called
        verify(controllerBlocks, times(1)).step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);
    }
}