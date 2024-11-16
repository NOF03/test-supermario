package org.grupogjl.controller.game.surprises;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.surprises.Surprise;

import java.util.Iterator;
import java.util.List;

public class ControllerSurprises {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        updateStatus(level, time);
        ControllerCoin controllerCoin = new ControllerCoin();
        controllerCoin.step(level, action, time);

    }

    public void updateStatus(Level level, long time) {
        List<Surprise> surprises = level.getSurprises();
        Iterator<Surprise> iterator = surprises.listIterator();

        while (iterator.hasNext()) {

            Surprise surprise = iterator.next();

            if (!surprise.isActivated()) {
                iterator.remove();
            } else {
                surprise.updateLocation();
            }


        }
        level.setSurprises(surprises);
    }
}
