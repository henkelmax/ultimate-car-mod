package de.maxhenkel.car.entity.model.obj;

public class OBJModelInstance {

    private OBJModel model;
    private OBJModelOptions options;

    public OBJModelInstance(OBJModel model, OBJModelOptions options) {
        this.model = model;
        this.options = options;
    }

    public OBJModelInstance(OBJModel model) {
        this(model, new OBJModelOptions());
    }

    public OBJModel getModel() {
        return model;
    }

    public OBJModelOptions getOptions() {
        return options;
    }
}
