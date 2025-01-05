package org.grupogjl.model.game.elements.generalobjects;

import org.grupogjl.model.game.elements.camera.Camera;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StaticObjectTest {

    @Test
    public void testStaticObjectSettersAndGetters() {
        StaticObject staticObject = new StaticObject(0, 0, 10, 10) {
            @Override
            public String getImage() {
                return "staticImage";
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

        staticObject.setX(5);
        staticObject.setY(5);
        staticObject.setWidth(15);
        staticObject.setHeight(20);

        assertThat(staticObject.getX()).isEqualTo(5);
        assertThat(staticObject.getY()).isEqualTo(5);
        assertThat(staticObject.getWidth()).isEqualTo(15);
        assertThat(staticObject.getHeight()).isEqualTo(20);
    }
}