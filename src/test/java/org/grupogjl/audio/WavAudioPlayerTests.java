package org.grupogjl.audio;

import org.grupogjl.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WavAudioPlayerTests {

    private Clip mockClip;
    private AudioInputStream mockAudioInputStream;
    private InputStream mockInputStream;
    private CountDownLatch latch;

    @BeforeEach
    void setUp() {
        // Create mocks for Clip, AudioInputStream, and InputStream
        mockClip = mock(Clip.class);
        mockAudioInputStream = mock(AudioInputStream.class);
        mockInputStream = mock(InputStream.class);

        // Use a CountDownLatch to handle synchronization with the thread
        latch = new CountDownLatch(1);
    }

    @Test
    void testPlaySoundSuccessfully() {
        // Mock the static methods of AudioSystem and Main
        try (MockedStatic<AudioSystem> audioSystemMock = Mockito.mockStatic(AudioSystem.class);
             MockedStatic<Main> mainMock = Mockito.mockStatic(Main.class)) {

            // Mock the behavior of AudioSystem.getClip()
            audioSystemMock.when(AudioSystem::getClip).thenReturn(mockClip);

            // Instead of a lambda, use a direct method reference
            // Mock the behavior of AudioSystem.getAudioInputStream using matchers
            audioSystemMock.when(() -> AudioSystem.getAudioInputStream(Mockito.any(InputStream.class)))
                    .thenReturn(mockAudioInputStream);

            // Mock the behavior of Main.class.getResourceAsStream to return the mockInputStream
            mainMock.when(() -> Main.class.getResourceAsStream("/Audio/gameSound.wav")).thenReturn(mockInputStream);

            // Wrap the playSound call in a Runnable that decrements the latch when complete
            Thread testThread = new Thread(() -> {
                WavAudioPlayer.playSound("gameSound.wav");
                latch.countDown();
            });

            // Start the test thread
            testThread.start();
            latch.await(); // Wait for the thread to finish

            // Verify that the clip was opened and started
            verify(mockClip, timeout(1000)).open(mockAudioInputStream);
            verify(mockClip, timeout(1000)).start();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception should not be thrown in successful case");
        }
    }

    @Test
    void testPlaySoundWithException() {
        // Mock the static methods of AudioSystem
        try (MockedStatic<AudioSystem> audioSystemMock = Mockito.mockStatic(AudioSystem.class)) {
            // Mock the behavior of AudioSystem.getClip() to throw an exception
            audioSystemMock.when(AudioSystem::getClip).thenThrow(new RuntimeException("Test Exception"));

            // Capture system error output
            PrintStream originalErr = System.err;
            ByteArrayOutputStream errContent = new ByteArrayOutputStream();
            System.setErr(new PrintStream(errContent));

            // Wrap the playSound call in a Runnable that decrements the latch when complete
            Thread testThread = new Thread(() -> {
                WavAudioPlayer.playSound("gameSound.wav");
                latch.countDown();
            });

            // Start the test thread
            testThread.start();
            latch.await(); // Wait for the thread to finish

            // Restore the original System.err
            System.setErr(originalErr);

            // Verify that the error message was printed
            String expectedErrorMessage = "Test Exception";
            assertTrue(errContent.toString().contains(expectedErrorMessage));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception should not be thrown in this case");
        }
    }
}