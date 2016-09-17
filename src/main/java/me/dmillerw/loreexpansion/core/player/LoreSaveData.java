package me.dmillerw.loreexpansion.core.player;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import me.dmillerw.loreexpansion.LoreExpansion;
import me.dmillerw.loreexpansion.core.data.LoreKey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LoreSaveData extends WorldSavedData {

    public static final ResourceLocation LORE_DATA_ID = new ResourceLocation(LoreExpansion.ID, "playerData");

    private Map<UUID, Set<LoreKey>> playerData = new HashMap<UUID, Set<LoreKey>>();

    public LoreSaveData() {
        super(LORE_DATA_ID.toString());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        NBTTagList entries = tag.getTagList("playerData", 10);
        for (int i = 0; i < entries.tagCount(); i++) {
            NBTTagCompound loreTag = entries.getCompoundTagAt(i);
            UUID uuid = UUID.fromString(loreTag.getString("uuid"));
            Set<LoreKey> mapValue = Sets.newHashSet();
            NBTTagList loreEntries = loreTag.getTagList("loreEntries", 10);
            for (int k = 0; k < loreEntries.tagCount(); k++)
                mapValue.add(LoreKey.deserialize(loreEntries.getCompoundTagAt(k)));

            playerData.put(uuid, mapValue);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        NBTTagList entries = new NBTTagList();
        for (Map.Entry<UUID, Set<LoreKey>> entry : playerData.entrySet()) {
            NBTTagCompound loreTag = new NBTTagCompound();
            loreTag.setString("uuid", entry.getKey().toString());
            NBTTagList loreEntries = new NBTTagList();
            for (LoreKey loreKey : entry.getValue())
                loreEntries.appendTag(loreKey.serialize());
            loreTag.setTag("loreEntries", loreEntries);

            entries.appendTag(loreTag);
        }

        tag.setTag("playerData", entries);

        return tag;
    }

    public Set<LoreKey> getDataForPlayer(EntityPlayer player) {
        UUID uuid = player.getGameProfile().getId();
        if (!playerData.containsKey(uuid))
            initPlayer(player);
        return playerData.get(uuid);
    }

    public void initPlayer(EntityPlayer player) {
        UUID playerId = player.getGameProfile().getId();
        if (!playerData.containsKey(playerId))
            playerData.put(playerId, Sets.<LoreKey>newHashSet());
    }

    public Map<UUID, Set<LoreKey>> getPlayerData() {
        return ImmutableMap.copyOf(playerData);
    }
}
