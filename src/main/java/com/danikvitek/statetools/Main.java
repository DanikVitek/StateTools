package com.danikvitek.statetools;

import com.danikvitek.statetools.utils.CustomConfigManager;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static YamlConfiguration modifyTranslationsFile;

    public static YamlConfiguration getModifyTranslationsFile() {
        return modifyTranslationsFile;
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        initFiles();

        Objects.requireNonNull(getCommand("statetool")).setExecutor(new StateToolCommand());
        Bukkit.getPluginManager().registerEvents(new InteractionsEventListener(), this);

        long period = getConfig().getLong("check_inventory_period", 2L);

        InteractionsEventListener.paintBrushRunnable.runTaskTimer(this, 0L, period);
    }

    @Override
    public void onDisable() {
        if (!InteractionsEventListener.paintBrushRunnable.isCancelled())
            InteractionsEventListener.paintBrushRunnable.cancel();
    }

    private void initFiles() {
        CustomConfigManager translationsConfig = new CustomConfigManager(this, "translations.yml");
        translationsConfig.reloadCustomConfig();
        translationsConfig.saveDefaultConfig();
        modifyTranslationsFile = (YamlConfiguration) translationsConfig.getCustomConfig();
    }

    public static CoreProtectAPI getCoreProtect() {
        Plugin plugin = Main.getPlugin(Main.class).getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (!CoreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 7) {
            return null;
        }

        return CoreProtect;
    }
}
