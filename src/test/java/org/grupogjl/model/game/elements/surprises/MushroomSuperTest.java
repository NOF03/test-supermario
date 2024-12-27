package org.grupogjl.model.game.elements.surprises;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MushroomSuperTest {

    @Test
    void testActivateWhenMarioIsNotBigOrFire() {
        MushroomSuper mushroomSuper = new MushroomSuper(10, 20);
        Mario mockMario = mock(Mario.class);
        when(mockMario.isStateBig()).thenReturn(false);
        when(mockMario.isStateFire()).thenReturn(false);

        mushroomSuper.activate(mockMario);

        verify(mockMario).setStateBig(true);
        verify(mockMario).setHeight(2);
    }

    @Test
    void testActivateWhenMarioIsBigOrFire() {
        MushroomSuper mushroomSuper = new MushroomSuper(10, 20);
        Mario mockMario = mock(Mario.class);
        when(mockMario.isStateBig()).thenReturn(true);
        when(mockMario.isStateFire()).thenReturn(true);

        mushroomSuper.activate(mockMario);

        verify(mockMario, never()).setStateBig(anyBoolean());
        verify(mockMario, never()).setHeight(anyInt());
    }

    @Test
    void testGetVirtX() {
        MushroomSuper mushroomSuper = new MushroomSuper(10, 20);
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5f);

        assertThat(mushroomSuper.getVirtX(mockCamera)).isEqualTo(5f); // X - leftCamLimit
    }

    @Test
    void testGetVirtY() {
        MushroomSuper mushroomSuper = new MushroomSuper(10, 20);
        assertThat(mushroomSuper.getVirtY()).isEqualTo(20);
    }

    @Test
    void testGetImage() {
        MushroomSuper mushroomSuper = new MushroomSuper(10, 20);
        assertThat(mushroomSuper.getImage()).isEqualTo("superMushroom.png");
    }
}
