package org.grupogjl.model.game.elements.level;

import org.grupogjl.model.game.elements.camera.Camera;
import org.grupogjl.model.game.elements.generalobjects.GameObject;

import java.util.List;

public abstract class LevelBuilder {
    public Level createLevel() {
        Level level = new Level(getWidth(), getHeight());
        level.setObjects(createGameObjects());
        level.setCamera(new Camera());

        return level;
    }
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract List<GameObject> createGameObjects();
    protected abstract List<GameObject> connectPipes(List<GameObject> blocks);
}
