package org.grupogjl.model.game.elements.blocks;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PipeTest {

    @Test
    void testPipeFunctionality() {
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(5.0f);

        Pipe pipe = new Pipe(10, 20, 5, 5);

        // Test connection
        Pipe otherPipe = new Pipe(15, 25, 5, 5);
        pipe.setConection(otherPipe);
        assertThat(pipe.getConection()).isEqualTo(otherPipe);

        // Test virtual coordinates
        assertThat(pipe.getVirtX(camera)).isEqualTo(5.0f);
        assertThat(pipe.getVirtY()).isEqualTo(16); // Y - (Height - 1)

        // Test image
        assertThat(pipe.getImage()).isEqualTo("pipe5.png");
    }
}
