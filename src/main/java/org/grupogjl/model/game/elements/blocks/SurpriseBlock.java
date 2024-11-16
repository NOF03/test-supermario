package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.surprises.Surprise;

public class SurpriseBlock extends InteractableBlock {
    private boolean used;
    private Surprise surprise;
    private Boolean isOpen;
    public SurpriseBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.isOpen = false;
        this.used = false;

    }
    public boolean isUsed() {
        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }
    public Surprise getSurprise() {
        return surprise;
    }
    public void setSurprise(Surprise surprise) {
        this.surprise = surprise;
    }
    public Boolean getOpen() {
        return isOpen;
    }
    public void setOpen(Boolean open) {
        isOpen = open;
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
        if (isUsed()) {
            return "emptySurpriseBlock.png";
        } else {
            return "surpriseBlock.png";
        }
    }
    @Override
    public void gotHit(Mario mario) {
        if (!isUsed()) {
            freeSurprise();
            used = true;
        }
    }
    public void freeSurprise() {
        float currenty = surprise.getY();
        surprise.setY(currenty - 1);
        surprise.setActivated(true);
        surprise.born();
    }
}
