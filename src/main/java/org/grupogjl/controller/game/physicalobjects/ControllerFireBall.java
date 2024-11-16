package org.grupogjl.controller.game.physicalobjects;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.props.FireBall;

import java.util.Iterator;
import java.util.List;

public class ControllerFireBall {
    public void step(Level level, GeneralGui.ACTION action, long time) {
        updateStatus(level, time);

    }
    public void updateStatus(Level level, long time) {
        List<FireBall> fireBalls = level.getFireBalls();
        Iterator<FireBall> iterator = fireBalls.listIterator();
        while (iterator.hasNext()) {
            FireBall fireBall = iterator.next();
            if (fireBall.isActive()) {
                fireBall.updateLocation();
            } else {
                iterator.remove();
            }
        }
        level.setFireBalls(fireBalls);
    }
}
