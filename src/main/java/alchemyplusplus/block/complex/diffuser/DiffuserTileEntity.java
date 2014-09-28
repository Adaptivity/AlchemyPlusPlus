package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.network.MessageHandler;
import alchemyplusplus.network.message.DiffuserUpdateMessage;
import alchemyplusplus.reference.Settings;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public class DiffuserTileEntity extends TileEntity implements IFluidHandler, IFluidTank
{
	private boolean updateState;
	public boolean isDiffusing;
	public int bottleColor;
	public int diffusingTicks;
	public FluidTank fluidTank;

	public DiffuserTileEntity()
	{
		this.bottleColor = 0;
		this.diffusingTicks = 0;
		this.isDiffusing = false;
		this.updateState = false;
		this.fluidTank = new FluidTank((int) 333);
	}

	public boolean canDiffuse()
	{
		if (this.fluidTank.getFluidAmount() > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		this.writeToNBT(new NBTTagCompound());
		return MessageHandler.INSTANCE.getPacketFrom(new DiffuserUpdateMessage(this));
	}

	public boolean isDiffuserActive()
	{
		return isDiffusing;
	}

	public void setBottleColorValue(int bottleColor)
	{
		this.bottleColor = bottleColor;
	}

	public void toggleDiffusingState()
	{
		if (this.fluidTank.getFluidAmount() > 0)
		{
			isDiffusing = !isDiffusing;
		} else
		{
			isDiffusing = false;
		}
		if (Settings.DebugMode)
		{
			System.err.println("Fluid level:" + this.fluidTank.getFluidAmount());
			System.err.println("Diffusing: " + isDiffusing);
		}
		this.updateState = true;
	}

	public void setDiffusingState(boolean value)
	{
		this.isDiffusing = value;
		this.updateState = true;
	}

	@Override
	public void updateEntity()
	{
		if (this.updateState)
		{
			MessageHandler.INSTANCE.sendToAllAround(new DiffuserUpdateMessage(this), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 20));
			this.markDirty();
			this.updateState = false;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if (this.fluidTank != null)
		{
			nbt.setTag("diffuserTank", this.fluidTank.writeToNBT(new NBTTagCompound()));
		}

		nbt.setShort("diffusingTicks", (short) this.diffusingTicks);
		nbt.setInteger("bottleColor", this.bottleColor);
		nbt.setBoolean("isDiffusing", this.isDiffusing);

	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.diffusingTicks = nbt.getShort("diffusingTicks");

		this.isDiffusing = nbt.getBoolean("isDiffusing");

		this.bottleColor = nbt.getInteger("bottleColor");

		if (this.fluidTank != null)
		{
			this.fluidTank.readFromNBT(nbt.getCompoundTag("diffuserTank"));
		}
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		if (resource != null)
		{
			return this.fluidTank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		if (resource != null)
		{
			return this.fluidTank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if (maxDrain > 0)
		{
			return this.fluidTank.drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		if (resource != null)
		{
			return this.fluidTank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return this.fluidTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		if (this.fluidTank != null && this.fluidTank.getFluidAmount() < this.fluidTank.getCapacity())
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		if (this.fluidTank != null && this.fluidTank.getFluidAmount() > 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public FluidStack getFluid()
	{
		if (this.fluidTank != null)
		{
			return this.fluidTank.getFluid();
		}
		return null;

	}

	@Override
	public int getFluidAmount()
	{

		if (this.fluidTank != null)
		{
			return this.fluidTank.getFluidAmount();
		}
		return 0;
	}

	@Override
	public int getCapacity()
	{

		if (this.fluidTank != null)
		{
			return this.fluidTank.getCapacity();
		}
		return 0;
	}

	@Override
	public FluidTankInfo getInfo()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
