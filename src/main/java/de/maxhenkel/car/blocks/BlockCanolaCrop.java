package de.maxhenkel.car.blocks;

public class BlockCanolaCrop extends BlockCrop {


    //TODO block loot table
    public BlockCanolaCrop() {
        super("canola");
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

}
