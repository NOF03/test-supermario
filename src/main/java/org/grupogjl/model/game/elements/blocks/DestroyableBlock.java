package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;

public class DestroyableBlock extends InteractableBlock {
    private int strenght;
    public DestroyableBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
        strenght = 1;
    }
    public int getStrenght() {
        return strenght;
    }
    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }
    @Override
    public float getVirtX(Camera camera) {
        return getX() - camera.getLeftCamLimit();
    }
    @Override
    public float getVirtY() {
        return getY();
    }
    @Override
    public String getImage() {
        return "breakableBlock.png";
    }
    @Override
    public void gotHit(Mario mario) {
        if (mario.isStateBig() || mario.isStateFire()) {
            setStrenght(0);
        }
    }
}
