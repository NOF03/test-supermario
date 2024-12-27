package org.grupogjl.controller.game.blocks;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.blocks.DestroyableBlock;
import org.grupogjl.model.game.elements.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerDestroyableBlocksTests {

    private static final long MOCK_TIME = 123L;

    @Mock
    private Level mockLevel;

    @Mock
    private DestroyableBlock mockBlock1;

    @Mock
    private DestroyableBlock mockBlock2;

    @InjectMocks
    private ControllerDestroyableBlocks controllerDestroyableBlocks;

    private List<DestroyableBlock> destroyableBlocks;

    @BeforeEach
    void setUp() {
        destroyableBlocks = new ArrayList<>();
        destroyableBlocks.add(mockBlock1);
        destroyableBlocks.add(mockBlock2);

        when(mockLevel.getDestroyableBlocks()).thenReturn(destroyableBlocks);
    }

    @Test
    void shouldRemoveBlocksWithZeroOrNegativeStrength() {
        // Given
        when(mockBlock1.getStrenght()).thenReturn(0);
        when(mockBlock2.getStrenght()).thenReturn(-1);

        // When
        controllerDestroyableBlocks.updateStatus(mockLevel, MOCK_TIME);

        // Then
        verify(mockLevel).setDestroyableBlocks(destroyableBlocks);
        verify(mockBlock1).getStrenght();
        verify(mockBlock2).getStrenght();
        assert destroyableBlocks.isEmpty();
    }

    @Test
    void shouldNotRemoveBlocksWithPositiveStrength() {
        // Given
        when(mockBlock1.getStrenght()).thenReturn(5);
        when(mockBlock2.getStrenght()).thenReturn(3);

        // When
        controllerDestroyableBlocks.updateStatus(mockLevel, MOCK_TIME);

        // Then
        verify(mockLevel).setDestroyableBlocks(destroyableBlocks);
        verify(mockBlock1).getStrenght();
        verify(mockBlock2).getStrenght();
        assert destroyableBlocks.size() == 2;
    }

    @Test
    void stepShouldInvokeUpdateStatus() {
        // When
        controllerDestroyableBlocks.step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        verify(mockLevel).getDestroyableBlocks();
        verify(mockLevel).setDestroyableBlocks(destroyableBlocks);
    }
}