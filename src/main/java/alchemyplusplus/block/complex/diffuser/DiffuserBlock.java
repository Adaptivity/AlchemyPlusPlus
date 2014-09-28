package alchemyplusplus.block.complex.diffuser;

import alchemyplusplus.block.BlockComplex;
import alchemyplusplus.reference.Naming;
import alchemyplusplus.utility.NotificationManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public class DiffuserBlock extends BlockComplex
{
	public DiffuserBlock()
	{
		super(Material.wood, Naming.Blocks.DIFFUSER, Block.soundTypeWood);
		this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.8F, 0.8F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new DiffuserTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		DiffuserTileEntity diffuser = (DiffuserTileEntity) world.getTileEntity(x, y, z);

		if (player.getHeldItem() != null && !ItemPotion.isSplash(player.getHeldItem().getItemDamage()) && player.getHeldItem().getItem() == Items.potionitem)
		{
			if (diffuser.fluidTank.getFluidAmount() < diffuser.fluidTank.getCapacity())
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.pour");
				}

				if (!player.capabilities.isCreativeMode && !player.isSneaking())
				{
					player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.glass_bottle);
				}

				if (!diffuser.isDiffuserActive())
				{
					diffuser.toggleDiffusingState();
				}
			} else
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.full");
				}
			}
		} else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.glass_bottle)
		{

			if (diffuser.fluidTank.getFluidAmount() > 0)
			{

				// Allow creative mode players to override this by sneaking
				if (!player.capabilities.isCreativeMode)
				{
					//player.inventory.mainInventory[player.inventory.currentItem] = diffuser;
				}
				// Wiping the diffuser data
				diffuser.setBottleColorValue(0);
				if (diffuser.isDiffuserActive())
				{
					diffuser.toggleDiffusingState();
				}

			} else
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.bottle.refill.failure");
				}
			}
		} else if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.potionitem && player.getHeldItem().getItemDamage() == 0)
		{
			if (!player.capabilities.isCreativeMode)
			{
				player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.glass_bottle);
			}
			diffuser.fluidTank = new FluidTank(0);

			diffuser.setBottleColorValue(0);
			if (diffuser.isDiffuserActive())
			{
				diffuser.toggleDiffusingState();
			}
			if (!world.isRemote)
			{
				NotificationManager.sendChatMessage(player, "diffuser.wash.success");
			}

		} else if (diffuser.canDiffuse() || diffuser.isDiffuserActive())
		{
			if (diffuser.isDiffuserActive())
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.cork");
				}
			} else
			{
				if (!world.isRemote)
				{
					NotificationManager.sendChatMessage(player, "diffuser.uncork");
				}
			}
			diffuser.toggleDiffusingState();

		} else
		{
			if (!world.isRemote)
			{
				NotificationManager.sendChatMessage(player, "diffuser.uncork.failure");
			}

		}

		return false;
	}
}
