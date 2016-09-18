package me.dmillerw.loreexpansion.proxy;

import me.dmillerw.loreexpansion.LoreExpansion;
import me.dmillerw.loreexpansion.client.gui.GuiJournal;
import me.dmillerw.loreexpansion.network.MessageSyncLore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0)
            LoreExpansion.NETWORK_WRAPPER.sendTo(new MessageSyncLore((EntityPlayerMP) player), (EntityPlayerMP) player);
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiJournal(id == 1);
    }
}
