package de.maxhenkel.tools;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Rotation {

    private List<AxisRotation> rotations;

    public Rotation(float angle, Vector3f axis) {
        this();
        add(angle, axis);
    }

    public Rotation() {
        rotations = new ArrayList<>();
    }

    public Rotation add(float angle, Vector3f axis) {
        rotations.add(new AxisRotation(angle, axis));
        return this;
    }

    private class AxisRotation {
        private float angle;
        private Vector3f axis;

        public AxisRotation(float angle, Vector3f axis) {
            this.angle = angle;
            this.axis = axis;
        }
    }

    public void applyRotation(MatrixStack matrixStack) {
        for (AxisRotation rotation : rotations) {
            matrixStack.func_227863_a_(rotation.axis.func_229187_a_(rotation.angle));
        }
    }

}
