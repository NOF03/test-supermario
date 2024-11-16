package org.grupogjl.model.game.elements.generalobjects;

public abstract class StaticObject implements GameObject {
    private float x;
    private float y;
    private float width;
    private float height;
    public StaticObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    @Override
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    @Override
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
}
