package org.grupogjl.controller.game.physicalobjects;

import org.grupogjl.gui.GeneralGui;
import org.grupogjl.model.game.elements.Mario;
import org.grupogjl.model.game.elements.blocks.DestroyableBlock;
import org.grupogjl.model.game.elements.blocks.Pipe;
import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.level.Level;
import org.grupogjl.model.game.elements.props.FireBall;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerMarioTest {

    @Spy
    @InjectMocks
    private ControllerMario controllerMario;

    @Mock
    private Level level;

    @Mock
    private Mario mario;

    @Mock
    private Camera camera;

    @Test
    public void testStep() {
        when(level.getMario()).thenReturn(mario);
        when(level.getCamera()).thenReturn(camera);

        controllerMario.step(level, GeneralGui.ACTION.RIGHT, 1000L);

        verify(controllerMario).updateMarioStatus(level);
        verify(controllerMario).moveMario(eq(GeneralGui.ACTION.RIGHT), eq(mario), anyList());
        verify(camera).updateCamera(mario);
    }

    @Test
    public void testUpdateMarioStatus_withCoinsForLife() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getCoins()).thenReturn(10);
        when(mario.getY()).thenReturn(0f);

        controllerMario.updateMarioStatus(level);

        verify(mario).setLives(anyInt());
        verify(mario).setCoins(0);
    }

    @Test
    public void testUpdateMarioStatus_marioFallsOffLevel() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getY()).thenReturn(20f); // Mario falls below the level height
        when(level.getHeight()).thenReturn(15);

        controllerMario.updateMarioStatus(level);

        verify(mario).reset();
    }

    @Test
    public void testUpdateMarioStatus_noCoinsForLife() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getCoins()).thenReturn(5); // Less than 10 coins
        when(mario.getY()).thenReturn(0f);

        controllerMario.updateMarioStatus(level);

        verify(mario, never()).setLives(anyInt());
        verify(mario, never()).setCoins(anyInt());
    }

    @Test
    public void testUpdateMarioStatus_invincibleTime() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getInvencibleTime()).thenReturn(5);
        when(mario.isStateInvencible()).thenReturn(true);

        controllerMario.updateMarioStatus(level);

        verify(mario).setInvencibleTime(4); // Time decrements
    }

    @Test
    public void testUpdateMarioStatus_noInvincibleTime() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getInvencibleTime()).thenReturn(0);
        when(mario.isStateInvencible()).thenReturn(true);

        controllerMario.updateMarioStatus(level);

        verify(mario).setStateInvencible(false);
    }

    @Test
    public void testMoveMario_jumpAction() {
        controllerMario.moveMario(GeneralGui.ACTION.UP, mario, new ArrayList<>());
        verify(mario).jump();
    }

    @Test
    public void testMoveMario_moveRightAction() {
        controllerMario.moveMario(GeneralGui.ACTION.RIGHT, mario, new ArrayList<>());
        verify(mario).moveRight();
    }

    @Test
    public void testMoveMario_moveLeftAction() {
        controllerMario.moveMario(GeneralGui.ACTION.LEFT, mario, new ArrayList<>());
        verify(mario).moveLeft();
    }

    @Test
    public void testMoveMario_noneAction() {
        when(mario.isJumping()).thenReturn(false);
        when(mario.isFalling()).thenReturn(false);

        controllerMario.moveMario(GeneralGui.ACTION.NONE, mario, new ArrayList<>());

        verify(mario).setVx(0);
    }

    @Test
    public void testMoveMario_noneActionJumping() {
        when(mario.isJumping()).thenReturn(true);
        lenient().when(mario.isFalling()).thenReturn(false);

        controllerMario.moveMario(GeneralGui.ACTION.NONE, mario, new ArrayList<>());

        verify(mario).setVx(mario.getVx() / 4);
    }

    @Test
    public void testMoveMario_noneAction_marioFalling() {
        when(mario.isJumping()).thenReturn(false);
        when(mario.isFalling()).thenReturn(true);

        controllerMario.moveMario(GeneralGui.ACTION.NONE, mario, new ArrayList<>());

        verify(mario).setVx(mario.getVx() / 4);
    }

    @Test
    public void testMoveMario_noneAction_marioFallingAndJumping() {
        when(mario.isJumping()).thenReturn(true);
        lenient().when(mario.isFalling()).thenReturn(true);

        controllerMario.moveMario(GeneralGui.ACTION.NONE, mario, new ArrayList<>());

        verify(mario).setVx(mario.getVx() / 4);
    }

    @Test
    public void testMoveMario_throwFireball() {
        List<GameObject> objects = new ArrayList<>();
        when(mario.isStateFire()).thenReturn(true);

        controllerMario.moveMario(GeneralGui.ACTION.THROWBALL, mario, objects);

        assertThat(objects).hasSize(1);
        assertThat(objects.getFirst()).isInstanceOf(FireBall.class);
    }

    @Test
    public void testMoveMario_throwFireballFalse() {
        List<GameObject> objects = new ArrayList<>();
        when(mario.isStateFire()).thenReturn(false);

        controllerMario.moveMario(GeneralGui.ACTION.THROWBALL, mario, objects);

        assertThat(objects).hasSize(0);
    }

    @Test
    public void testMoveMario_transportMario() {
        Pipe pipe = mock(Pipe.class);
        when(pipe.getConection()).thenReturn(pipe);
        when(pipe.getX()).thenReturn(1.0f);
        when(pipe.getY()).thenReturn(2.0f);
        when(pipe.getHeight()).thenReturn(1.0f);

        List<GameObject> objects = new ArrayList<>();
        objects.add(pipe);

        when(mario.getX()).thenReturn(1.0f);
        when(mario.getY()).thenReturn(1.0f);

        controllerMario.moveMario(GeneralGui.ACTION.DOWN, mario, objects);

        verify(mario).setX(pipe.getX());
        verify(mario).setY(pipe.getY() - pipe.getHeight());
    }

    @Test
    public void testMoveMario_noTransportMario() {
        Pipe pipe = mock(Pipe.class);
        lenient().when(pipe.getConection()).thenReturn(pipe);
        when(pipe.getX()).thenReturn(10.0f);
        lenient().when(pipe.getY()).thenReturn(20.0f);
        lenient().when(pipe.getHeight()).thenReturn(1.0f);

        List<GameObject> objects = new ArrayList<>();
        objects.add(pipe);

        lenient().when(mario.getX()).thenReturn(1.0f);
        lenient().when(mario.getY()).thenReturn(1.0f);

        controllerMario.moveMario(GeneralGui.ACTION.DOWN, mario, objects);

        verify(mario, never()).setX(anyFloat());
        verify(mario, never()).setY(anyFloat());
    }

    @Test
    public void testUpdateMarioStatus_noConditionsMet() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getCoins()).thenReturn(5); // Less than 10 coins
        when(mario.getY()).thenReturn(5f); // Above level height
        when(level.getHeight()).thenReturn(10);
        when(mario.getInvencibleTime()).thenReturn(1);
        when(mario.isStateInvencible()).thenReturn(false);

        controllerMario.updateMarioStatus(level);

        verify(mario, never()).reset();
        verify(mario, never()).setLives(anyInt());
        verify(mario, never()).setCoins(anyInt());
        verify(mario, never()).setStateInvencible(anyBoolean());
    }

    @Test
    public void testMoveMario_defaultCase() {
        controllerMario.moveMario(GeneralGui.ACTION.SELECT, mario, new ArrayList<>());

        // Verify no action is taken
        verify(mario, never()).jump();
        verify(mario, never()).moveRight();
        verify(mario, never()).moveLeft();
        verify(mario, never()).setVx(anyFloat());
    }

    @Test
    public void testTransportMario_noActionWhenNotAbovePipe() {
        Pipe pipe = mock(Pipe.class);
        lenient().when(pipe.getConection()).thenReturn(pipe);
        when(pipe.getX()).thenReturn(10.0f);
        lenient().when(pipe.getY()).thenReturn(20.0f);
        lenient().when(pipe.getHeight()).thenReturn(1.0f);

        List<GameObject> objects = new ArrayList<>();
        objects.add(pipe);

        when(mario.getX()).thenReturn(1.0f);
        lenient().when(mario.getY()).thenReturn(1.0f);

        controllerMario.transportMario(mario, objects);

        verify(mario, never()).setX(anyFloat());
        verify(mario, never()).setY(anyFloat());
    }

    @Test
    public void testTransportMario_objectNotPipe() {
        DestroyableBlock pipe = mock(DestroyableBlock.class);
        lenient().when(pipe.getX()).thenReturn(10.0f);
        lenient().when(pipe.getY()).thenReturn(20.0f);
        lenient().when(pipe.getHeight()).thenReturn(1.0f);

        List<GameObject> objects = new ArrayList<>();
        objects.add(pipe);

        lenient().when(mario.getX()).thenReturn(1.0f);
        lenient().when(mario.getY()).thenReturn(1.0f);

        controllerMario.transportMario(mario, objects);

        verify(mario, never()).setX(anyFloat());
        verify(mario, never()).setY(anyFloat());
    }

    @Test
    public void testTransportMario_notAbovePipe() {
        Pipe pipe = mock(Pipe.class);
        lenient().when(pipe.getConection()).thenReturn(pipe);
        lenient().when(pipe.getX()).thenReturn(1.5f);
        lenient().when(pipe.getY()).thenReturn(20f);
        lenient().when(pipe.getHeight()).thenReturn(1.0f);

        List<GameObject> objects = new ArrayList<>();
        objects.add(pipe);

        lenient().when(mario.getX()).thenReturn(1.0f);
        lenient().when(mario.getY()).thenReturn(1.0f);

        controllerMario.transportMario(mario, objects);

        verify(mario, never()).setX(anyFloat());
        verify(mario, never()).setY(anyFloat());
    }

    @Test
    public void testUpdateMarioStatus_hitCooldownTrue() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getInvencibleTime()).thenReturn(0);
        when(mario.isStateInvencible()).thenReturn(true);
        when(mario.isHitCooldown()).thenReturn(true);

        controllerMario.updateMarioStatus(level);

        verify(mario).setHitCooldown(false);
        verify(mario).setStateInvencible(false);
    }

    @Test
    public void testUpdateMarioStatus_hitCooldownFalse() {
        when(level.getMario()).thenReturn(mario);
        when(mario.getInvencibleTime()).thenReturn(0);
        when(mario.isStateInvencible()).thenReturn(true);
        when(mario.isHitCooldown()).thenReturn(false);

        controllerMario.updateMarioStatus(level);

        verify(mario, never()).setHitCooldown(false);
        verify(mario).setStateInvencible(false);
    }


}
