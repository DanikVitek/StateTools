package com.danikvitek.statetools;

import com.danikvitek.statetools.utils.CustomConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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

        getCommand("statetool").setExecutor(new StateToolCommand());
        Bukkit.getPluginManager().registerEvents(new InteractionsEventListener(), this);

        InteractionsEventListener.paintBrushRunnable.runTaskTimer(this, 0L, 1L);
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
}
