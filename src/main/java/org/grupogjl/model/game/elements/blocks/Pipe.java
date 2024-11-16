package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.camera.Camera;

public class Pipe extends BuildingBlock {
    public Pipe conection;
    public Pipe(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    public Pipe getConection() {
        return conection;
    }
    public void setConection(Pipe conection) {
        this.conection = conection;
    }
    @Override
    public float getVirtX(Camera camera) {
        return getX() - camera.getLeftCamLimit();
    }
    @Override
    public float getVirtY() {
        return getY() - (getHeight() - 1);
    }
    @Override
    public String getImage() {
        return "pipe" + (int) getHeight() + ".png";
    }
}
