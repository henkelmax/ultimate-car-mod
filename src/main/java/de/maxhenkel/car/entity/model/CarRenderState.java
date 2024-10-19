package de.maxhenkel.car.entity.model;

import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.joml.Vector3d;

import java.util.List;

public class CarRenderState extends EntityRenderState {

    public List<OBJModelInstance<CarRenderState>> models;
    public String licensePlate;
    public Vector3d licensePlateOffset;
    public float yRot;

}
