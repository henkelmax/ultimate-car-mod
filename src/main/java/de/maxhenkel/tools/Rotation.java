package de.maxhenkel.tools;

import com.mojang.blaze3d.platform.GlStateManager;

import java.util.ArrayList;
import java.util.List;

public class Rotation {

    private List<AxisRotation> rotations;

    public Rotation(float angle, float x, float y, float z) {
        this();
        add(angle, x, y, z);
    }

    public Rotation() {
        rotations = new ArrayList<>();
    }

    public Rotation add(float angle, float x, float y, float z) {
        rotations.add(new AxisRotation(angle, x, y, z));
        return this;
    }

    private class AxisRotation {
        private float angle;
        private float x, y, z;

        public AxisRotation(float angle, float x, float y, float z) {
            this.angle = angle;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public void applyGLRotation() {
        for (AxisRotation rotation : rotations) {
            GlStateManager.rotatef(rotation.angle, rotation.x, rotation.y, rotation.z);
        }
    }

}
