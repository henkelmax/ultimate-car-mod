package de.maxhenkel.tools;


import net.minecraft.client.renderer.Quaternion;

public class QuaternionWrapper {

    private float x, y, z, w;
    private Quaternion quaternion;

    public QuaternionWrapper(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion getQuaternion() {
        if (quaternion == null) {
            quaternion = new Quaternion(x, y, z, w);
        }
        return quaternion;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }
}
