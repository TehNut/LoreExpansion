package me.dmillerw.loreexpansion.proxy;

import me.dmillerw.loreexpansion.LoreExpansion;
import me.dmillerw.loreexpansion.core.LoreLoader;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

    public void preInit() {
        LoreLoader.init(LoreExpansion.loreDir, true);
    }

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(LoreExpansion.INSTANCE, new GuiHandler());
    }

    public void postInit() {

    }
}
