package org.grupogjl.model.game.elements.level;

import org.grupogjl.model.game.elements.blocks.*;
import org.grupogjl.model.game.elements.enemies.Goomba;
import org.grupogjl.model.game.elements.enemies.KoopaTroopa;
import org.grupogjl.model.game.elements.generalobjects.GameObject;
import org.grupogjl.model.game.elements.surprises.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoaderLevelBuilder extends LevelBuilder {
    private final List<String> lines;
    public LoaderLevelBuilder(int leveln) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                LoaderLevelBuilder.class.getResourceAsStream("/Levels/level" + leveln + ".lvl"), StandardCharsets.UTF_8))) {
            lines = readLines(br);
        }
    }
    public List<String> readLines(BufferedReader br) throws IOException {
        List<String> Lines = new ArrayList<>();
        for (String line; (line = br.readLine()) != null; ) {
            Lines.add(line);
        }
        return Lines;
    }
    @Override
    public int getWidth() {
        int width = 0;
        for (String line : lines) {
            width = Math.max(width, line.length());
        }
        return width;
    }
    @Override
    public int getHeight() {
        return lines.size();
    }
    @Override
    public List<GameObject> createGameObjects() {
        List<GameObject> objects = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char character = line.charAt(x);
                switch (character) {
                    case '#':
                        objects.add(new UnbreakableBlock(x, y, 1, 1));
                        break;
                    case '$':
                        objects.add(new DestroyableBlock(x, y, 1, 1));
                        break;
                    case '+':
                        objects.add(new GoalBlock(x, y, 1, 10));
                        break;
                    case '?':
                        SurpriseBlock surpriseBlock = new SurpriseBlock(x, y, 1, 1);
                        Random random = new Random();
                        int randomNumber = random.nextInt(5); // Change 3 to the number of different surprises you have
                        switch (randomNumber) {
                            case 0:
                                surpriseBlock.setSurprise(new Mushroom1UP(x, y));
                                break;
                            case 1:
                                surpriseBlock.setSurprise(new Coin(x, y));
                                break;
                            case 2:
                                surpriseBlock.setSurprise(new MushroomSuper(x, y));
                                break;
                            case 3:
                                surpriseBlock.setSurprise(new Flower(x, y));
                                break;
                            case 4:
                                surpriseBlock.setSurprise(new Star(x, y));
                                break;
                        }
                        objects.add(surpriseBlock);
                        break;
                    case 'u', 'v', 'w', 'x', 'y', 'z':
                        objects.add(new Pipe(x, y, 2, 2 + (character - 'u')));
                        break;
                    case 'p':
                        objects.add(new KoopaTroopa(x, y, 1, 1));
                        break;
                    case 'g':
                        objects.add(new Goomba(x, y, 1, 1));
                        break;
                }
            }
        }
        objects = connectPipes(objects);
        return objects;
    }
    @Override
    public List<GameObject> connectPipes(List<GameObject> objects) {
        List<GameObject> connectedObjects = new ArrayList<>();
        List<GameObject> normalObjects = new ArrayList<>();
        List<Pipe> pipes = new ArrayList<>();
        // Separate pipes from other blocks
        for (GameObject object : objects) {
            if (object instanceof Pipe) {
                pipes.add((Pipe) object);
            } else {
                normalObjects.add(object);
            }
        }
        // Connect pairs of pipes
        for (int i = 0; i < pipes.size(); i += 2) {
            int previndex = i;
            int nextindex = i + 1;
            pipes.get(previndex).setConection(pipes.get(nextindex));
            pipes.get(nextindex).setConection(pipes.get(nextindex));
            // Print console output with corrected indexing
            // Add connected pipes to the result list
            connectedObjects.add(pipes.get(previndex));
            connectedObjects.add(pipes.get(nextindex));
        }
        for (int i = 0; i < normalObjects.size(); i++) {
            connectedObjects.add(normalObjects.get(i));
        }
        return connectedObjects;
    }
}
