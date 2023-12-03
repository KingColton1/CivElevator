package kingcolton1.civelevator;

import kingcolton1.civelevator.config.config;
import kingcolton1.civelevator.function.Listener;
import org.bukkit.plugin.java.JavaPlugin;
public final class CivElevator extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        // Setup config
        config.setup();
        config.get().addDefault("ELEVATOR MATERIAL", "GOLD_BLOCK");
        config.get().addDefault("SOUND MATERIAL", "ENTITY_ENDERMAN_TELEPORT");
        config.get().options().copyDefaults(true);
        config.save();

        getServer().getPluginManager().registerEvents(new Listener(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
