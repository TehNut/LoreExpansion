package me.dmillerw.loreexpansion.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.dmillerw.loreexpansion.LoreExpansion;
import me.dmillerw.loreexpansion.json.JsonHelper;
import me.dmillerw.loreexpansion.json.data.Lore;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class LoreLoader {

    public static final Set<Lore> LOADED_LORE = Sets.newHashSet();

    public static Set<String> categories = Sets.newHashSet();
    private static Map<String, Lore> lore = Maps.newHashMap();
    private static Map<String, Set<Lore>> sortedLore = Maps.newHashMap();

    public static void init(File loreDir) {
        if (!loreDir.exists() && loreDir.mkdir()) {
            try {
                String json = JsonHelper.GSON.toJson(Lore.NULL_LORE);
                FileWriter writer = new FileWriter(new File(loreDir, "null.json"));
                writer.write(json);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Set<String> names = Sets.newHashSet();
        File[] jsonFiles = loreDir.listFiles((FileFilter) FileFilterUtils.suffixFileFilter(".json"));
        if (jsonFiles == null)
            return;

        try {
            for (File file : jsonFiles) {
                Lore lore = JsonHelper.GSON.fromJson(new FileReader(file), Lore.class);
                if (names.contains(lore.getId())) {
                    LoreExpansion.LOGGER.error("Duplicate Lore id: {}", lore.getId());
                } else {
                    LOADED_LORE.add(lore);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImmutableMap.Builder<String, Lore> loreBuilder = ImmutableMap.builder();
        ImmutableSet.Builder<String> categoryBuilder = ImmutableSet.builder();

        for (Lore lore : LOADED_LORE) {
            loreBuilder.put(lore.getId(), lore);
            categoryBuilder.add(lore.getCategory());
        }

        lore = loreBuilder.build();
        categories = categoryBuilder.build();

        ImmutableMap.Builder<String, Set<Lore>> sortedBuilder = ImmutableMap.builder();
        for (String category : categories) {
            Set<Lore> lores = Sets.newHashSet();
            for (Lore lore : LOADED_LORE)
                if (lore.getCategory().equalsIgnoreCase(category))
                    lores.add(lore);

            sortedBuilder.put(category, lores);
        }
        sortedLore = sortedBuilder.build();
    }

    public static Lore getLore(String key) {
        return lore.get(key);
    }

    public static Set<Lore> getLoreForCategory(String category) {
        return sortedLore.get(category) == null ? Collections.<Lore>emptySet() : sortedLore.get(category);
    }
}
