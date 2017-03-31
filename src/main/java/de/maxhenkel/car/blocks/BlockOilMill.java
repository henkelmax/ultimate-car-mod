package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityOilMill;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockOilMill extends BlockGui{

	protected BlockOilMill() {
		super(Material.IRON, "oilmill");
		setHardness(3.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityOilMill();
	}

	@Override
	public int getGUIID() {
		return GuiHandler.GUI_OIL_MILL;
	}
}
