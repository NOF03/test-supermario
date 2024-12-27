package org.grupogjl.model.game.elements.level;

import org.grupogjl.model.game.elements.blocks.Pipe;
import org.grupogjl.model.game.elements.blocks.SurpriseBlock;
import org.grupogjl.model.game.elements.enemies.KoopaTroopa;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.surprises.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

class LoaderLevelBuilderTest {

    @Test
    void testReadLines() throws IOException {
        BufferedReader br = new BufferedReader(new StringReader("line1\nline2\nline3"));
        LoaderLevelBuilder builder = mock(LoaderLevelBuilder.class, CALLS_REAL_METHODS);

        // Test reading lines
        List<String> lines = builder.readLines(br);
        assertThat(lines).containsExactly("line1", "line2", "line3");
    }

    @Test
    void testGetWidth() throws IOException {
        LoaderLevelBuilder builder = new LoaderLevelBuilder(1) {
            @Override
            public List<String> readLines(BufferedReader br) throws IOException {
                return List.of("short", "longer line");
            }
        };

        // Test width calculation
        assertThat(builder.getWidth()).isEqualTo("longer line".length());
    }

    @Test
    void testGetHeight() throws IOException {
        LoaderLevelBuilder builder = new LoaderLevelBuilder(1) {
            @Override
            public List<String> readLines(BufferedReader br) throws IOException {
                return List.of("line1", "line2");
            }
        };

        // Test height calculation
        assertThat(builder.getHeight()).isEqualTo(2);
    }

    @Test
    void testCreateGameObjects() throws IOException {
        LoaderLevelBuilder builder = new LoaderLevelBuilder(1) {
            @Override
            public List<String> readLines(BufferedReader br) throws IOException {
                return List.of("#$+?", "uvwxyz", "pg", "a");
            }
        };

        // Test game objects creation
        List<GameObject> objects = builder.createGameObjects();
        assertThat(objects).hasSize(12); // 4 from first row, 6 pipes, 2 enemies
        assertThat(objects.get(0)).isInstanceOf(Pipe.class);
        assertThat(objects.get(1)).isInstanceOf(Pipe.class);
        assertThat(objects.get(2)).isInstanceOf(Pipe.class);
        assertThat(objects.get(3)).isInstanceOf(Pipe.class);
        assertThat(objects.get(4)).isInstanceOf(Pipe.class);
        assertThat(objects.get(10)).isInstanceOf(KoopaTroopa.class);
    }

    @Test
    void testCreateGameObjectsHitsAllRandomCases() throws IOException {
        // Mock the BufferedReader to simulate a single SurpriseBlock line
        BufferedReader mockReader = new BufferedReader(new StringReader("?"));
        LoaderLevelBuilder builder = new LoaderLevelBuilder(1) {
            @Override
            public List<String> readLines(BufferedReader br) throws IOException {
                return List.of("?"); // Simulate a single line with a "?"
            }
        };

        // Set to track all `Surprise` classes encountered
        Set<Class<?>> encounteredSurprises = new HashSet<>();

        // Run the method multiple times until all cases are covered
        for (int i = 0; i < 100; i++) { // Run enough iterations to statistically guarantee coverage
            List<GameObject> objects = builder.createGameObjects();
            assertThat(objects).hasSize(1); // Only one SurpriseBlock expected per invocation

            // Extract the Surprise type and add it to the set
            SurpriseBlock block = (SurpriseBlock) objects.getFirst();
            if (block.getSurprise() != null) {
                encounteredSurprises.add(block.getSurprise().getClass());
            }

            // Break early if all cases are covered
            if (encounteredSurprises.containsAll(Set.of(
                    Mushroom1UP.class,
                    Coin.class,
                    MushroomSuper.class,
                    Flower.class,
                    Star.class
            ))) {
                break;
            }
        }

        // Verify that all Surprise types were encountered
        assertThat(encounteredSurprises).containsExactlyInAnyOrder(
                Mushroom1UP.class,
                Coin.class,
                MushroomSuper.class,
                Flower.class,
                Star.class
        );
    }

    @Test
    void testCreateGameObjectsWithSurpriseBlock() throws IOException {
        LoaderLevelBuilder builder = new LoaderLevelBuilder(1) {
            @Override
            public List<String> readLines(BufferedReader br) throws IOException {
                return List.of("?");
            }
        };

        // Test surprise block creation
        List<GameObject> objects = builder.createGameObjects();
        assertThat(objects).hasSize(1);
        SurpriseBlock block = (SurpriseBlock) objects.getFirst();
        assertThat(block.getSurprise()).isNotNull();
    }

    @Test
    void testConnectPipes() throws IOException {
        LoaderLevelBuilder builder = new LoaderLevelBuilder(1);
        Pipe pipe1 = new Pipe(0, 0, 2, 2);
        Pipe pipe2 = new Pipe(0, 0, 2, 2);
        List<GameObject> objects = List.of(pipe1, pipe2);

        // Connect pipes
        List<GameObject> connectedObjects = builder.connectPipes(objects);

        // Verify connections
        assertThat(connectedObjects).containsExactlyInAnyOrder(pipe1, pipe2);
        assertThat(pipe1.getConection()).isEqualTo(pipe2);
        assertThat(pipe2.getConection()).isEqualTo(pipe2);
    }
}
