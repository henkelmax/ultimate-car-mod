package de.maxhenkel.car.blocks.tileentity;

import javax.annotation.Nullable;
import de.maxhenkel.car.blocks.BlockOrientableHorizontal;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TileEntitySignCar extends TileEntity {

	public final ITextComponent[] signText = new ITextComponent[] { new TextComponentString(""),
			new TextComponentString(""), new TextComponentString(""), new TextComponentString("") };

	public TileEntitySignCar() {
		
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		for (int i = 0; i < 4; ++i) {
			String s = ITextComponent.Serializer.componentToJson(this.signText[i]);
			compound.setString("Text" + (i + 1), s);
		}

		return compound;
	}

	protected void setWorldCreate(World worldIn) {
		this.setWorldObj(worldIn);
	}

	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		for (int i = 0; i < 4; ++i) {
			String s = compound.getString("Text" + (i + 1));
			signText[i] = ITextComponent.Serializer.jsonToComponent(s);
		}
	}

	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 9, getUpdateTag());// ?
	}

	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	public EnumFacing getDirection(){
		IBlockState state=worldObj.getBlockState(pos);
		
		if(state.getBlock().equals(ModBlocks.SIGN)){
			return state.getValue(BlockOrientableHorizontal.FACING);
		}
		
		return EnumFacing.NORTH;
	}

}
