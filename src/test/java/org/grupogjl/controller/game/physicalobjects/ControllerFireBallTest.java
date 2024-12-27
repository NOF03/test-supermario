package org.grupogjl.controller.game.physicalobjects;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.props.FireBall;
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
class ControllerFireBallTest {

    @Spy
    @InjectMocks
    private ControllerFireBall controllerFireBall;

    @Mock
    private Level level;

    @Test
    void testStep() {
        controllerFireBall.step(level, GeneralGui.ACTION.THROWBALL, 1000L);

        verify(controllerFireBall, times(1)).updateStatus(level, 1000L);
    }

    @Test
    void testUpdateStatus_removesInactiveFireBalls() {
        FireBall activeFireBall = mock(FireBall.class);
        FireBall inactiveFireBall = mock(FireBall.class);

        List<FireBall> fireBalls = new ArrayList<>();
        fireBalls.add(activeFireBall);
        fireBalls.add(inactiveFireBall);

        when(level.getFireBalls()).thenReturn(fireBalls);
        when(activeFireBall.isActive()).thenReturn(true);
        when(inactiveFireBall.isActive()).thenReturn(false);

        controllerFireBall.updateStatus(level, 1000L);

        verify(activeFireBall).updateLocation();
        verify(inactiveFireBall, never()).updateLocation();
        verify(level).setFireBalls(any());
    }
}
