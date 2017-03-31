package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityBlastFurnace;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBlastFurnace extends BlockGui{

	protected BlockBlastFurnace() {
		super(Material.IRON, "blastfurnace");
		setHardness(3.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlastFurnace();
	}

	@Override
	public int getGUIID() {
		return GuiHandler.GUI_BLAST_FURNACE;
	}

}
