package alchemyplusplus.proxy.client;

import alchemyplusplus.proxy.CommonProxy;
import alchemyplusplus.block.complex.diffuser.DiffuserBlockRender;
import alchemyplusplus.block.complex.distillery.DistilleryBlockRender;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerBlockRender;
import alchemyplusplus.block.complex.potionJug.PotionJugBlockRender;
import alchemyplusplus.block.complex.diffuser.DiffuserTileEntity;
import alchemyplusplus.block.complex.distillery.DistilleryTileEntity;
import alchemyplusplus.block.complex.fluidMixer.FluidMixerTileEntity;
import alchemyplusplus.block.complex.potionJug.PotionJugTileEntity;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ClientProxy extends CommonProxy
{

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(PotionJugTileEntity.class, new PotionJugBlockRender());
        ClientRegistry.bindTileEntitySpecialRenderer(DistilleryTileEntity.class, new DistilleryBlockRender());
        ClientRegistry.bindTileEntitySpecialRenderer(DiffuserTileEntity.class, new DiffuserBlockRender());
        ClientRegistry.bindTileEntitySpecialRenderer(FluidMixerTileEntity.class, new FluidMixerBlockRender());
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        return Minecraft.getMinecraft().thePlayer;
    }
}
