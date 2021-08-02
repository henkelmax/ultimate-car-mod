package de.maxhenkel.tools;

import net.minecraft.core.BlockPos;

import java.util.LinkedList;
import java.util.List;

public class BlockPosList {

    private List<BlockPos> positions;

    public BlockPosList() {
        this.positions = new LinkedList<>();
    }

    public boolean contains(BlockPos pos) {
        return positions.contains(pos);
    }

    public void add(BlockPos pos) {
        if (!contains(pos)) {
            positions.add(pos);
        }
    }

    @Override
    public String toString() {
        return positions.toString();
    }
}
