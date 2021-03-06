package me.dmillerw.loreexpansion.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class GeneralUtil {

    public static void giveStackToPlayer(EntityPlayer player, ItemStack stack) {
        if (player.inventory.addItemStackToInventory(stack)) {
            player.getEntityWorld().playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.inventoryContainer.detectAndSendChanges();
        } else {
            EntityItem entityitem = player.dropItem(stack, false);
            if (entityitem != null) {
                entityitem.setNoPickupDelay();
                entityitem.setOwner(player.getName());
            }
        }
    }

    @Nonnull
    public static File extractZip(File zip) {
        String zipPath = zip.getParent() + "/" + FilenameUtils.getBaseName(zip.getName());
        File temp = new File(zipPath);
        temp.mkdir();

        ZipFile zipFile = null;

        try {
            zipFile = new ZipFile(zip);

            // get an enumeration of the ZIP file entries
            Enumeration<? extends ZipEntry> e = zipFile.entries();

            while (e.hasMoreElements()) {
                ZipEntry entry = e.nextElement();

                File destinationPath = new File(zipPath, entry.getName());

                // create parent directories
                destinationPath.getParentFile().mkdirs();

                // if the entry is a file extract it
                if (entry.isDirectory()) {
                    continue;
                } else {
                    BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));

                    int b;
                    byte buffer[] = new byte[1024];

                    FileOutputStream fos = new FileOutputStream(destinationPath);

                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

                    while ((b = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, b);
                    }

                    bos.close();
                    bis.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
                zip.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return temp;
    }

    public static NBTTagCompound getModPersistedTag(EntityPlayer player, String modid) {
        NBTTagCompound tag = player.getEntityData();

        NBTTagCompound persistTag;
        if (tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
            persistTag = tag.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        else
            tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistTag = new NBTTagCompound());

        NBTTagCompound modTag;
        if (persistTag.hasKey(modid))
            modTag = persistTag.getCompoundTag(modid);
        else
            persistTag.setTag(modid, modTag = new NBTTagCompound());

        return modTag;
    }
}
