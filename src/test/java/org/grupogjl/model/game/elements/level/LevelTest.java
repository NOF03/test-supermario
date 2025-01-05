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
    public void testInitialization() {
        Level level = new Level(100, 50);

        // Verify initial properties
        assertThat(level.getWidth()).isEqualTo(100);
        assertThat(level.getHeight()).isEqualTo(50);
        assertThat(level.getObjects()).isEmpty();
    }

    @Test
    public void testSetAndGetGoalBlock() {
        Level level = new Level(100, 50);
        GoalBlock goalBlock = mock(GoalBlock.class);

        level.setGoalBlock(goalBlock);
        assertThat(level.getGoalBlock()).isEqualTo(goalBlock);
    }

    @Test
    public void testSetAndGetCamera() {
        Level level = new Level(100, 50);
        Camera camera = mock(Camera.class);

        level.setCamera(camera);
        assertThat(level.getCamera()).isEqualTo(camera);
    }

    @Test
    public void testSetAndGetMario() {
        Level level = new Level(100, 50);
        Mario mario = mock(Mario.class);

        level.setMario(mario);
        assertThat(level.getMario()).isEqualTo(mario);
    }

    @Test
    public void testAddAndRetrieveDestroyableBlocks() {
        Level level = new Level(100, 50);

        DestroyableBlock block1 = mock(DestroyableBlock.class);
        DestroyableBlock block2 = mock(DestroyableBlock.class);
        List<DestroyableBlock> blocks = List.of(block1, block2);

        level.setDestroyableBlocks(blocks);
        assertThat(level.getDestroyableBlocks()).containsExactlyInAnyOrder(block1, block2);
    }

    @Test
    public void testAddAndRetrieveSurpriseBlocks() {
        Level level = new Level(100, 50);

        SurpriseBlock block1 = mock(SurpriseBlock.class);
        SurpriseBlock block2 = mock(SurpriseBlock.class);

        level.getObjects().add(block1);
        level.getObjects().add(block2);

        assertThat(level.getSurpriseBlocks()).containsExactlyInAnyOrder(block1, block2);
    }

    @Test
    public void testGetDestroyableBlocks() {
        Level level = new Level(100, 50);
        List<GameObject> objects = new ArrayList<>();
        DestroyableBlock destroyableBlock = mock(DestroyableBlock.class);
        objects.add(destroyableBlock);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<DestroyableBlock> result = level.getDestroyableBlocks();

        assertThat(result).containsExactly(destroyableBlock);
    }

    @Test
    public void testGetSurpriseBlocks() {
        Level level = new Level(100, 50);
        List<GameObject> objects = new ArrayList<>();
        SurpriseBlock surpriseBlock = mock(SurpriseBlock.class);
        objects.add(surpriseBlock);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<SurpriseBlock> result = level.getSurpriseBlocks();

        assertThat(result).containsExactly(surpriseBlock);
    }

    @Test
    public void testGetEnemies() {
        Level level = new Level(100, 50);
        List<GameObject> objects = new ArrayList<>();
        Enemy enemy = mock(Enemy.class);
        objects.add(enemy);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<Enemy> result = level.getEnemies();

        assertThat(result).containsExactly(enemy);
    }

    @Test
    public void testSetEnemies() {
        Enemy newEnemy = mock(Enemy.class);
        Enemy enemy = mock(Enemy.class);
        Level level = new Level(100, 50);

        List<GameObject> objects = new ArrayList<>();
        objects.add(enemy);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<Enemy> newEnemies = List.of(newEnemy);
        level.setEnemies(newEnemies);

        List<GameObject> updatedObjects = level.getObjects();
        assertThat(updatedObjects).contains(newEnemy).doesNotContain(enemy);
    }

    @Test
    public void testGetSurprises() {
        List<GameObject> objects = new ArrayList<>();
        Level level = new Level(100, 50);
        Surprise surprise = mock(Surprise.class);
        objects.add(mock(GameObject.class));
        objects.add(surprise);
        level.setObjects(objects);

        List<Surprise> result = level.getSurprises();

        assertThat(result).containsExactly(surprise);
    }

    @Test
    public void testSetSurprises() {
        Surprise newSurprise = mock(Surprise.class);
        Surprise surprise = mock(Surprise.class);
        Level level = new Level(100, 50);

        List<GameObject> objects = new ArrayList<>();
        objects.add(surprise);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<Surprise> newSurprises = List.of(newSurprise);
        level.setSurprises(newSurprises);

        List<GameObject> updatedObjects = level.getObjects();
        assertThat(updatedObjects).contains(newSurprise).doesNotContain(surprise);
    }

    @Test
    public void testGetCoins() {
        List<GameObject> objects = new ArrayList<>();
        Level level = new Level(100, 50);
        Coin coin = mock(Coin.class);
        objects.add(mock(GameObject.class));
        objects.add(coin);
        level.setObjects(objects);

        List<Coin> result = level.getCoins();

        assertThat(result).containsExactly(coin);
    }

    @Test
    public void testGetFireBalls() {
        List<GameObject> objects = new ArrayList<>();
        Level level = new Level(100, 50);
        FireBall fireBall = mock(FireBall.class);
        objects.add(mock(GameObject.class));
        objects.add(fireBall);
        level.setObjects(objects);

        List<FireBall> result = level.getFireBalls();

        assertThat(result).containsExactly(fireBall);
    }

    @Test
    public void testSetFireBalls() {
        FireBall newFireBall = mock(FireBall.class);
        FireBall fireBall = mock(FireBall.class);
        Level level = new Level(100, 50);

        List<GameObject> objects = new ArrayList<>();
        objects.add(fireBall);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<FireBall> newFireBalls = List.of(newFireBall);
        level.setFireBalls(newFireBalls);

        List<GameObject> updatedObjects = level.getObjects();
        assertThat(updatedObjects).contains(newFireBall).doesNotContain(fireBall);
    }

    @Test
    public void testAddAndRetrieveEnemies() {
        Level level = new Level(100, 50);

        Enemy enemy1 = mock(Enemy.class);
        Enemy enemy2 = mock(Enemy.class);
        List<Enemy> enemies = List.of(enemy1, enemy2);

        level.setEnemies(enemies);
        assertThat(level.getEnemies()).containsExactlyInAnyOrder(enemy1, enemy2);
    }

    @Test
    public void testAddAndRetrieveSurprises() {
        Level level = new Level(100, 50);

        Surprise surprise1 = mock(Surprise.class);
        Surprise surprise2 = mock(Surprise.class);
        List<Surprise> surprises = List.of(surprise1, surprise2);

        level.setSurprises(surprises);
        assertThat(level.getSurprises()).containsExactlyInAnyOrder(surprise1, surprise2);
    }

    @Test
    public void testAddAndRetrieveCoins() {
        Level level = new Level(100, 50);

        Coin coin1 = mock(Coin.class);
        Coin coin2 = mock(Coin.class);

        level.getObjects().add(coin1);
        level.getObjects().add(coin2);

        assertThat(level.getCoins()).containsExactlyInAnyOrder(coin1, coin2);
    }

    @Test
    public void testAddAndRetrieveFireBalls() {
        Level level = new Level(100, 50);

        FireBall fireBall1 = mock(FireBall.class);
        FireBall fireBall2 = mock(FireBall.class);
        List<FireBall> fireBalls = List.of(fireBall1, fireBall2);

        level.setFireBalls(fireBalls);
        assertThat(level.getFireBalls()).containsExactlyInAnyOrder(fireBall1, fireBall2);
    }

    @Test
    public void testSetObjects() {
        Level level = new Level(100, 50);

        GameObject object1 = mock(GameObject.class);
        GameObject object2 = mock(GameObject.class);
        List<GameObject> objects = List.of(object1, object2);

        level.setObjects(objects);
        assertThat(level.getObjects()).containsExactlyInAnyOrder(object1, object2);
    }

    @Test
    public void testSetSurprisesRemovesExistingSurprises() {
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
    public void testSetDestroyableBlocksRemovesExistingBlocks() {
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
    public void testSetDestroyableBlocks() {
        DestroyableBlock newBlock = mock(DestroyableBlock.class);
        DestroyableBlock destroyableBlock = mock(DestroyableBlock.class);
        Level level = new Level(100, 50);

        List<GameObject> objects = new ArrayList<>();
        objects.add(destroyableBlock);
        objects.add(mock(GameObject.class));
        level.setObjects(objects);

        List<DestroyableBlock> newBlocks = List.of(newBlock);
        level.setDestroyableBlocks(newBlocks);

        List<GameObject> updatedObjects = level.getObjects();
        assertThat(updatedObjects).contains(newBlock).doesNotContain(destroyableBlock);
    }

    @Test
    public void testSetWidthAndHeight() {
        Level level = new Level(100, 50);

        // Update width and height
        level.setWidth(200);
        level.setHeight(300);

        // Verify updated width and height
        assertThat(level.getWidth()).isEqualTo(200);
        assertThat(level.getHeight()).isEqualTo(300);
    }

    @Test
    public void testSetEnemiesRemovesExistingEnemies() {
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
    public void testSetFireBallsRemovesExistingFireBalls() {
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
