package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StarTest {

    @Test
    void testBorn() {
        Star star = new Star(10, 20);
        star.born();

        assertThat(star.getVx()).isEqualTo(0.2f);
        assertThat(star.isFalling()).isTrue();
    }

    @Test
    void testActivate() {
        Star star = new Star(10, 20);
        Mario mario = mock(Mario.class);

        star.activate(mario);

        verify(mario).setInvencibleTime(600);
        verify(mario).setStateInvencible(true);
    }

    @Test
    void testHandleCollisionWithStaticObject() {
        Star star = new Star(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);

        star.handleCollision(mockStaticObject, 'A');

        assertThat(star.getY()).isEqualTo(20);
        assertThat(star.isFalling()).isFalse();
        assertThat(star.isJumping()).isFalse();
    }

    @Test
    void testHandleCollisionWithStaticObjectUp() {
        Star star = new Star(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);

        star.handleCollision(mockStaticObject, 'U');

        assertThat(star.getY()).isEqualTo(25 + star.getHeight());
        assertThat(star.getVy()).isEqualTo(0);
    }

    @Test
    void testHandleCollisionWithStaticObjectDown() {
        Star star = new Star(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getHeight()).thenReturn(5f);

        star.handleCollision(mockStaticObject, 'D');

        assertThat(star.getY()).isEqualTo(25 - 5);
        assertThat(star.isFalling()).isFalse();
        assertThat(star.isJumping()).isTrue();
        assertThat(star.getVy()).isEqualTo(-0.1f);
    }

    @Test
    void testHandleCollisionWithStaticObjectLeft() {
        Star star = new Star(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getWidth()).thenReturn(3f);

        star.handleCollision(mockStaticObject, 'L');

        assertThat(star.getX()).isEqualTo(3);
        assertThat(star.getVx()).isEqualTo(0.2f);
    }

    @Test
    void testHandleCollisionWithStaticObjectRight() {
        Star star = new Star(10, 20);
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(25f);
        when(mockStaticObject.getX()).thenReturn(15f);

        star.handleCollision(mockStaticObject, 'R');

        assertThat(star.getX()).isEqualTo(15 - star.getWidth());
        assertThat(star.getVx()).isEqualTo(-0.2f);
    }

    @Test
    void testHandleCollisionWithNonStaticObject() {
        Star star = new Star(10, 20);
        PhysicalObject mockPhysicalObject = mock(PhysicalObject.class);
        when(mockPhysicalObject.getY()).thenReturn(25f);

        star.handleCollision(mockPhysicalObject, 'D');

        assertThat(star.getY()).isEqualTo(20);
        assertThat(star.isFalling()).isFalse();
        assertThat(star.isJumping()).isFalse();
    }

    @Test
    void testGetImage() {
        Star star = new Star(10, 20);
        assertThat(star.getImage()).isEqualTo("star.png");
    }

    @Test
    void testGetVirtX() {
        Star star = new Star(10, 20);
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5f);

        assertThat(star.getVirtX(mockCamera)).isEqualTo(5f); // X - leftCamLimit
    }

    @Test
    void testGetVirtY() {
        Star star = new Star(10, 20);
        assertThat(star.getVirtY()).isEqualTo(20);
    }
}
