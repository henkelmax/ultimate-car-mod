package de.maxhenkel.car.blocks.tileentity;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.Config;
import de.maxhenkel.tools.FluidUtils;
import de.maxhenkel.car.blocks.BlockFluidExtractor;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityFluidExtractor extends TileEntityBase implements ITickable{

	private IFluidHandler extractHandler;
	
	private final int drainSpeed;
	
	private ItemStack filter;
	
	public TileEntityFluidExtractor() {
		this.drainSpeed=Config.fluidExtractorDrainSpeed;
		filter=null;
	}
	
	@Nullable
	public Fluid getFilterFluid(){
		if(filter==null){
			return null;
		}
		
		FluidStack stack=FluidUtil.getFluidContained(filter);
		
		if(stack==null||stack.amount<=0){
			return null;
		}
		
		return stack.getFluid();
	}

	@Override
	public void update() {
		if(world.isRemote){
			return;
		}
		updateExtractHandler();
		
		if(extractHandler==null){
			return;
		}
		
		FluidStack drainSimulated;
		if(getFilterFluid()==null){
			drainSimulated=extractHandler.drain(drainSpeed, false);
		}else{
			drainSimulated=extractHandler.drain(new FluidStack(getFilterFluid(), drainSpeed), false);
		}
		
		if(drainSimulated==null||drainSimulated.amount<=0){
			return;
		}
		
		List<IFluidHandler> handlers=new LinkedList<IFluidHandler>();
		
		getConnectedHandlers(handlers, new BlockPosList(), pos);
		
		List<IFluidHandler> fillHandlers=new LinkedList<IFluidHandler>();

		for(IFluidHandler handler:handlers){
			int amount=handler.fill(drainSimulated, false);
			if(amount>0){
				fillHandlers.add(handler);
			}
		}
		
		if(fillHandlers.isEmpty()){
			return;
		}
		
		for(IFluidHandler handler:fillHandlers){
			if(getFilterFluid()==null){
				FluidUtil.tryFluidTransfer(handler, extractHandler, drainSpeed, true);
			}else{
				FluidUtils.tryFluidTransfer(handler, extractHandler, drainSpeed, true, getFilterFluid());
			}
		}
	}
	
	public void updateExtractHandler(){
		IBlockState state=world.getBlockState(pos);
		
		if(!state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR)){
			extractHandler=null;
			return;
		}
		
		EnumFacing side=state.getValue(BlockFluidExtractor.FACING);
		
		TileEntity te=world.getTileEntity(pos.offset(side));
		
		if(!(te instanceof IFluidHandler)){
			extractHandler = null;
			return;
		}
		
		extractHandler=(IFluidHandler) te;
	}
	
	public void getConnectedHandlers(List<IFluidHandler> handlers, BlockPosList positions, BlockPos pos){
		for(EnumFacing side:EnumFacing.values()){
			BlockPos p=pos.offset(side);
			
			if(positions.contains(p)){
				continue;
			}
			
			positions.add(p);
			
			IBlockState state=world.getBlockState(p);
			
			if(state.getBlock().equals(ModBlocks.FLUID_PIPE)||state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR)){
				getConnectedHandlers(handlers, positions, p);
				continue;
			}
			
			TileEntity te=world.getTileEntity(p);
			
			if(!(te instanceof IFluidHandler)){
				continue;
			}
			
			IFluidHandler handler=(IFluidHandler) te;
			
			if(handler.equals(extractHandler)){
				continue;
			}
			
			handlers.add(handler);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		if(filter!=null){
			NBTTagCompound tag = new NBTTagCompound();
			filter.writeToNBT(tag);
			compound.setTag("filter", tag);
		}
		
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		if(compound.hasKey("filter")){
			NBTTagCompound tag = compound.getCompoundTag("filter");
			filter=new ItemStack(tag);
		}else{
			filter=null;
		}
		
		super.readFromNBT(compound);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.fluid_extractor.name");
	}

	public ItemStack getFilter() {
		if(filter==null){
			return null;
		}
		return filter.copy();
	}

	public void setFilter(ItemStack filter) {
		if(filter==null){
			this.filter=null;
			markDirty();
			synchronize();
			return;
		}
		this.filter = filter.copy();
		markDirty();
		synchronize();
	}
	
}
