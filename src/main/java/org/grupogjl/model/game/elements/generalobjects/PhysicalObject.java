package org.grupogjl.model.game.elements.generalobjects;

public abstract class PhysicalObject implements GameObject {
    private float x;
    private float y;
    private float vx, vy;
    private float g;
    private boolean falling;
    private boolean jumping;
    private float width;
    private float height;
    public PhysicalObject(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = 0;
        this.vy = 0;
        this.g = 0.23F;
        this.jumping = false;
        this.falling = false;
    }
    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }
    public float getWidth() {
        return width;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    @Override
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    @Override
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }
    public float getVx() {
        return vx;
    }
    public void setVx(float vx) {
        this.vx = vx;
    }
    public float getVy() {
        return vy;
    }
    public void setVy(float vy) {
        this.vy = vy;
    }
    public float getG() {
        return g;
    }
    public void setG(float g) {
        this.g = g;
    }
    public boolean isFalling() {
        return falling;
    }
    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public boolean isJumping() {
        return jumping;
    }
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
    public abstract void handleCollision(GameObject object, char r);
    public void updateLocation() {
        if (jumping) {
            float prevVy = vy; // Store the previous vy
            vy = vy - g; // Gravity is subtracted when jumping
            y = y - prevVy; // Use the previous vy to update y
        }
        if (falling) {
            float prevVy = vy; // Store the previous vy
            vy = vy + g; // Gravity is added when falling
            if (vy > 1.4) {
                vy = 1.4f;
            }
            y = y + prevVy; // Use the previous vy to update y
        }
        x = x + vx;
        // Reset vy to zero when not jumping and not falling
        if (!jumping && !falling) {
            vy = 0;
        }
        // Adjust jumping and falling flags based on y position
        if (jumping && vy <= 0) {
            jumping = false;
            setFalling(true);
        }
    }
    public boolean collidesWithStatic(StaticObject other) {
        float thisLeft = getX();
        float thisRight = getX() + getWidth();
        float thisBottom = getY();
        float thisTop = getY() - getHeight();
        float otherLeft = other.getX();
        float otherRight = other.getX() + other.getWidth();
        float otherBottom = other.getY();
        float otherTop = other.getY() - other.getHeight();
        // Check for overlap on the x-axis
        boolean xOverlap = thisLeft < otherRight && thisRight > otherLeft;
        // Check for vertical overlap
        boolean verticalOverlap = thisTop < otherBottom && thisBottom > otherTop;
        // If there is overlap on the x-axis and vertical overlap, a collision has occurred
        return xOverlap && verticalOverlap;
    }
    public boolean collidesWithPhysical(PhysicalObject other, float xme, float yme) {
        float thisLeft = getX() - xme;
        float thisRight = getX() + getWidth() + xme;
        float thisBottom = getY() + yme;
        float thisTop = getY() - getHeight() - yme;
        float otherLeft = other.getX();
        float otherRight = other.getX() + other.getWidth();
        float otherBottom = other.getY();
        float otherTop = other.getY() - other.getHeight();
        // Check for overlap on the x-axis
        boolean xOverlap = thisLeft < otherRight && thisRight > otherLeft;
        // Check for vertical overlap
        boolean verticalOverlap = thisTop < otherBottom && thisBottom > otherTop;
        // If there is overlap on the x-axis and vertical overlap, a collision has occurred
        return xOverlap && verticalOverlap;
    }
    public abstract void handleWallColision(float leftCamLimit);
    public boolean collidesWith(GameObject object) {
        if (object instanceof PhysicalObject) {
            return collidesWithPhysical((PhysicalObject) object, 0, 0);
        } else {
            return collidesWithStatic((StaticObject) object);
        }
    }
}
