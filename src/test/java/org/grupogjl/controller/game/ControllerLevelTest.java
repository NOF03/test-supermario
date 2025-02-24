package org.grupogjl.controller.game;

import org.grupogjl.controller.game.blocks.ControllerBlocks;
import org.grupogjl.controller.game.physicalobjects.ControllerEnemy;
import org.grupogjl.controller.game.physicalobjects.ControllerFireBall;
import org.grupogjl.controller.game.physicalobjects.ControllerMario;
import org.grupogjl.controller.game.surprises.ControllerSurprises;
import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.blocks.BuildingBlock;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.generalobjects.PhysicalObject;
import org.grupogjl.model.game.elements.generalobjects.StaticObject;
import org.grupogjl.model.game.elements.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerLevelTest {

    @Mock
    private ControllerLevel controllerLevel;
    @Mock
    private ControllerMario controllerMario;
    @Mock
    private ControllerEnemy controllerEnemy;
    @Mock
    private ControllerBlocks controllerBlocks;
    @Mock
    private ControllerSurprises controllerSurprises;
    @Mock
    private ControllerFireBall controllerFireBall;

    @BeforeEach
    public void setUp() {
        controllerMario = mock(ControllerMario.class);
        controllerEnemy = mock(ControllerEnemy.class);
        controllerBlocks = mock(ControllerBlocks.class);
        controllerSurprises = mock(ControllerSurprises.class);
        controllerFireBall = mock(ControllerFireBall.class);

        controllerLevel = new ControllerLevel();

        // Inject mocks into the controllerLevel
        controllerLevel = Mockito.spy(controllerLevel);
        lenient().doReturn(controllerMario).when(controllerLevel).getControllerMario();
        lenient().doReturn(controllerEnemy).when(controllerLevel).getControllerEnemy();
        lenient().doReturn(controllerBlocks).when(controllerLevel).getControllerBlocks();
        lenient().doReturn(controllerSurprises).when(controllerLevel).getControllerSurprises();
        lenient().doReturn(controllerFireBall).when(controllerLevel).getControllerFireBall();
    }

    @Test
    public void testGetControllerMario() {
        // Arrange
        ControllerLevel controllerLevel = spy(ControllerLevel.class);

        // Act
        ControllerMario result = controllerLevel.getControllerMario();

        // Assert
        assertSame(controllerLevel.getControllerMario(), result);
    }

    @Test
    public void testGetControllerEnemy() {
        // Arrange
        ControllerLevel controllerLevel = spy(ControllerLevel.class);

        // Act
        ControllerEnemy result = controllerLevel.getControllerEnemy();

        // Assert
        assertSame(controllerLevel.getControllerEnemy(), result);
    }

    @Test
    public void testGetControllerBlocks() {
        // Arrange
        ControllerLevel controllerLevel = spy(ControllerLevel.class);

        // Act
        ControllerBlocks result = controllerLevel.getControllerBlocks();

        // Assert
        assertSame(controllerLevel.getControllerBlocks(), result);
    }

    @Test
    public void testGetControllerSurprises() {
        // Arrange
        ControllerLevel controllerLevel = spy(ControllerLevel.class);

        // Act
        ControllerSurprises result = controllerLevel.getControllerSurprises();

        // Assert
        assertSame(controllerLevel.getControllerSurprises(), result);
    }

    @Test
    public void testGetControllerFireBall() {
        // Arrange
        ControllerLevel controllerLevel = spy(ControllerLevel.class);

        // Act
        ControllerFireBall result = controllerLevel.getControllerFireBall();

        // Assert
        assertSame(controllerLevel.getControllerFireBall(), result);
    }

    @Test
    public void testStep() {
        // Mock dependencies
        Level level = mock(Level.class);
        Mario mario = mock(Mario.class); // Mario is also a GameObject
        Camera camera = mock(Camera.class);
        PhysicalObject physicalObject = mock(PhysicalObject.class); // Mock as PhysicalObject

        // Mocked list of objects
        List<GameObject> objects = Arrays.asList(physicalObject, mario);

        // Define behavior for PhysicalObject
        when(physicalObject.getX()).thenReturn(5f);
        lenient().when(physicalObject.getY()).thenReturn(5f);
        lenient().when(physicalObject.getVirtX(camera)).thenReturn(5f);
        lenient().when(physicalObject.getVirtY()).thenReturn(5f);
        lenient().when(physicalObject.getImage()).thenReturn("object_image.png");

        // Define behavior for Mario
        when(mario.getX()).thenReturn(1f);
        lenient().when(mario.getY()).thenReturn(1f);
        lenient().when(mario.getVirtX(camera)).thenReturn(1f);
        lenient().when(mario.getVirtY()).thenReturn(1f);
        lenient().when(mario.getImage()).thenReturn("mario_image.png");

        // Define behavior for Level
        when(level.getMario()).thenReturn(mario);
        when(level.getCamera()).thenReturn(camera);
        when(level.getObjects()).thenReturn(objects);

        // Call the step method
        controllerLevel.step(level, GeneralGui.ACTION.NONE, 100L);

        // Verify interactions
        verify(controllerFireBall).step(level, GeneralGui.ACTION.NONE, 100L);
        verify(controllerMario).step(level, GeneralGui.ACTION.NONE, 100L);
        verify(controllerEnemy).step(level, 100L);
        verify(controllerSurprises).step(level, GeneralGui.ACTION.NONE, 100L);
        verify(controllerBlocks).step(level, GeneralGui.ACTION.NONE, 100L);
        verify(controllerLevel).checkCollisions(mario, objects, camera);
    }

    @Test
    public void testCheckPhysicalCollisionsX() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);
        BuildingBlock block = mock(BuildingBlock.class);

        when(camera.getLeftCamLimit()).thenReturn(0f);
        when(physicalObject.getX()).thenReturn(-1f);
        when(physicalObject.getVx()).thenReturn(-1f);

        controllerLevel.CheckPhysicalCollisionsX(physicalObject, Collections.singletonList(block), camera);

        verify(physicalObject).handleWallColision(0f);
    }

    @Test
    public void testCheckPhysicalCollisionsX_WallCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock wall collision condition
        when(mainObject.getX()).thenReturn(0f);
        when(mainObject.getVx()).thenReturn(-1f); // Moving left
        when(camera.getLeftCamLimit()).thenReturn(0f);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.emptyList(), camera);

        verify(mainObject).handleWallColision(0f); // Verify wall collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_NoVelocityCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getY()).thenReturn(5f);
        when(enemy.getX()).thenReturn(6f);
        when(enemy.getY()).thenReturn(5f);
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject).handleCollision(enemy, 'L'); // Verify left collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_RightwardCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);
        Camera camera = mock(Camera.class);

        // Mock rightward movement and collision condition
        when(mainObject.getVx()).thenReturn(3f);
        when(mainObject.getX()).thenReturn(5f, 6f, 7f); // Simulate movement
        when(mainObject.collidesWith(object)).thenReturn(false, true); // No collision -> Collision

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(object), camera);

        verify(mainObject).handleCollision(object, 'R'); // Verify right collision handling
        verify(mainObject, times(2)).setX(anyFloat()); // Verify movement simulation
    }

    @Test
    public void testCheckPhysicalCollisionsX_LeftwardCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);
        Camera camera = mock(Camera.class);

        // Mock leftward movement and collision condition
        when(mainObject.getVx()).thenReturn(-3f);
        when(mainObject.getX()).thenReturn(5f, 4f, 3f); // Simulate movement
        when(mainObject.collidesWith(object)).thenReturn(false, true); // No collision -> Collision

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(object), camera);

        verify(mainObject).handleCollision(object, 'L'); // Verify left collision handling
        verify(mainObject, times(2)).setX(anyFloat()); // Verify movement simulation
    }

    @Test
    public void testCheckPhysicalCollisionsX_UpwardCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);
        Camera camera = mock(Camera.class);

        // Mock rightward movement and collision condition
        when(mainObject.getVx()).thenReturn(3f);
        when(mainObject.getX()).thenReturn(5f, 6f, 7f); // Simulate movement
        when(mainObject.collidesWith(object)).thenReturn(false, true); // No collision -> Collision

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(object), camera);
// Verify right collision handling
        verify(mainObject, times(2)).setX(anyFloat()); // Verify movement simulation
    }

    @Test
    public void testCheckPhysicalCollisionsX_NoCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);
        Camera camera = mock(Camera.class);

        // Mock rightward movement with no collision
        when(mainObject.getVx()).thenReturn(3f);
        when(mainObject.getX()).thenReturn(5f, 6f, 7f); // Simulate movement
        when(mainObject.collidesWith(object)).thenReturn(false); // No collision

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(object), camera);

        verify(mainObject, never()).handleCollision(any(GameObject.class), anyChar());
    }

    @Test
    public void testCheckPhysicalCollisionsX_ZeroVelocityLeftCollision() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and left collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(5f);
        when(mainObject.getY()).thenReturn(5f);
        when(enemy.getX()).thenReturn(6f); // Enemy is to the right of the main object
        when(enemy.getY()).thenReturn(5f); // Same level
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject).handleCollision(enemy, 'L'); // Verify left collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_ZeroVelocityRightCollision() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and right collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(5f);
        when(mainObject.getY()).thenReturn(5f);
        when(enemy.getX()).thenReturn(4f); // Enemy is to the left of the main object
        when(enemy.getY()).thenReturn(5f); // Same level
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject).handleCollision(enemy, 'R'); // Verify right collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_ThirdIfAllTrue() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and right collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(5f);
        when(mainObject.getY()).thenReturn(5f);
        when(enemy.getX()).thenReturn(6f); // Enemy is to the left of the main object
        when(enemy.getY()).thenReturn(5f); // Same level
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject).handleCollision(enemy, 'L'); // Verify right collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_ThirdIfFirstTrueSecondFalse() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and right collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(5f);
        when(mainObject.getY()).thenReturn(4f);
        when(enemy.getX()).thenReturn(6f); // Enemy is to the left of the main object
        when(enemy.getY()).thenReturn(5f); // Same level
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject, never()).handleCollision(enemy, 'L'); // Verify right collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_ThirdIfFirstFalseSecondTrue() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and right collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(7f);
        when(mainObject.getY()).thenReturn(5f);
        when(enemy.getX()).thenReturn(6f); // Enemy is to the left of the main object
        when(enemy.getY()).thenReturn(5f); // Same level
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject, never()).handleCollision(enemy, 'L'); // Verify right collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_ThirdIfAllFalse() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity and right collision condition
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(7f);
        when(mainObject.getY()).thenReturn(4f);
        when(enemy.getX()).thenReturn(6f); // Enemy is to the left of the main object
        when(enemy.getY()).thenReturn(5f); // Same level
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        verify(mainObject, never()).handleCollision(enemy, 'L'); // Verify right collision handling
    }

    @Test
    public void testCheckPhysicalCollisionsX_ZeroVelocityNoCollision() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity with no collision
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(false);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        // Verify no collision handling
        verify(mainObject, never()).handleCollision(any(PhysicalObject.class), anyChar());
    }

    @Test
    public void testCheckPhysicalCollisionsX_SameObjectVxGreaterThan0() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock zero velocity with no collision
        when(mainObject.getVx()).thenReturn(1f);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(mainObject), camera);

        // Verify no collision handling
        verify(mainObject, never()).handleCollision(any(PhysicalObject.class), anyChar());
    }

    @Test
    public void testCheckPhysicalCollisionsX_SameObjectVxLowerThan0() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);
        when(camera.getLeftCamLimit()).thenReturn(-2f);
        // Mock zero velocity with no collision
        when(mainObject.getVx()).thenReturn(-1f);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(mainObject), camera);

        // Verify no collision handling
        verify(mainObject, never()).handleCollision(any(PhysicalObject.class), anyChar());
    }

    @Test
    public void testCheckPhysicalCollisionsX_ZeroVelocityNonPhysicalObject() {
        PhysicalObject mainObject = mock(PhysicalObject.class);
        GameObject nonPhysicalObject = mock(GameObject.class); // Not a PhysicalObject
        Camera camera = mock(Camera.class);

        // Mock zero velocity
        when(mainObject.getVx()).thenReturn(0f);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(nonPhysicalObject), camera);

        // Verify no collision handling
        verify(mainObject, never()).handleCollision(any(PhysicalObject.class), anyChar());
    }

    @Test
    public void testCheckPhysicalCollisionsX_ResetPositionOnNoCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);
        Camera camera = mock(Camera.class);

        // Mock leftward movement with no collision
        when(mainObject.getVx()).thenReturn(-3f);
        when(mainObject.getX()).thenReturn(5f, 4f, 3f); // Simulate movement
        when(mainObject.collidesWith(object)).thenReturn(false); // No collision

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(object), camera);

        verify(mainObject).setX(4f); // Verify reset to starting position
    }

    @Test
    public void testCheckPhysicalCollisionsX_NoVelocityRightCollisionWithEnemy() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject mainObject = mock(PhysicalObject.class);
        PhysicalObject enemy = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);

        // Mock no velocity but collision condition on the right
        when(mainObject.getVx()).thenReturn(0f);
        when(mainObject.getX()).thenReturn(5f);
        when(mainObject.getY()).thenReturn(5f);
        when(enemy.getX()).thenReturn(4f); // Enemy is to the left of the main object
        when(enemy.getY()).thenReturn(5f);
        when(mainObject.collidesWithPhysical(enemy, Math.abs(enemy.getVx()), -0.2f)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsX(mainObject, Collections.singletonList(enemy), camera);

        // Verify that the collision is handled as a right collision ('R')
        verify(mainObject).handleCollision(enemy, 'R');
    }

    @Test
    public void testCheckPhysicalCollisionsY() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(physicalObject.getY()).thenReturn(3f);
        when(block.getX()).thenReturn(4f);
        when(block.getWidth()).thenReturn(2f);
        when(block.getY()).thenReturn(1f);
        when(block.getHeight()).thenReturn(2f);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        verify(physicalObject).setFalling(true);
    }

    @Test
    public void testCheckPhysicalCollisionsY_BothConditionsTrue() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        // Mock the condition where both subconditions are true
        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(block.getX()).thenReturn(6f);
        when(block.getWidth()).thenReturn(2f);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        // Verify that the blockbelow state could be set if the second if is true
        verify(physicalObject).setFalling(anyBoolean());
    }

    @Test
    public void testCheckPhysicalCollisionsY_FirstConditionTrueSecondFalse() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        // Mock the condition where the first subcondition is true but the second is false
        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(block.getX()).thenReturn(8f);
        when(block.getWidth()).thenReturn(2f);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        // Verify that blockbelow remains unaffected
        verify(physicalObject).setFalling(anyBoolean());
    }

    @Test
    public void testCheckPhysicalCollisionsY_FirstConditionFalseSecondTrue() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        // Mock the condition where the first subcondition is false but the second is true
        when(physicalObject.getX()).thenReturn(10f);
        lenient().when(physicalObject.getWidth()).thenReturn(2f);
        when(block.getX()).thenReturn(8f);
        when(block.getWidth()).thenReturn(1f);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        // Verify that blockbelow remains unaffected
        verify(physicalObject).setFalling(anyBoolean());
    }

    @Test
    public void testCheckPhysicalCollisionsY_BothConditionsFalse() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        // Mock the condition where both subconditions are false
        when(physicalObject.getX()).thenReturn(10f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(block.getX()).thenReturn(20f);
        when(block.getWidth()).thenReturn(2f);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        // Verify that blockbelow remains unaffected
        verify(physicalObject).setFalling(anyBoolean());
    }


    @Test
    public void testCheckPhysicalCollisionsY_BlockBelow() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        // Mock behavior for block below
        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(physicalObject.getY()).thenReturn(3f);

        when(block.getX()).thenReturn(4f);
        when(block.getWidth()).thenReturn(3f);
        when(block.getY()).thenReturn(5f);
        when(block.getHeight()).thenReturn(2f);

        when(physicalObject.isJumping()).thenReturn(false);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        verify(physicalObject).setFalling(false);
    }

    @Test
    public void testCheckPhysicalCollisionsY_JumpingUpwardCollision() {
        // Arrange
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject physicalObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);

        // Mock upward movement behavior
        when(physicalObject.getVy()).thenReturn(3f); // Simulate upward velocity
        when(physicalObject.isJumping()).thenReturn(true); // Simulate jumping state

        // Simulate position updates
        when(physicalObject.getY())
                .thenReturn(5f) // Initial position
                .thenReturn(4f) // After first movement
                .thenReturn(3f); // After second movement

        // Ensure collision occurs on the second movement
        when(physicalObject.collidesWith(object))
                .thenReturn(false) // No collision initially
                .thenReturn(true); // Collision occurs in second step

        // Act
        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(object));

        // Assert
        verify(physicalObject).handleCollision(object, 'U'); // Verify collision handling
        verify(physicalObject, times(2)).setY(anyFloat()); // Verify position updates
        verify(physicalObject, times(2)).collidesWith(object); // Verify collision checks
    }

    @Test
    public void testCheckPhysicalCollisionsY_BlockBelowAndNotJumping() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(physicalObject.getY()).thenReturn(3f);
        when(block.getX()).thenReturn(4f);
        when(block.getWidth()).thenReturn(3f);
        when(block.getY()).thenReturn(5f);
        when(block.getHeight()).thenReturn(2f);
        when(physicalObject.isJumping()).thenReturn(false);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        verify(physicalObject).setFalling(false);
    }

    @Test
    public void testCheckPhysicalCollisionsY_NoBlockBelowAndNotJumping() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(physicalObject.getY()).thenReturn(3f);
        when(block.getX()).thenReturn(4f);
        when(block.getWidth()).thenReturn(3f);
        when(block.getY()).thenReturn(7f); // No block below
        when(block.getHeight()).thenReturn(2f);
        when(physicalObject.isJumping()).thenReturn(false);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        verify(physicalObject).setFalling(true);
    }

    @Test
    public void testCheckPhysicalCollisionsY_BlockBelowAndJumping() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(physicalObject.getY()).thenReturn(3f);
        when(block.getX()).thenReturn(4f);
        when(block.getWidth()).thenReturn(3f);
        when(block.getY()).thenReturn(5f);
        when(block.getHeight()).thenReturn(2f);
        when(physicalObject.isJumping()).thenReturn(true); // Jumping

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        verify(physicalObject, never()).setFalling(anyBoolean());
    }

    @Test
    public void testCheckPhysicalCollisionsY_NoBlockBelowAndJumping() {
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        BuildingBlock block = mock(BuildingBlock.class);

        when(physicalObject.getX()).thenReturn(5f);
        when(physicalObject.getWidth()).thenReturn(2f);
        when(physicalObject.getY()).thenReturn(3f);
        when(block.getX()).thenReturn(4f);
        when(block.getWidth()).thenReturn(3f);
        when(block.getY()).thenReturn(7f); // No block below
        when(block.getHeight()).thenReturn(2f);
        when(physicalObject.isJumping()).thenReturn(true); // Jumping

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(block));

        verify(physicalObject, never()).setFalling(anyBoolean());
    }


    @Test
    public void testCheckPhysicalCollisionsY_FallingDownwardCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject physicalObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);

        // Mock behavior for falling
        when(physicalObject.getVy()).thenReturn(1f);
        when(physicalObject.isJumping()).thenReturn(false);
        when(physicalObject.getY()).thenReturn(5f);

        when(physicalObject.collidesWith(object)).thenReturn(true);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(object));

        verify(physicalObject).handleCollision(object, 'D');
    }

    @Test
    public void testCheckPhysicalCollisionsY_NoCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject physicalObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);

        // Mock behavior for no collision
        when(physicalObject.getVy()).thenReturn(1f);
        when(physicalObject.isJumping()).thenReturn(false);
        when(physicalObject.getY()).thenReturn(5f);

        when(physicalObject.collidesWith(object)).thenReturn(false);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(object));

        verify(physicalObject, never()).handleCollision(any(GameObject.class), anyChar());
    }

    @Test
    public void testCheckPhysicalCollisionsY_NoUpwardCollision() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject physicalObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);

        // Mock upward movement behavior
        when(physicalObject.getVy()).thenReturn(-3f); // Upward velocity
        when(physicalObject.isJumping()).thenReturn(true); // Simulate jumping state

        // Simulate position updates without collision
        when(physicalObject.getY()).thenReturn(5f); // Initial position
        lenient().when(physicalObject.collidesWith(object)).thenReturn(false); // No collision

        // Act
        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(object));

        // Assert
        verify(physicalObject).setY(5f); // Verify position reset to start
        verify(physicalObject, never()).handleCollision(any(GameObject.class), eq('U')); // No collision handling
    }


    @Test
    public void testCheckPhysicalCollisionsY_FallingState() {
        ControllerLevel controllerLevel = new ControllerLevel();

        PhysicalObject physicalObject = mock(PhysicalObject.class);
        GameObject object = mock(GameObject.class);

        // Mock behavior for falling state
        when(physicalObject.getVy()).thenReturn(0f);
        when(physicalObject.isJumping()).thenReturn(false);

        controllerLevel.CheckPhysicalCollisionsY(physicalObject, Collections.singletonList(object));

        verify(physicalObject).setFalling(true);
    }

    @Test
    public void testCheckCollisions() {
        Mario mario = mock(Mario.class);
        PhysicalObject physicalObject = mock(PhysicalObject.class);
        Camera camera = mock(Camera.class);
        List<GameObject> objects = Collections.singletonList(physicalObject);

        doNothing().when(controllerLevel).CheckPhysicalCollisionsX(mario, objects, camera);
        doNothing().when(controllerLevel).CheckPhysicalCollisionsY(mario, objects);

        controllerLevel.checkCollisions(mario, objects, camera);

        verify(controllerLevel).CheckPhysicalCollisionsX(mario, objects, camera);
        verify(controllerLevel).CheckPhysicalCollisionsY(mario, objects);
        verify(controllerLevel).CheckPhysicalCollisionsX(physicalObject, objects, camera);
        verify(controllerLevel).CheckPhysicalCollisionsY(physicalObject, objects);
    }

    @Test
    public void testCheckCollisionsStaticObject() {
        Mario mario = mock(Mario.class);
        StaticObject staticObject = mock(StaticObject.class);
        Camera camera = mock(Camera.class);
        List<GameObject> objects = Collections.singletonList(staticObject);

        doNothing().when(controllerLevel).CheckPhysicalCollisionsX(mario, objects, camera);
        doNothing().when(controllerLevel).CheckPhysicalCollisionsY(mario, objects);

        controllerLevel.checkCollisions(mario, objects, camera);

        verify(controllerLevel).CheckPhysicalCollisionsX(mario, objects, camera);
        verify(controllerLevel).CheckPhysicalCollisionsY(mario, objects);
    }
}
