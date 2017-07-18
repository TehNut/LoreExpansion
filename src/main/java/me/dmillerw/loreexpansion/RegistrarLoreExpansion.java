package me.dmillerw.loreexpansion;

import me.dmillerw.loreexpansion.item.ItemJournal;
import me.dmillerw.loreexpansion.item.ItemScrap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(LoreExpansion.ID)
public class RegistrarLoreExpansion {

    @GameRegistry.ObjectHolder("lore_journal")
    public static final ItemJournal JOURNAL = new ItemJournal();
    @GameRegistry.ObjectHolder("lore_scrap")
    public static final ItemScrap SCRAP = new ItemScrap();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemJournal().setRegistryName("lore_journal"));
        event.getRegistry().register(new ItemScrap().setRegistryName("lore_scrap"));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(JOURNAL, 0, new ModelResourceLocation(JOURNAL.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JOURNAL, 1, new ModelResourceLocation(JOURNAL.getRegistryName().toString() + "_creative", "inventory"));
        ModelLoader.setCustomModelResourceLocation(SCRAP, 0, new ModelResourceLocation(SCRAP.getRegistryName(), "inventory"));
    }
}
