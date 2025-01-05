package org.grupogjl.controller.game.surprises;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.surprises.Coin;
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
class ControllerCoinTest {

    @Spy
    @InjectMocks
    private ControllerCoin controllerCoin;

    @Mock
    private Level level;

    @Mock
    private Mario mario;

    @Test
    public void testStep() {
        long time = 1000L;
        GeneralGui.ACTION action = GeneralGui.ACTION.RIGHT;

        // Spy the actual method
        doNothing().when(controllerCoin).updateStatus(level, time);

        controllerCoin.step(level, action, time);

        verify(controllerCoin, times(1)).updateStatus(level, time);
    }

    @Test
    public void testUpdateStatus_whenCoinTimerIsZeroOrNegative() {
        Coin coin1 = mock(Coin.class);
        Coin coin2 = mock(Coin.class);
        List<Coin> coins = new ArrayList<>();
        coins.add(coin1);
        coins.add(coin2);

        when(level.getMario()).thenReturn(mario);
        when(level.getCoins()).thenReturn(coins);
        when(coin1.getActivationTimer()).thenReturn(0);
        when(coin2.getActivationTimer()).thenReturn(-5);

        controllerCoin.updateStatus(level, 1000L);

        verify(coin1).setActivated(false);
        verify(coin2).setActivated(false);
        verify(mario, times(2)).setCoins(anyInt());
    }

    @Test
    public void testUpdateStatus_whenCoinTimerIsPositive() {
        Coin coin1 = mock(Coin.class);
        Coin coin2 = mock(Coin.class);
        List<Coin> coins = new ArrayList<>();
        coins.add(coin1);
        coins.add(coin2);

        when(level.getMario()).thenReturn(mario);
        when(level.getCoins()).thenReturn(coins);
        when(coin1.getActivationTimer()).thenReturn(3);
        when(coin2.getActivationTimer()).thenReturn(7);

        controllerCoin.updateStatus(level, 1000L);

        verify(coin1).setActivationTimer(2);
        verify(coin2).setActivationTimer(6);
        verifyNoInteractions(mario);
    }
}
