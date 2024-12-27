package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MushroomTest {

    private Mushroom mushroom;

    @BeforeEach
    void setUp() {
        mushroom = mock(Mushroom.class, CALLS_REAL_METHODS);
        mushroom.setX(10);
        mushroom.setY(20);
    }

    @Test
    void testBorn() {
        mushroom.born();
        assertThat(mushroom.getVx()).isEqualTo(0.2f);
        assertThat(mushroom.isFalling()).isTrue();
    }

    @Test
    void testHandleCollisionWithStaticObjectUp() {
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(30f);

        mushroom.handleCollision(mockStaticObject, 'U');

        assertThat(mushroom.getY()).isEqualTo(30 + mushroom.getHeight());
        assertThat(mushroom.getVy()).isEqualTo(0);
    }

    @Test
    void testHandleCollisionWithStaticObjectDown() {
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getY()).thenReturn(30f);
        when(mockStaticObject.getHeight()).thenReturn(5f);

        mushroom.handleCollision(mockStaticObject, 'D');

        assertThat(mushroom.getY()).isEqualTo(30 - 5);
        assertThat(mushroom.isFalling()).isFalse();
        assertThat(mushroom.isJumping()).isFalse();
        assertThat(mushroom.getVy()).isEqualTo(0);
    }

    @Test
    void testHandleCollisionWithStaticObjectLeft() {
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getX()).thenReturn(5f);
        when(mockStaticObject.getWidth()).thenReturn(3f);

        mushroom.handleCollision(mockStaticObject, 'L');

        assertThat(mushroom.getX()).isEqualTo(5 + 3);
        assertThat(mushroom.getVx()).isEqualTo(0.2f);
    }

    @Test
    void testHandleCollisionWithStaticObjectRight() {
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getX()).thenReturn(15f);

        mushroom.handleCollision(mockStaticObject, 'R');

        assertThat(mushroom.getX()).isEqualTo(15 - mushroom.getWidth());
        assertThat(mushroom.getVx()).isEqualTo(-0.2f);
    }

    @Test
    void testHandleCollisionWithStaticObjectOtherDirection() {
        StaticObject mockStaticObject = mock(StaticObject.class);
        when(mockStaticObject.getX()).thenReturn(15f);

        mushroom.handleCollision(mockStaticObject, 'A');

        assertThat(mushroom.getX()).isEqualTo(10);
        assertThat(mushroom.getVx()).isEqualTo(0.0f);
    }

    @Test
    void testHandleCollisionWithStaticObjectNotStatic() {
        PhysicalObject mockStaticObject = mock(PhysicalObject.class);
        when(mockStaticObject.getX()).thenReturn(15f);

        mushroom.handleCollision(mockStaticObject, 'R');

        assertThat(mushroom.getX()).isEqualTo(10);
        assertThat(mushroom.getVx()).isEqualTo(0.0f);
    }
}
