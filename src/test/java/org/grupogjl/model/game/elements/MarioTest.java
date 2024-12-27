package org.grupogjl.model.game.elements;

import org.grupogjl.model.game.elements.blocks.*;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.enemies.Enemy;
import org.grupogjl.model.game.elements.surprises.Surprise;
import org.grupogjl.state.StateGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MarioTest {

    private Mario mario;

    @BeforeEach
    void setUp() {
        mario = new Mario(10, 20, 1, 2);
    }

    @Test
    void testGetImageCoversAllScenarios() {
        // Set to track all possible outcomes
        Set<String> observedImages = new HashSet<>();

        // Variables to represent state combinations
        boolean[] booleanStates = {true, false};

        // Iterate through all possible state combinations
        for (boolean stateBig : booleanStates) {
            for (boolean stateFire : booleanStates) {
                for (boolean stateInvincible : booleanStates) {
                    for (boolean hitCooldown : booleanStates) {
                        mario.setStateBig(stateBig);
                        mario.setStateFire(stateFire);
                        mario.setStateInvencible(stateInvincible);
                        mario.setHitCooldown(hitCooldown);

                        // Call `getImage()` multiple times to account for randomness
                        for (int i = 0; i < 10; i++) {
                            observedImages.add(mario.getImage());
                        }
                    }
                }
            }
        }

        // Verify all possible outcomes
        assertThat(observedImages).containsExactlyInAnyOrder(
                "marioStarBig.png",
                "marioSuper.png",
                "marioFlower.png",
                "hitMario.png",
                "marioStar.png",
                "mario.png"
        );
    }

    @Test
    void testGetImageReturnsNull() {
        // Set the state of Mario to ensure no condition is satisfied
        Mario spyMario = spy(mario);
        String result = "mario.png";
        // Configure the initial conditions
        while (result != null) {
            doReturn(true).doReturn(false).when(spyMario).isStateInvencible(); // First true, then false
            spyMario.setHitCooldown(false); // No cooldown

            // Call getImage and verify the "else" of the "else if" is reached
            result = spyMario.getImage();
        }

        // Validate that the "mario.png" is returned since the else condition was hit
        assertThat(result).isEqualTo(null);
    }

    @Test
    void testMarioInitialization() {
        Mario mario = new Mario(10, 20, 1, 1);

        // Verify initial state
        assertThat(mario.getX()).isEqualTo(10);
        assertThat(mario.getY()).isEqualTo(20);
        assertThat(mario.getWidth()).isEqualTo(1);
        assertThat(mario.getHeight()).isEqualTo(1);
        assertThat(mario.getLives()).isEqualTo(3);
        assertThat(mario.getCoins()).isEqualTo(0);
    }

    @Test
    void testJump() {
        Mario mario = new Mario(10, 20, 1, 1);

        // Jump
        mario.jump();
        assertThat(mario.isJumping()).isTrue();
        assertThat(mario.getVy()).isEqualTo(1.3f);
    }

    @Test
    void testMoveLeft() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.moveLeft();

        // Verify velocity
        assertThat(mario.getVx()).isEqualTo(-0.5f);
    }

    @Test
    void testMoveRight() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.moveRight();

        // Verify velocity
        assertThat(mario.getVx()).isEqualTo(0.5f);
    }

    @Test
    void testNotifyStateLives() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);

        // Notify state
        mario.notifyState("lives");
        verify(mockObserver).resetLevel();
    }

    @Test
    void testNotifyStateGoal() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);

        // Notify state
        mario.notifyState("goal");
        verify(mockObserver).nextLevel();
    }

    @Test
    void testNotifyStateIOException() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);

        // Simulate IOException when resetLevel or nextLevel is called
        doThrow(new IOException()).when(mockObserver).resetLevel();

        // Expect RuntimeException when notifyState is called with "lives"
        RuntimeException exception = assertThrows(RuntimeException.class, () -> mario.notifyState("lives"));
        assertThat(exception.getCause()).isInstanceOf(IOException.class);
    }

    @Test
    void testNotifyStateElse() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);

        // Notify state
        mario.notifyState("goala");
        verify(mockObserver, never()).nextLevel();
    }

    @Test
    void testHandleWallCollision() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setVx(1.0f);

        // Handle wall collision
        mario.handleWallColision(5.0f);

        // Verify position and velocity
        assertThat(mario.getX()).isEqualTo(5.0f);
        assertThat(mario.getVx()).isEqualTo(0.0f);
    }

    @Test
    void testHandleCollisionWithSurprise() {
        Mario mario = new Mario(10, 20, 1, 1);
        Surprise mockSurprise = mock(Surprise.class);

        // Handle collision with a surprise
        mario.handleCollision(mockSurprise, 'U');
        verify(mockSurprise).activate(mario);
        verify(mockSurprise).setActivated(false);
    }

    @Test
    void testHandleCollisionWithEnemyAbove() {
        Mario mario = new Mario(10, 20, 1, 1);
        Enemy mockEnemy = mock(Enemy.class);
        when(mockEnemy.getLives()).thenReturn(1);

        // Handle collision with an enemy from above
        mario.handleCollision(mockEnemy, 'D');
        assertThat(mario.isJumping()).isTrue();
        assertThat(mario.isFalling()).isFalse();
        assertThat(mario.getVy()).isEqualTo(0.7f);
        verify(mockEnemy).setLives(0);
    }

    @Test
    void testHandleCollisionWithEnemyInvincible() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setStateInvencible(true);
        mario.setHitCooldown(false);

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy while invincible and not in hit cooldown
        mario.handleCollision(mockEnemy, 'L');

        // Verify enemy's lives and Mario's velocity
        verify(mockEnemy).setLives(0);
        assertThat(mario.getVx()).isEqualTo(0.0f);
    }

    @Test
    void testHandleCollisionWithEnemyInvincibleAndHitCooldown() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setStateInvencible(true);
        mario.setHitCooldown(true);

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy while invincible and not in hit cooldown
        mario.handleCollision(mockEnemy, 'L');

        assertThat(mario.getVx()).isEqualTo(0.0f);
    }

    @Test
    void testHandleCollisionWithEnemySideWhenBigOrFire() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setStateBig(true);
        mario.setStateFire(true);
        mario.setHeight(2);

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy from the side when Mario is big or has fire state
        mario.handleCollision(mockEnemy, 'L');

        // Verify state changes
        assertThat(mario.isStateBig()).isFalse();
        assertThat(mario.isStateFire()).isFalse();
        assertThat(mario.getHeight()).isEqualTo(1);
        assertThat(mario.getInvencibleTime()).isEqualTo(15);
        assertThat(mario.isStateInvencible()).isTrue();
        assertThat(mario.isHitCooldown()).isTrue();
    }

    @Test
    void testHandleCollisionWithEnemySideWhenNoBigOrFire() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setStateBig(false);
        mario.setStateFire(true);
        mario.setHeight(2);

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy from the side when Mario is big or has fire state
        mario.handleCollision(mockEnemy, 'L');

        // Verify state changes
        assertThat(mario.isStateBig()).isFalse();
        assertThat(mario.isStateFire()).isFalse();
        assertThat(mario.getHeight()).isEqualTo(1);
        assertThat(mario.getInvencibleTime()).isEqualTo(15);
        assertThat(mario.isStateInvencible()).isTrue();
        assertThat(mario.isHitCooldown()).isTrue();
    }

    @Test
    void testHandleCollisionWithEnemySideWhenBigOrNoFire() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setStateBig(true);
        mario.setStateFire(false);
        mario.setHeight(2);

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy from the side when Mario is big or has fire state
        mario.handleCollision(mockEnemy, 'L');

        // Verify state changes
        assertThat(mario.isStateBig()).isFalse();
        assertThat(mario.isStateFire()).isFalse();
        assertThat(mario.getHeight()).isEqualTo(1);
        assertThat(mario.getInvencibleTime()).isEqualTo(15);
        assertThat(mario.isStateInvencible()).isTrue();
        assertThat(mario.isHitCooldown()).isTrue();
    }

    @Test
    void testHandleCollisionWithEnemySideWhenNotBigOrFire() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setStateBig(false);
        mario.setStateFire(false);
        mario.setLives(3);

        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy from the side when Mario is neither big nor has fire state
        mario.handleCollision(mockEnemy, 'L');

        // Verify state changes
        assertThat(mario.getLives()).isEqualTo(2);
        verify(mockObserver).resetLevel();
    }

    @Test
    void testHandleCollisionWithEnemySide() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver); // Set the observer to avoid NullPointerException

        Enemy mockEnemy = mock(Enemy.class);

        // Handle collision with an enemy from the side
        mario.handleCollision(mockEnemy, 'L');

        // Verify state changes
        assertThat(mario.isStateBig()).isFalse();
        assertThat(mario.isStateFire()).isFalse();
        verify(mockObserver).resetLevel(); // Ensure `resetLevel` is called
    }

    @Test
    void testHandleCollisionWithGoalBlock() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        GoalBlock mockGoalBlock = mock(GoalBlock.class);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);

        // Handle collision with a goal block
        mario.handleCollision(mockGoalBlock, 'U');
        verify(mockObserver).nextLevel();
    }

    @Test
    void testHandleCollisionWithInteractableBlockUp() {
        Mario mario = new Mario(10, 20, 1, 1);
        InteractableBlock mockBlock = mock(InteractableBlock.class);

        // Handle collision with an interactable block from above
        mario.handleCollision(mockBlock, 'U');
        verify(mockBlock).gotHit(mario);
        assertThat(mario.isFalling()).isTrue();
        assertThat(mario.isJumping()).isFalse();
    }

    @Test
    void testHandleCollisionWithInteractableBlockDown() {
        Mario mario = new Mario(10, 20, 1, 1);
        InteractableBlock mockBlock = mock(InteractableBlock.class);

        // Handle collision with an interactable block from above
        mario.handleCollision(mockBlock, 'D');
        assertThat(mario.isFalling()).isFalse();
        assertThat(mario.isJumping()).isFalse();
    }

    @Test
    void testHandleCollisionWithInteractableBlockRight() {
        Mario mario = new Mario(10, 20, 1, 1);
        InteractableBlock mockBlock = mock(InteractableBlock.class);

        // Handle collision with an interactable block from above
        mario.handleCollision(mockBlock, 'R');
        assertThat(mario.getVx()).isEqualTo(0.0f);
        assertThat(mario.getX()).isEqualTo(-1.0f);
    }

    @Test
    void testHandleCollisionWithInteractableBlockLeft() {
        Mario mario = new Mario(10, 20, 1, 1);
        InteractableBlock mockBlock = mock(InteractableBlock.class);

        // Handle collision with an interactable block from above
        mario.handleCollision(mockBlock, 'L');
        assertThat(mario.getVx()).isEqualTo(0.0f);
        assertThat(mario.getX()).isEqualTo(0.0f);
    }

    @Test
    void testHandleCollisionWithInteractableBlockNothing() {
        Mario mario = new Mario(10, 20, 1, 1);
        InteractableBlock mockBlock = mock(InteractableBlock.class);

        // Handle collision with an interactable block from above
        mario.handleCollision(mockBlock, 'A');
        assertThat(mario.getVx()).isEqualTo(0.0f);
        assertThat(mario.getX()).isEqualTo(10.0f);
    }

    @Test
    void testHandleCollisionWithPipe() {
        Mario mario = new Mario(10, 20, 1, 1);
        Pipe mockBlock = mock(Pipe.class);

        // Handle collision with an interactable block from above
        mario.handleCollision(mockBlock, 'U');
        assertThat(mario.isFalling()).isTrue();
        assertThat(mario.isJumping()).isFalse();
    }

    @Test
    void testHandleCollisionWithMario() {
        Mario mario = new Mario(10, 20, 1, 1);
        Mario mockBlock = mock(Mario.class);

        // Handle collision with a building block from the right
        mario.handleCollision(mockBlock, 'R');
        assertThat(mario.getX()).isEqualTo(10.0f); // Adjusted position
        assertThat(mario.getVx()).isEqualTo(0.0f);
    }

    @Test
    void testReset() throws IOException {
        Mario mario = new Mario(10, 20, 1, 1);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);
        mario.setLives(3);

        // Reset Mario
        mario.reset();
        assertThat(mario.getLives()).isEqualTo(2);
        verify(mockObserver).resetLevel();
    }

    @Test
    void testResetWhenBig() throws IOException {
        Mario mario = new Mario(10, 20, 1, 2);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);
        mario.setLives(3);
        mario.setStateBig(true); // Set Mario to Big
        mario.setStateFire(false);

        // Reset Mario
        mario.reset();

        assertThat(mario.getHeight()).isEqualTo(1); // Height is reset to 1
        verify(mockObserver).resetLevel();
    }

    @Test
    void testResetWhenFire() throws IOException {
        Mario mario = new Mario(10, 20, 1, 2);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);
        mario.setLives(3);
        mario.setStateBig(false);
        mario.setStateFire(true); // Set Mario to Fire

        // Reset Mario
        mario.reset();

        assertThat(mario.getHeight()).isEqualTo(1); // Height is reset to 1
        verify(mockObserver).resetLevel();
    }

    @Test
    void testResetWhenBigAndFire() throws IOException {
        Mario mario = new Mario(10, 20, 1, 2);
        StateGame mockObserver = mock(StateGame.class);
        mario.setObserver(mockObserver);
        mario.setLives(3);
        mario.setStateBig(true);  // Set Mario to Big
        mario.setStateFire(true); // Set Mario to Fire

        // Reset Mario
        mario.reset();

        assertThat(mario.getHeight()).isEqualTo(1); // Height is reset to 1
        verify(mockObserver).resetLevel();
    }


    @Test
    void testGetVirtX() {
        Camera mockCamera = mock(Camera.class);
        when(mockCamera.getLeftCamLimit()).thenReturn(5.0f);

        Mario mario = new Mario(10, 20, 1, 1);

        // Verify virtual X
        assertThat(mario.getVirtX(mockCamera)).isEqualTo(5.0f);
    }

    @Test
    void testGetVirtY() {
        Mario mario = new Mario(10, 20, 1, 1);

        // Verify virtual Y
        assertThat(mario.getVirtY()).isEqualTo(20.0f); // Y - (Height - 1)
    }

    @Test
    void testGetInvencibleTime() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setInvencibleTime(600);

        assertThat(mario.getInvencibleTime()).isEqualTo(600);
    }

    @Test
    void testSetCoins() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setCoins(100);

        assertThat(mario.getCoins()).isEqualTo(100);
    }

    @Test
    void testIsHitCooldown() {
        Mario mario = new Mario(10, 20, 1, 1);
        mario.setHitCooldown(true);

        assertThat(mario.isHitCooldown()).isTrue();
    }
}
