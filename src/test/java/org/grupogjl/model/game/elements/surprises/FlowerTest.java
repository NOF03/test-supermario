package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FlowerTest {

    @Test
    public void testBorn() {
        Flower flower = new Flower(10, 20);
        flower.born();

        assertThat(flower.getY()).isEqualTo(19); // 20 - 1
        assertThat(flower.isFalling()).isTrue();
    }

    @Test
    public void testActivate() {
        Flower flower = new Flower(10, 20);
        Mario mario = mock(Mario.class);

        flower.activate(mario);

        verify(mario).setHeight(2);
        verify(mario).setStateFire(true);
        verify(mario).setStateBig(false);
    }

    @Test
    public void testHandleCollisionWithStaticObject() {
        Flower flower = new Flower(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);

        flower.handleCollision(mockStaticObject, 'A');

        assertThat(flower.getY()).isEqualTo(20);
        assertThat(flower.isFalling()).isFalse();
        assertThat(flower.isJumping()).isFalse();
    }

    @Test
    public void testHandleCollisionWithStaticObjectUp() {
        Flower flower = new Flower(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);

        flower.handleCollision(mockStaticObject, 'U');

        assertThat(flower.getY()).isEqualTo(25 + flower.getHeight());
        assertThat(flower.getVy()).isEqualTo(0);
    }

    @Test
    public void testHandleCollisionWithStaticObjectDown() {
        Flower flower = new Flower(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getHeight()).thenReturn(5f);

        flower.handleCollision(mockStaticObject, 'D');

        assertThat(flower.getY()).isEqualTo(25 - 5);
        assertThat(flower.isFalling()).isFalse();
        assertThat(flower.isJumping()).isFalse();
        assertThat(flower.getVy()).isEqualTo(0.0f);
    }

    @Test
    public void testHandleCollisionWithStaticObjectLeft() {
        Flower flower = new Flower(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getWidth()).thenReturn(3f);

        flower.handleCollision(mockStaticObject, 'L');

        assertThat(flower.getX()).isEqualTo(3);
        assertThat(flower.getVx()).isEqualTo(0.0f);
    }

    @Test
    public void testHandleCollisionWithStaticObjectRight() {
        Flower flower = new Flower(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getX()).thenReturn(15f);

        flower.handleCollision(mockStaticObject, 'R');

        assertThat(flower.getX()).isEqualTo(15 - flower.getWidth());
        assertThat(flower.getVx()).isEqualTo(0.0f);
    }

    @Test
    public void testHandleCollisionWithNonStaticObject() {
        Flower flower = new Flower(10, 20);
        PhysicalObject mockPhysicalObject = mock(PhysicalObject.class);
        when(mockPhysicalObject.getY()).thenReturn(25f);

        flower.handleCollision(mockPhysicalObject, 'D');

        assertThat(flower.getY()).isEqualTo(20);
        assertThat(flower.isFalling()).isFalse();
        assertThat(flower.isJumping()).isFalse();
    }

    @Test
    public void testGetImage() {
        Flower flower = new Flower(10, 20);
        assertThat(flower.getImage()).isEqualTo("flower.png");
    }

    @Test
    public void testGetVirtX() {
        Flower flower = new Flower(10, 20);
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5f);

        assertThat(flower.getVirtX(mockCamera)).isEqualTo(5f); // X - leftCamLimit
    }

    @Test
    public void testGetVirtY() {
        Flower flower = new Flower(10, 20);
        assertThat(flower.getVirtY()).isEqualTo(20);
    }
}
