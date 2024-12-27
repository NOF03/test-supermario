package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class SurpriseTest {

    private Surprise surprise;

    @BeforeEach
    void setUp() {
        surprise = mock(Surprise.class, CALLS_REAL_METHODS);
    }

    @Test
    void testInitialization() {
        when(surprise.getX()).thenReturn(10f);
        when(surprise.getY()).thenReturn(20f);

        assertThat(surprise.getX()).isEqualTo(10f);
        assertThat(surprise.getY()).isEqualTo(20f);
        assertThat(surprise.isActivated()).isFalse();
    }

    @Test
    void testSetAndGetActivated() {
        surprise.setActivated(true);
        assertThat(surprise.isActivated()).isTrue();

        surprise.setActivated(false);
        assertThat(surprise.isActivated()).isFalse();
    }

    @Test
    void testHandleWallCollision() {
        surprise.setX(15);
        surprise.setVx(5);

        surprise.handleWallColision(10);

        assertThat(surprise.getX()).isEqualTo(10);
        assertThat(surprise.getVx()).isEqualTo(-5);
    }

    @Test
    void testActivate() {
        Mario mockMario = mock(Mario.class);
        doNothing().when(surprise).activate(mockMario);

        surprise.activate(mockMario);

        verify(surprise).activate(mockMario);
    }

    @Test
    void testBorn() {
        doNothing().when(surprise).born();

        surprise.born();

        verify(surprise).born();
    }

    @Test
    void testHandleCollision() {
        GameObject mockGameObject = mock(GameObject.class);

        doNothing().when(surprise).handleCollision(eq(mockGameObject), anyChar());

        surprise.handleCollision(mockGameObject, 'R');

        verify(surprise).handleCollision(mockGameObject, 'R');
    }
}
