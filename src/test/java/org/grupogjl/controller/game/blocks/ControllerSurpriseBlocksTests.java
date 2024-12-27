package org.grupogjl.controller.game.blocks;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.blocks.SurpriseBlock;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.surprises.Surprise;
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
class ControllerSurpriseBlocksTests {

    private static final long MOCK_TIME = 123L;

    @Mock
    private Level mockLevel;

    @Mock
    private SurpriseBlock mockSurpriseBlock;

    @Mock
    private Surprise mockSurprise;

    @InjectMocks
    private ControllerSurpriseBlocks controllerSurpriseBlocks;

    private List<SurpriseBlock> surpriseBlocks;
    private List<GameObject> objects;

    @BeforeEach
    void setUp() {
        // Initialize non-mock collections
        surpriseBlocks = spy(new ArrayList<>());
        objects = spy(new ArrayList<>());

        // Add mockSurpriseBlock to surpriseBlocks
        surpriseBlocks.add(mockSurpriseBlock);

        // Stub necessary Level methods
        when(mockLevel.getSurpriseBlocks()).thenReturn(surpriseBlocks);
        when(mockLevel.getObjects()).thenReturn(objects);

        // Stub SurpriseBlock behavior
        lenient().when(mockSurpriseBlock.getSurprise()).thenReturn(mockSurprise);
    }

    @Test
    void shouldAddSurpriseToObjectsWhenConditionsAreMet() {
        // Given
        when(mockSurpriseBlock.isUsed()).thenReturn(true);
        when(mockSurprise.isActivated()).thenReturn(true);

        // When
        controllerSurpriseBlocks.step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        assert objects.contains(mockSurprise);
        verify(mockLevel).setObjects(objects);
    }

    @Test
    void shouldNotAddSurpriseToObjectsWhenItIsNotUsed() {
        // Given
        when(mockSurpriseBlock.isUsed()).thenReturn(false);

        // When
        controllerSurpriseBlocks.step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        assert !objects.contains(mockSurprise);
        verify(mockLevel).setObjects(objects);
    }

    @Test
    void shouldNotAddSurpriseToObjectsWhenSurpriseIsNotActivated() {
        // Given
        when(mockSurpriseBlock.isUsed()).thenReturn(true);
        when(mockSurprise.isActivated()).thenReturn(false);

        // When
        controllerSurpriseBlocks.step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        assert !objects.contains(mockSurprise);
        verify(mockLevel).setObjects(objects);
    }

    @Test
    void shouldNotAddSurpriseToObjectsWhenItIsAlreadyInObjects() {
        // Given
        objects.add(mockSurprise);
        when(mockSurpriseBlock.isUsed()).thenReturn(true);
        lenient().when(mockSurprise.isActivated()).thenReturn(true);

        // When
        controllerSurpriseBlocks.step(mockLevel, GeneralGui.ACTION.NONE, MOCK_TIME);

        // Then
        assert objects.size() == 1; // No duplicates
        verify(mockLevel).setObjects(objects);
    }
}