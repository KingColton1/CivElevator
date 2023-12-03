package kingcolton1.civelevator.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class config {
    private static File file;
    private static FileConfiguration configFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("CivElevator").getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                System.out.println("Failed to create a new CivElevator config file");
            }
        }

        configFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return configFile;
    }

    public static void save() {
        try {
            configFile.save(file);
        } catch (IOException ioe) {
            System.out.println("Failed to save CivElevator config file");
        }
    }

    public static void reload() {
        configFile = YamlConfiguration.loadConfiguration(file);
    }
}
