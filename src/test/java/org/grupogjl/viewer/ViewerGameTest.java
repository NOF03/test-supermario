package org.grupogjl.viewer;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.state.StateGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

class ViewerGameTest {

    @Test
    public void testDrawGameOver() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        StateGame mockState = mock(StateGame.class);
        ViewerGame viewerGame = new ViewerGame();

        when(mockState.isGameOver()).thenReturn(true);
        when(mockState.getModel()).thenReturn(mock(Level.class)); // Mocked level

        viewerGame.draw(mockState, mockGui);

        verify(mockGui).clear();
        verify(mockGui).drawGameOver();
        verifyNoMoreInteractions(mockGui);
    }

    @Test
    public void testDrawGameObjects() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        StateGame mockState = mock(StateGame.class);
        ViewerGame viewerGame = new ViewerGame();

        Level mockLevel = mock(Level.class);
        Camera mockCamera = mock(Camera.class);
        Mario mockMario = mock(Mario.class);
        GameObject mockObject1 = mock(GameObject.class);
        GameObject mockObject2 = mock(GameObject.class);

        when(mockState.isGameOver()).thenReturn(false);
        when(mockState.getModel()).thenReturn(mockLevel);
        when(mockLevel.getCamera()).thenReturn(mockCamera);
        when(mockLevel.getMario()).thenReturn(mockMario);
        when(mockLevel.getObjects()).thenReturn(Arrays.asList(mockObject1, mockObject2));

        when(mockCamera.getLeftCamLimit()).thenReturn(0f);
        when(mockCamera.getRightCamLimit()).thenReturn(10f);

        when(mockObject1.getX()).thenReturn(5f);
        when(mockObject1.getVirtX(mockCamera)).thenReturn(5f);
        when(mockObject1.getVirtY()).thenReturn(5f);
        when(mockObject1.getImage()).thenReturn("object1.png");

        when(mockObject2.getX()).thenReturn(15f); // Outside camera bounds

        when(mockMario.getVirtX(mockCamera)).thenReturn(3f);
        when(mockMario.getVirtY()).thenReturn(4f);
        when(mockMario.getImage()).thenReturn("mario.png");
        when(mockMario.getCoins()).thenReturn(10);
        when(mockMario.getLives()).thenReturn(3);

        viewerGame.draw(mockState, mockGui);

        verify(mockGui).clear();
        verify(mockGui).drawImage(5f, 5f, "object1.png"); // Within bounds
        verify(mockGui).drawImage(3f, 4f, "mario.png"); // Mario
        verify(mockGui).drawMenuText(1, 8, "coins: 10");
        verify(mockGui).drawMenuText(1, 24, "lives: 3");
        verifyNoMoreInteractions(mockGui);
    }

    @Test
    public void testObjectWithinBounds() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        StateGame mockState = mock(StateGame.class);
        ViewerGame viewerGame = new ViewerGame();

        Level mockLevel = mock(Level.class);
        Camera mockCamera = mock(Camera.class);
        Mario mockMario = mock(Mario.class);
        GameObject mockObject = mock(GameObject.class);

        when(mockState.isGameOver()).thenReturn(false);
        when(mockState.getModel()).thenReturn(mockLevel);
        when(mockLevel.getCamera()).thenReturn(mockCamera);
        when(mockLevel.getMario()).thenReturn(mockMario);
        when(mockLevel.getObjects()).thenReturn(Collections.singletonList(mockObject));

        when(mockCamera.getLeftCamLimit()).thenReturn(5f);
        when(mockCamera.getRightCamLimit()).thenReturn(15f);
        when(mockObject.getX()).thenReturn(10f);
        when(mockObject.getVirtX(mockCamera)).thenReturn(10f);
        when(mockObject.getVirtY()).thenReturn(5f);
        when(mockObject.getImage()).thenReturn("object.png");

        when(mockMario.getVirtX(mockCamera)).thenReturn(12f);
        when(mockMario.getVirtY()).thenReturn(6f);
        when(mockMario.getImage()).thenReturn("mario.png");

        viewerGame.draw(mockState, mockGui);

        verify(mockGui).clear();
        verify(mockGui).drawImage(10f, 5f, "object.png");
        verify(mockGui).drawImage(12f, 6f, "mario.png");
    }

    @Test
    public void testObjectGreaterThanRightLimit() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        StateGame mockState = mock(StateGame.class);
        ViewerGame viewerGame = new ViewerGame();

        Level mockLevel = mock(Level.class);
        Camera mockCamera = mock(Camera.class);
        Mario mockMario = mock(Mario.class);
        GameObject mockObject = mock(GameObject.class);

        when(mockState.isGameOver()).thenReturn(false);
        when(mockState.getModel()).thenReturn(mockLevel);
        when(mockLevel.getCamera()).thenReturn(mockCamera);
        when(mockLevel.getMario()).thenReturn(mockMario);
        when(mockLevel.getObjects()).thenReturn(Collections.singletonList(mockObject));

        when(mockCamera.getLeftCamLimit()).thenReturn(5f);
        when(mockCamera.getRightCamLimit()).thenReturn(15f);
        when(mockObject.getX()).thenReturn(20f);
        when(mockObject.getVirtX(mockCamera)).thenReturn(10f);
        when(mockObject.getVirtY()).thenReturn(5f);
        when(mockObject.getImage()).thenReturn("object.png");

        when(mockMario.getVirtX(mockCamera)).thenReturn(12f);
        when(mockMario.getVirtY()).thenReturn(6f);
        when(mockMario.getImage()).thenReturn("mario.png");

        viewerGame.draw(mockState, mockGui);

        verify(mockGui).clear();
        verify(mockGui).drawImage(12f, 6f, "mario.png"); // Mario is always drawn
        verify(mockGui, never()).drawImage(anyFloat(), anyFloat(), eq("object.png"));
    }

    @Test
    public void testObjectLessThanLeftLimitMinusOne() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        StateGame mockState = mock(StateGame.class);
        ViewerGame viewerGame = new ViewerGame();

        Level mockLevel = mock(Level.class);
        Camera mockCamera = mock(Camera.class);
        Mario mockMario = mock(Mario.class);
        GameObject mockObject = mock(GameObject.class);

        when(mockState.isGameOver()).thenReturn(false);
        when(mockState.getModel()).thenReturn(mockLevel);
        when(mockLevel.getCamera()).thenReturn(mockCamera);
        when(mockLevel.getMario()).thenReturn(mockMario);
        when(mockLevel.getObjects()).thenReturn(Collections.singletonList(mockObject));

        when(mockCamera.getLeftCamLimit()).thenReturn(5f);
        when(mockCamera.getRightCamLimit()).thenReturn(15f);
        when(mockObject.getX()).thenReturn(1f);
        when(mockObject.getVirtX(mockCamera)).thenReturn(10f);
        when(mockObject.getVirtY()).thenReturn(5f);
        when(mockObject.getImage()).thenReturn("object.png");

        when(mockMario.getVirtX(mockCamera)).thenReturn(12f);
        when(mockMario.getVirtY()).thenReturn(6f);
        when(mockMario.getImage()).thenReturn("mario.png");

        viewerGame.draw(mockState, mockGui);

        verify(mockGui).clear();
        verify(mockGui).drawImage(12f, 6f, "mario.png"); // Mario is always drawn
        verify(mockGui, never()).drawImage(anyFloat(), anyFloat(), eq("object.png"));
    }

    @Test
    public void testObjectOutsideBoundsBothConditionsTrue() throws IOException {
        GeneralGui mockGui = mock(GeneralGui.class);
        StateGame mockState = mock(StateGame.class);
        ViewerGame viewerGame = new ViewerGame();

        Level mockLevel = mock(Level.class);
        Camera mockCamera = mock(Camera.class);
        Mario mockMario = mock(Mario.class);
        GameObject mockObject = mock(GameObject.class);

        when(mockState.isGameOver()).thenReturn(false);
        when(mockState.getModel()).thenReturn(mockLevel);
        when(mockLevel.getCamera()).thenReturn(mockCamera);
        when(mockLevel.getMario()).thenReturn(mockMario);
        when(mockLevel.getObjects()).thenReturn(Collections.singletonList(mockObject));

        when(mockCamera.getLeftCamLimit()).thenReturn(15f);
        when(mockCamera.getRightCamLimit()).thenReturn(5f);
        when(mockObject.getX()).thenReturn(10f);
        when(mockObject.getVirtX(mockCamera)).thenReturn(10f);
        when(mockObject.getVirtY()).thenReturn(5f);
        when(mockObject.getImage()).thenReturn("object.png");

        when(mockMario.getVirtX(mockCamera)).thenReturn(12f);
        when(mockMario.getVirtY()).thenReturn(6f);
        when(mockMario.getImage()).thenReturn("mario.png");

        viewerGame.draw(mockState, mockGui);

        verify(mockGui).clear();
        verify(mockGui).drawImage(12f, 6f, "mario.png"); // Mario is always drawn
        verify(mockGui, never()).drawImage(anyFloat(), anyFloat(), eq("object.png"));
    }
}
