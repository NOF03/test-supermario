package org.grupogjl.model.game.elements.level;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LevelBuilderTest {

    private static class TestLevelBuilder extends LevelBuilder {
        @Override
        public int getWidth() {
            return 100;
        }

        @Override
        public int getHeight() {
            return 50;
        }

        @Override
        public List<GameObject> createGameObjects() {
            return List.of(mock(GameObject.class), mock(GameObject.class));
        }

        @Override
        protected List<GameObject> connectPipes(List<GameObject> blocks) {
            return blocks; // For testing, return blocks unmodified
        }
    }

    @Test
    void testCreateLevel() {
        LevelBuilder builder = new TestLevelBuilder();

        // Create level
        Level level = builder.createLevel();

        // Verify level properties
        assertThat(level.getWidth()).isEqualTo(100);
        assertThat(level.getHeight()).isEqualTo(50);
        assertThat(level.getObjects()).hasSize(2); // Two mock objects added
        assertThat(level.getCamera()).isNotNull(); // Camera should be set
    }
}
