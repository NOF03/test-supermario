package org.grupogjl.controller.game.surprises;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.surprises.Coin;

import java.util.Iterator;
import java.util.List;

public class ControllerCoin {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        updateStatus(level, time);
    }

    public void updateStatus(Level level, long time) {
        Mario mario = level.getMario();
        List<Coin> coins = level.getCoins();
        Iterator<Coin> iterator = coins.iterator();
        while (iterator.hasNext()) {
            Coin coin = iterator.next();
            if (coin.getActivationTimer() <= 0) {
                coin.setActivated(false);
                mario.setCoins(mario.getCoins() + 1);
            } else {
                int curtime = coin.getActivationTimer();
                coin.setActivationTimer(curtime - 1);
            }
        }
    }
}
