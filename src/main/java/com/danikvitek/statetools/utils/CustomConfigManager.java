package com.danikvitek.statetools.utils;

import com.danikvitek.statetools.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CustomConfigManager {
    private final Main main;
    private File customConfigFile;
    private FileConfiguration modifyCustomConfigFile;
    private final String fileName;

    public CustomConfigManager(Main main, String fileName) {
        this.main = main;
        this.fileName = fileName;
        customConfigFile = null;
        modifyCustomConfigFile = null;
    }

    public void reloadCustomConfig() {
        if (customConfigFile == null)
            customConfigFile = new File(main.getDataFolder(), fileName);
        modifyCustomConfigFile = YamlConfiguration.loadConfiguration(customConfigFile);

        // Look for defaults in the jar
        Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource(fileName)), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        modifyCustomConfigFile.setDefaults(defConfig);
    }

    public FileConfiguration getCustomConfig() {
        if (modifyCustomConfigFile == null) {
            reloadCustomConfig();
        }
        return modifyCustomConfigFile;
    }

    public void saveCustomConfig() {
        if (modifyCustomConfigFile == null || customConfigFile == null) {
            return;
        }
        try {
            getCustomConfig().save(customConfigFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (customConfigFile == null) {
            customConfigFile = new File(main.getDataFolder(), fileName);
        }
        if (!customConfigFile.exists()) {
            main.saveResource(fileName, false);
        }
    }

    public File getCustomConfigFile() {
        return customConfigFile;
    }
}
