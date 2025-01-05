package org.grupogjl.model.game.elements.generalobjects;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhysicalObjectTest {

    @Mock
    private StaticObject staticObjectMock;

    @InjectMocks
    private PhysicalObject physicalObject = new PhysicalObject(0, 0, 10, 10) {
        @Override
        public void handleCollision(GameObject object, char r) {
            // Provide a dummy implementation for abstract method
        }

        @Override
        public void handleWallColision(float leftCamLimit) {
            // Provide a dummy implementation for abstract method
        }

        @Override
        public String getImage() {
            return "dummyImage";
        }

        @Override
        public float getVirtX(Camera camera) {
            return getX();
        }

        @Override
        public float getVirtY() {
            return getY();
        }
    };

    @Test
    public void testUpdateLocation_JumpingOnly() {
        physicalObject.setJumping(true);
        physicalObject.setVy(1.0f); // Starting vertical velocity
        physicalObject.setFalling(false);

        physicalObject.updateLocation();

        float expectedVy = 1.0f - 0.23f; // vy decreases by gravity
        float expectedY = 0.0f - 1.0f; // y decreases by previous vy

        assertThat(physicalObject.getVy()).isEqualTo(expectedVy);
        assertThat(physicalObject.getY()).isEqualTo(expectedY);
        assertThat(physicalObject.isJumping()).isTrue(); // Still jumping (vy > 0)
        assertThat(physicalObject.isFalling()).isFalse();
    }

    @Test
    public void testUpdateLocation_FallingOnly() {
        physicalObject.setJumping(false);
        physicalObject.setFalling(true);
        physicalObject.setVy(1.0f); // Starting vertical velocity

        physicalObject.updateLocation();

        float expectedVy = Math.min(1.0f + 0.23f, 1.4f); // vy increases with gravity, capped at 1.4
        float expectedY = 0.0f + 1.0f; // y increases by previous vy

        assertThat(physicalObject.getVy()).isEqualTo(expectedVy);
        assertThat(physicalObject.getY()).isEqualTo(expectedY);
        assertThat(physicalObject.isJumping()).isFalse();
        assertThat(physicalObject.isFalling()).isTrue(); // Still falling
    }

    @Test
    public void testUpdateLocation_FallingOnly_Vy15() {
        physicalObject.setJumping(false);
        physicalObject.setFalling(true);
        physicalObject.setVy(1.5f); // Starting vertical velocity

        physicalObject.updateLocation();

        assertThat(physicalObject.getVy()).isEqualTo(1.4f);
        assertThat(physicalObject.getY()).isEqualTo(1.5f);
        assertThat(physicalObject.isJumping()).isFalse();
        assertThat(physicalObject.isFalling()).isTrue(); // Still falling
    }

    @Test
    public void testUpdateLocation_JumpingEndsAndTransitionToFalling() {
        physicalObject.setJumping(true);
        physicalObject.setVy(0.1f); // Vertical velocity close to zero
        physicalObject.setFalling(false);

        physicalObject.updateLocation();

        float expectedY = 0.0f - 0.1f; // y decreases by previous vy

        assertThat(physicalObject.getVy()).isEqualTo(-0.13f); // vy reset to 0
        assertThat(physicalObject.getY()).isEqualTo(expectedY);
        assertThat(physicalObject.isJumping()).isFalse(); // Jumping ends
        assertThat(physicalObject.isFalling()).isTrue(); // Falling begins
    }


    @Test
    public void testUpdateLocation_NotJumpingOrFalling() {
        physicalObject.setJumping(false);
        physicalObject.setFalling(false);
        physicalObject.setVy(1.0f);
        physicalObject.updateLocation();
        assertThat(physicalObject.getVy()).isZero();
    }

    @Test
    public void testCollidesWithStatic_AllConditionsTrue() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to make both xOverlap and verticalOverlap true
        when(physicalObject.getX()).thenReturn(5.0f);
        when(physicalObject.getWidth()).thenReturn(2.0f);
        when(physicalObject.getY()).thenReturn(5.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f);

        when(staticObject.getX()).thenReturn(6.0f);
        when(staticObject.getWidth()).thenReturn(2.0f);
        when(staticObject.getY()).thenReturn(5.0f);
        when(staticObject.getHeight()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isTrue(); // Collision should occur
    }

    @Test
    public void testCollidesWithStatic_xOverlapFalse() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to make xOverlap false
        when(physicalObject.getX()).thenReturn(1.0f); // Too far left
        when(physicalObject.getWidth()).thenReturn(2.0f);
        when(physicalObject.getY()).thenReturn(5.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f);

        when(staticObject.getX()).thenReturn(6.0f);
        when(staticObject.getWidth()).thenReturn(2.0f);
        when(staticObject.getY()).thenReturn(5.0f);
        when(staticObject.getHeight()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No collision
    }

    @Test
    public void testCollidesWithStatic_verticalOverlapFalse() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to make verticalOverlap false
        when(physicalObject.getX()).thenReturn(5.0f);
        when(physicalObject.getWidth()).thenReturn(2.0f);
        when(physicalObject.getY()).thenReturn(10.0f); // Too far above
        when(physicalObject.getHeight()).thenReturn(2.0f);

        when(staticObject.getX()).thenReturn(6.0f);
        when(staticObject.getWidth()).thenReturn(2.0f);
        when(staticObject.getY()).thenReturn(5.0f);
        when(staticObject.getHeight()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No collision
    }

    @Test
    public void testCollidesWithStatic_NoOverlap() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to make both xOverlap and verticalOverlap false
        when(physicalObject.getX()).thenReturn(1.0f); // Too far left
        when(physicalObject.getWidth()).thenReturn(2.0f);
        when(physicalObject.getY()).thenReturn(10.0f); // Too far above
        when(physicalObject.getHeight()).thenReturn(2.0f);

        when(staticObject.getX()).thenReturn(6.0f);
        when(staticObject.getWidth()).thenReturn(2.0f);
        when(staticObject.getY()).thenReturn(5.0f);
        when(staticObject.getHeight()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No collision
    }

    @Test
    public void testCollidesWithStatic_OnlyXOverlapTrue() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to make only xOverlap true
        when(physicalObject.getX()).thenReturn(5.0f);
        when(physicalObject.getWidth()).thenReturn(2.0f);
        when(physicalObject.getY()).thenReturn(10.0f); // Out of vertical range
        when(physicalObject.getHeight()).thenReturn(2.0f);

        when(staticObject.getX()).thenReturn(6.0f);
        when(staticObject.getWidth()).thenReturn(2.0f);
        when(staticObject.getY()).thenReturn(5.0f);
        when(staticObject.getHeight()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No collision
    }

    @Test
    public void testCollidesWithStatic_OnlyVerticalOverlapTrue() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to make only verticalOverlap true
        when(physicalObject.getX()).thenReturn(1.0f); // Out of horizontal range
        when(physicalObject.getWidth()).thenReturn(2.0f);
        when(physicalObject.getY()).thenReturn(5.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f);

        when(staticObject.getX()).thenReturn(6.0f);
        when(staticObject.getWidth()).thenReturn(2.0f);
        when(staticObject.getY()).thenReturn(5.0f);
        when(staticObject.getHeight()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No collision
    }

    @Test
    public void testCollidesWithStatic_ThisTopLessThanOtherBottom_ThisBottomNotGreaterThanOtherTop() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to ensure only thisTop < otherBottom is true
        when(physicalObject.getY()).thenReturn(5.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f); // Top at 3.0, Bottom at 5.0

        when(staticObject.getY()).thenReturn(8.0f);
        when(staticObject.getHeight()).thenReturn(2.0f); // Top at 6.0, Bottom at 8.0

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // Vertical overlap should be false
    }

    @Test
    public void testCollidesWithStatic_ThisTopNotLessThanOtherBottom_ThisBottomGreaterThanOtherTop() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to ensure only thisBottom > otherTop is true
        when(physicalObject.getY()).thenReturn(2.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f); // Top at 0.0, Bottom at 2.0

        when(staticObject.getY()).thenReturn(1.0f);
        when(staticObject.getHeight()).thenReturn(1.0f); // Top at 0.0, Bottom at 1.0

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // Vertical overlap should be false
    }

    @Test
    public void testCollidesWithStatic_NoVerticalOverlap() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to ensure neither thisTop < otherBottom nor thisBottom > otherTop is true
        when(physicalObject.getY()).thenReturn(5.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f); // Top at 3.0, Bottom at 5.0

        when(staticObject.getY()).thenReturn(9.0f);
        when(staticObject.getHeight()).thenReturn(2.0f); // Top at 7.0, Bottom at 9.0

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // Vertical overlap should be false
    }


    @Test
    public void testCollidesWithStatic_XOverlapBoundaryCase() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to test xOverlap boundary condition
        when(physicalObject.getX()).thenReturn(5.0f);
        when(physicalObject.getWidth()).thenReturn(2.0f); // Ends exactly at 7.0

        when(staticObject.getX()).thenReturn(7.0f); // Starts exactly at 7.0
        when(staticObject.getWidth()).thenReturn(2.0f);

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No overlap
    }

    @Test
    public void testCollidesWithStatic_VerticalOverlapBoundaryCase() {
        PhysicalObject physicalObject = mock(PhysicalObject.class, CALLS_REAL_METHODS);
        StaticObject staticObject = mock(StaticObject.class);

        // Mock positions to test verticalOverlap boundary condition
        when(physicalObject.getY()).thenReturn(5.0f);
        when(physicalObject.getHeight()).thenReturn(2.0f); // Bottom at 5.0, top at 3.0

        when(staticObject.getY()).thenReturn(3.0f); // Bottom at 3.0
        when(staticObject.getHeight()).thenReturn(2.0f); // Top at 1.0

        boolean result = physicalObject.collidesWithStatic(staticObject);

        assertThat(result).isFalse(); // No overlap
    }

    @Test
    public void testCollidesWithStatic() {
        when(staticObjectMock.getX()).thenReturn(5.0f);
        when(staticObjectMock.getY()).thenReturn(5.0f);
        when(staticObjectMock.getWidth()).thenReturn(10.0f);
        when(staticObjectMock.getHeight()).thenReturn(10.0f);

        boolean result = physicalObject.collidesWithStatic(staticObjectMock);

        assertThat(result).isTrue();
    }

    @Test
    public void testCollidesWithPhysical() {
        PhysicalObject otherPhysicalObject = new PhysicalObject(5, 5, 10, 10) {
            @Override
            public void handleCollision(GameObject object, char r) {
            }

            @Override
            public void handleWallColision(float leftCamLimit) {
            }

            @Override
            public String getImage() {
                return "otherDummyImage";
            }

            @Override
            public float getVirtX(Camera camera) {
                return getX();
            }

            @Override
            public float getVirtY() {
                return getY();
            }
        };

        boolean result = physicalObject.collidesWithPhysical(otherPhysicalObject, 0, 0);

        assertThat(result).isTrue();
    }

    @Test
    public void testCollidesWith_StaticObject() {
        StaticObject mockObject = mock(StaticObject.class);
        when(mockObject.getX()).thenReturn(5.0f);
        when(mockObject.getY()).thenReturn(5.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(10.0f);

        boolean result = physicalObject.collidesWith(mockObject);

        assertThat(result).isTrue();
    }

    @Test
    public void testCollidesWith_PhysicalObject() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(5.0f);
        when(mockObject.getY()).thenReturn(5.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(10.0f);

        boolean result = physicalObject.collidesWith(mockObject);

        assertThat(result).isTrue();
    }

    @Test
    public void testCollidesWith_PhysicalObjectXOverlapTrueTrue() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(5.0f);
        when(mockObject.getY()).thenReturn(5.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(10.0f);

        boolean result = physicalObject.collidesWithPhysical(mockObject, 2.0f, 2.0f);

        assertThat(result).isTrue();
    }

    @Test
    public void testCollidesWith_PhysicalObjectXOverlapTrueFalse() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(15.0f);
        when(mockObject.getY()).thenReturn(5.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(10.0f);

        boolean result = physicalObject.collidesWithPhysical(mockObject, 2.0f, 2.0f);

        assertThat(result).isFalse();
    }

    @Test
    public void testCollidesWith_PhysicalObjectXOverlapFalseTrue() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(-15.0f);
        when(mockObject.getY()).thenReturn(5.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(10.0f);

        boolean result = physicalObject.collidesWithPhysical(mockObject, 2.0f, 2.0f);

        assertThat(result).isFalse();
    }

    @Test
    public void testCollidesWith_PhysicalObjectVerticalOverlapTrueFalse() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(-15.0f);
        when(mockObject.getY()).thenReturn(5.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(0.0f);

        boolean result = physicalObject.collidesWithPhysical(mockObject, 2.0f, 2.0f);

        assertThat(result).isFalse();
    }

    @Test
    public void testCollidesWith_PhysicalObjectVerticalOverlapFalseTrue() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(-15.0f);
        when(mockObject.getY()).thenReturn(-15.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(-20.0f);

        boolean result = physicalObject.collidesWithPhysical(mockObject, 2.0f, 2.0f);

        assertThat(result).isFalse();
    }

    @Test
    public void testCollidesWith_PhysicalObjectTrueFalse() {
        PhysicalObject mockObject = mock(PhysicalObject.class);
        when(mockObject.getX()).thenReturn(5.0f);
        when(mockObject.getY()).thenReturn(-15.0f);
        when(mockObject.getWidth()).thenReturn(10.0f);
        when(mockObject.getHeight()).thenReturn(-20.0f);

        boolean result = physicalObject.collidesWithPhysical(mockObject, 2.0f, 2.0f);

        assertThat(result).isFalse();
    }

    @Test
    public void testSettersAndGetters() {
        physicalObject.setX(10);
        physicalObject.setY(20);
        physicalObject.setWidth(30);
        physicalObject.setHeight(40);
        physicalObject.setVx(50);
        physicalObject.setVy(60);
        physicalObject.setG(0.5f);
        physicalObject.setFalling(true);
        physicalObject.setJumping(true);

        assertThat(physicalObject.getX()).isEqualTo(10);
        assertThat(physicalObject.getY()).isEqualTo(20);
        assertThat(physicalObject.getWidth()).isEqualTo(30);
        assertThat(physicalObject.getHeight()).isEqualTo(40);
        assertThat(physicalObject.getVx()).isEqualTo(50);
        assertThat(physicalObject.getVy()).isEqualTo(60);
        assertThat(physicalObject.getG()).isEqualTo(0.5f);
        assertThat(physicalObject.isFalling()).isTrue();
        assertThat(physicalObject.isJumping()).isTrue();
    }
}
