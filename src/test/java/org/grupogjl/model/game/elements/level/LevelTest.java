package org.grupogjl.model.game.elements.level;

import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.blocks.DestroyableBlock;
import org.grupogjl.model.game.elements.blocks.GoalBlock;
import org.grupogjl.model.game.elements.blocks.SurpriseBlock;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.props.FireBall;
import org.grupogjl.model.game.elements.surprises.Coin;
import org.grupogjl.model.game.elements.surprises.Surprise;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LevelTest {

    @Test
    void testInitialization() {
        Level level = new Level(100, 50);

        // Verify initial properties
        assertThat(level.getWidth()).isEqualTo(100);
        assertThat(level.getHeight()).isEqualTo(50);
        assertThat(level.getObjects()).isEmpty();
    }

    @Test
    void testSetAndGetGoalBlock() {
        Level level = new Level(100, 50);
        GoalBlock goalBlock = mock(GoalBlock.class);

        level.setGoalBlock(goalBlock);
        assertThat(level.getGoalBlock()).isEqualTo(goalBlock);
    }

    @Test
    void testSetAndGetCamera() {
        Level level = new Level(100, 50);
        Camera camera = mock(Camera.class);

        level.setCamera(camera);
        assertThat(level.getCamera()).isEqualTo(camera);
    }

    @Test
    void testSetAndGetMario() {
        Level level = new Level(100, 50);
        Mario mario = mock(Mario.class);

        level.setMario(mario);
        assertThat(level.getMario()).isEqualTo(mario);
    }

    @Test
    void testAddAndRetrieveDestroyableBlocks() {
        Level level = new Level(100, 50);

        DestroyableBlock block1 = mock(DestroyableBlock.class);
        DestroyableBlock block2 = mock(DestroyableBlock.class);
        List<DestroyableBlock> blocks = List.of(block1, block2);

        level.setDestroyableBlocks(blocks);
        assertThat(level.getDestroyableBlocks()).containsExactlyInAnyOrder(block1, block2);
    }

    @Test
    void testAddAndRetrieveSurpriseBlocks() {
        Level level = new Level(100, 50);

        SurpriseBlock block1 = mock(SurpriseBlock.class);
        SurpriseBlock block2 = mock(SurpriseBlock.class);

        level.getObjects().add(block1);
        level.getObjects().add(block2);

        assertThat(level.getSurpriseBlocks()).containsExactlyInAnyOrder(block1, block2);
    }

    @Test
    void testAddAndRetrieveEnemies() {
        Level level = new Level(100, 50);

        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        List<Enemy> enemies = List.of(enemy1, enemy2);

        level.setEnemies(enemies);
        assertThat(level.getEnemies()).containsExactlyInAnyOrder(enemy1, enemy2);
    }

    @Test
    void testAddAndRetrieveSurprises() {
        Level level = new Level(100, 50);

        Surprise surprise1 = mock(Surprise.class);
        Surprise surprise2 = mock(Surprise.class);
        List<Surprise> surprises = List.of(surprise1, surprise2);

        level.setSurprises(surprises);
        assertThat(level.getSurprises()).containsExactlyInAnyOrder(surprise1, surprise2);
    }

    @Test
    void testAddAndRetrieveCoins() {
        Level level = new Level(100, 50);

        Coin coin1 = mock(Coin.class);
        Coin coin2 = mock(Coin.class);

        level.getObjects().add(coin1);
        level.getObjects().add(coin2);

        assertThat(level.getCoins()).containsExactlyInAnyOrder(coin1, coin2);
    }

    @Test
    void testAddAndRetrieveFireBalls() {
        Level level = new Level(100, 50);

        FireBall fireBall1 = mock(FireBall.class);
        FireBall fireBall2 = mock(FireBall.class);
        List<FireBall> fireBalls = List.of(fireBall1, fireBall2);

        level.setFireBalls(fireBalls);
        assertThat(level.getFireBalls()).containsExactlyInAnyOrder(fireBall1, fireBall2);
    }

    @Test
    void testSetObjects() {
        Level level = new Level(100, 50);

        GameObject object1 = mock(GameObject.class);
        GameObject object2 = mock(GameObject.class);
        List<GameObject> objects = List.of(object1, object2);

        level.setObjects(objects);
        assertThat(level.getObjects()).containsExactlyInAnyOrder(object1, object2);
    }

    @Test
    void testSetSurprisesRemovesExistingSurprises() {
        Level level = new Level(100, 50);

        // Mock existing Surprise in objects
        Surprise existingSurprise = mock(Surprise.class);
        level.getObjects().add(existingSurprise);

        // New surprises to add
        Surprise newSurprise1 = mock(Surprise.class);
        Surprise newSurprise2 = mock(Surprise.class);
        List<Surprise> newSurprises = List.of(newSurprise1, newSurprise2);

        // Set new surprises
        level.setSurprises(newSurprises);

        // Verify that existing surprises were removed and new ones were added
        assertThat(level.getObjects()).containsExactlyInAnyOrder(newSurprise1, newSurprise2);
    }

    @Test
    void testSetDestroyableBlocksRemovesExistingBlocks() {
        Level level = new Level(100, 50);

        // Mock existing DestroyableBlock in objects
        DestroyableBlock existingBlock = mock(DestroyableBlock.class);
        level.getObjects().add(existingBlock);

        // New blocks to add
        DestroyableBlock newBlock1 = mock(DestroyableBlock.class);
        DestroyableBlock newBlock2 = mock(DestroyableBlock.class);
        List<DestroyableBlock> newBlocks = List.of(newBlock1, newBlock2);

        // Set new blocks
        level.setDestroyableBlocks(newBlocks);

        // Verify that existing blocks were removed and new ones were added
        assertThat(level.getObjects()).containsExactlyInAnyOrder(newBlock1, newBlock2);
    }

    @Test
    void testSetWidthAndHeight() {
        Level level = new Level(100, 50);

        // Update width and height
        level.setWidth(200);
        level.setHeight(300);

        // Verify updated width and height
        assertThat(level.getWidth()).isEqualTo(200);
        assertThat(level.getHeight()).isEqualTo(300);
    }

    @Test
    void testSetEnemiesRemovesExistingEnemies() {
        Level level = new Level(100, 50);

        // Mock existing Enemy in objects
        Enemy existingEnemy = mock(Enemy.class);
        level.getObjects().add(existingEnemy);

        // New enemies to add
        Enemy newEnemy1 = mock(Enemy.class);
        Enemy newEnemy2 = mock(Enemy.class);
        List<Enemy> newEnemies = List.of(newEnemy1, newEnemy2);

        // Set new enemies
        level.setEnemies(newEnemies);

        // Verify that existing enemies were removed and new ones were added
        assertThat(level.getObjects()).containsExactlyInAnyOrder(newEnemy1, newEnemy2);
    }

    @Test
    void testSetFireBallsRemovesExistingFireBalls() {
        Level level = new Level(100, 50);

        // Mock existing FireBall in objects
        FireBall existingFireBall = mock(FireBall.class);
        level.getObjects().add(existingFireBall);

        // New fireballs to add
        FireBall newFireBall1 = mock(FireBall.class);
        FireBall newFireBall2 = mock(FireBall.class);
        List<FireBall> newFireBalls = List.of(newFireBall1, newFireBall2);

        // Set new fireballs
        level.setFireBalls(newFireBalls);

        // Verify that existing fireballs were removed and new ones were added
        assertThat(level.getObjects()).containsExactlyInAnyOrder(newFireBall1, newFireBall2);
    }


}
