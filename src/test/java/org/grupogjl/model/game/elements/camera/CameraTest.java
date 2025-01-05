package org.grupogjl.model.game.elements.camera;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CameraTest {

    @Test
    public void testCameraInitialization() {
        Camera camera = new Camera();

        // Test initial camera limits
        assertThat(camera.getLeftCamLimit()).isEqualTo(0);
        assertThat(camera.getRightCamLimit()).isEqualTo(26);
    }

    @Test
    public void testUpdateCamera() {
        Camera camera = new Camera();
        Mario mockMario = mock(Mario.class);
        when(mockMario.getX()).thenReturn(20.0f);

        // Update camera
        camera.updateCamera(mockMario);

        // Verify new camera limits
        assertThat(camera.getLeftCamLimit()).isEqualTo(7.0f); // Mario X - 13
        assertThat(camera.getRightCamLimit()).isEqualTo(33.0f); // Left cam + 26
    }

    @Test
    public void testUpdateCameraElse() {
        Camera camera = new Camera();
        Mario mockMario = mock(Mario.class);
        when(mockMario.getX()).thenReturn(2.0f);

        // Update camera
        camera.updateCamera(mockMario);

        // Verify new camera limits
        assertThat(camera.getLeftCamLimit()).isEqualTo(0.0f); // Mario X - 13
        assertThat(camera.getRightCamLimit()).isEqualTo(26.0f); // Left cam + 26
    }

    @Test
    public void testIsEnemyOnCam() {
        Camera camera = new Camera();
        Enemy mockEnemy = mock(Enemy.class);

        when(mockEnemy.getX()).thenReturn(10.0f);
        when(mockEnemy.getWidth()).thenReturn(2.0f);
        when(mockEnemy.wasRevealed()).thenReturn(false);

        // Test enemy visibility
        camera.isEnemyOnCam(mockEnemy);
        verify(mockEnemy).setRevealed(true); // Enemy is now revealed
    }

    @Test
    public void testIsEnemyOnCam_AllConditionsTrue() {
        Camera camera = new Camera();
        Enemy mockEnemy = mock(Enemy.class);

        // Mock conditions where all conditions are true
        when(mockEnemy.getX()).thenReturn(10.0f);
        when(mockEnemy.getWidth()).thenReturn(2.0f);
        when(mockEnemy.wasRevealed()).thenReturn(false);

        // Test enemy visibility
        camera.isEnemyOnCam(mockEnemy);

        verify(mockEnemy).setRevealed(true); // Enemy should be revealed
    }

    @Test
    public void testIsEnemyOnCam_Condition1False() {
        Camera camera = new Camera();
        Enemy mockEnemy = mock(Enemy.class);

        // Mock conditions where the first condition is false
        when(mockEnemy.getX()).thenReturn(20.0f); // Too far right
        when(mockEnemy.getWidth()).thenReturn(20.0f);
        when(mockEnemy.wasRevealed()).thenReturn(false);

        // Test enemy visibility
        camera.isEnemyOnCam(mockEnemy);

        verify(mockEnemy, never()).setRevealed(anyBoolean()); // Enemy should not be revealed
    }

    @Test
    public void testIsEnemyOnCam_Condition2False() {
        Camera camera = new Camera();
        Enemy mockEnemy = mock(Enemy.class);

        // Mock conditions where the second condition is false
        when(mockEnemy.getX()).thenReturn(-10.0f); // Too far left
        when(mockEnemy.getWidth()).thenReturn(2.0f);
        when(mockEnemy.wasRevealed()).thenReturn(false);

        // Test enemy visibility
        camera.isEnemyOnCam(mockEnemy);

        verify(mockEnemy, never()).setRevealed(anyBoolean()); // Enemy should not be revealed
    }

    @Test
    public void testIsEnemyOnCam_Condition3False() {
        Camera camera = new Camera();
        Enemy mockEnemy = mock(Enemy.class);

        // Mock conditions where the third condition is false
        when(mockEnemy.getX()).thenReturn(10.0f);
        when(mockEnemy.getWidth()).thenReturn(2.0f);
        when(mockEnemy.wasRevealed()).thenReturn(true); // Already revealed

        // Test enemy visibility
        camera.isEnemyOnCam(mockEnemy);

        verify(mockEnemy, never()).setRevealed(anyBoolean()); // Enemy should not be revealed again
    }

    @Test
    public void testIsEnemyOnCam_AllConditionsFalse() {
        Camera camera = new Camera();
        Enemy mockEnemy = mock(Enemy.class);

        // Mock conditions where all conditions are false
        when(mockEnemy.getX()).thenReturn(20.0f); // Too far right
        when(mockEnemy.getWidth()).thenReturn(2.0f);
        when(mockEnemy.wasRevealed()).thenReturn(true); // Already revealed

        // Test enemy visibility
        camera.isEnemyOnCam(mockEnemy);

        verify(mockEnemy, never()).setRevealed(anyBoolean()); // Enemy should not be revealed
    }

}
