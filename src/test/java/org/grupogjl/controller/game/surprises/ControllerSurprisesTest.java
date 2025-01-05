package org.grupogjl.controller.game.surprises;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.surprises.Surprise;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerSurprisesTest {

    @Spy
    @InjectMocks
    private ControllerSurprises controllerSurprises;

    @Mock
    private Level level;

    @Test
    public void testStep() {
        GeneralGui.ACTION action = GeneralGui.ACTION.LEFT;
        long time = 1000L;

        ControllerCoin controllerCoin = mock(ControllerCoin.class);
        doNothing().when(controllerSurprises).updateStatus(level, time);

        // Simulate the step of ControllerCoin
        lenient().doNothing().when(controllerCoin).step(level, action, time);

        controllerSurprises.step(level, action, time);

        verify(controllerSurprises, times(1)).updateStatus(level, time);
    }

    @Test
    public void testUpdateStatus_withActivatedSurprise() {
        Surprise activatedSurprise = mock(Surprise.class);
        List<Surprise> surprises = new ArrayList<>();
        surprises.add(activatedSurprise);

        when(level.getSurprises()).thenReturn(surprises);
        when(activatedSurprise.isActivated()).thenReturn(true);

        controllerSurprises.updateStatus(level, 1000L);

        verify(activatedSurprise).updateLocation();
        verify(level).setSurprises(surprises);
    }

    @Test
    public void testUpdateStatus_withNonActivatedSurprise() {
        Surprise nonActivatedSurprise = mock(Surprise.class);
        List<Surprise> surprises = new ArrayList<>();
        surprises.add(nonActivatedSurprise);

        when(level.getSurprises()).thenReturn(surprises);
        when(nonActivatedSurprise.isActivated()).thenReturn(false);

        controllerSurprises.updateStatus(level, 1000L);

        verify(nonActivatedSurprise, never()).updateLocation();
        verify(level).setSurprises(any());
    }
}
