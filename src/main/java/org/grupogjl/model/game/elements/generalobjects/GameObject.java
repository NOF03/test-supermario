package org.grupogjl.model.game.elements.generalobjects;

import org.grupogjl.model.game.elements.camera.Camera;

public interface GameObject {
    float getX();
    float getY();
    float getVirtX(Camera camera);
    float getVirtY();
    String getImage();
}