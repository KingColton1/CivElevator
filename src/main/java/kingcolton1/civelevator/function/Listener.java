package kingcolton1.civelevator.function;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import kingcolton1.civelevator.config.config;
import kingcolton1.civelevator.framework.AutoLoad;
import kingcolton1.civelevator.framework.DataParser;
import kingcolton1.civelevator.framework.DoubleInteractFixer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.block.Action;

public class Listener implements org.bukkit.event.Listener {
    @AutoLoad(processor = DataParser.MATERIAL)
    private final Material elevatorBlock = getMaterialName();
    private final Sound elevatorSound = getSoundName();

    private DoubleInteractFixer interactFixer;

    private Material getMaterialName() {
        String materialName = config.get().getString("ELEVATOR MATERIAL");

        if (materialName != null) {
            if (Material.matchMaterial(materialName) != null) {
                return Material.matchMaterial(materialName);
            }
        }
        return Material.GOLD_BLOCK;
    }

    private Sound getSoundName() {
        String soundName = config.get().getString("ELEVATOR SOUND");

        if (soundName != null) {
            return Sound.valueOf(soundName.toUpperCase());
        }
        return Sound.ENTITY_ENDERMAN_TELEPORT;
    }

    private boolean doTeleport(Block source, Player player, int y) {
        Block target = source.getWorld().getBlockAt(source.getX(), y, source.getZ());
        if (target.getType() != elevatorBlock) {
            return false;
        }
        if (!TeleportUtil.checkForTeleportSpace(target.getRelative(BlockFace.UP).getLocation())) {
            return false;
        }
        source.getWorld().playSound(source.getLocation(), elevatorSound, 1.0F, 8.0F);
        Location adjustedLocation = target.getLocation().clone();
        adjustedLocation.add(0.5, 1.02, 0.5);
        adjustedLocation.setYaw(player.getLocation().getYaw());
        adjustedLocation.setPitch(player.getLocation().getPitch());
        player.playSound(adjustedLocation, elevatorSound, 1.0F, 8.0F);
        player.teleport(adjustedLocation);
        return true;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void elevatorBlockSneak(PlayerToggleSneakEvent eventSneak) {
        Block below = eventSneak.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock) {
            return;
        }
        if (!eventSneak.isSneaking()) {
            return;
        }
        for (int y = (below.getY() - 1); y > below.getWorld().getMinHeight(); y--) {
            if (doTeleport(below, eventSneak.getPlayer(), y)) {
                return;
            }
        }

        String message = String.format("No %s block to teleport you down to. Jump to teleport up instead.", this.elevatorBlock);

        eventSneak.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void elevatorBlockJump(PlayerJumpEvent eventJump) {
        Block below = eventJump.getFrom().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock) {
            return;
        }
        for (int y = below.getY() + 1; y <= below.getWorld().getMaxHeight(); y++) {
            if (doTeleport(below, eventJump.getPlayer(), y)) {
                return;
            }
        }

        String message = String.format("No %s block to teleport you up to. Sneak to teleport down instead.", this.elevatorBlock);

        eventJump.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void elevatorBlockInteract(PlayerInteractEvent eventInteract) {
        if (eventInteract.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (interactFixer.checkInteracted(eventInteract.getPlayer(), eventInteract.getClickedBlock())) {
				return;
			}
		} else if (eventInteract.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}

        Block below = eventInteract.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != elevatorBlock) {
			return;
		}
		if (eventInteract.getClickedBlock() == null) {
			return;
		}
		if (!eventInteract.getClickedBlock().equals(below)) {
			return;
		}
		if (eventInteract.getAction() == Action.LEFT_CLICK_BLOCK) {
			for (int y = below.getY() - 1; y > below.getWorld().getMinHeight(); y--) {
				if (doTeleport(below, eventInteract.getPlayer(), y)) {
					return;
				}
			}

			String message = String.format("No %s block to teleport you down to. Right click to teleport up instead.", this.elevatorBlock);
			eventInteract.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
		} else {
			for (int y = below.getY() + 1; y <= below.getWorld().getMaxHeight(); y++) {
				if (doTeleport(below, eventInteract.getPlayer(), y)) {
					return;
				}
			}

			String message = String.format("No %s block to teleport you up to. Left click to teleport down instead.", this.elevatorBlock);
			eventInteract.getPlayer().sendMessage(Component.text(message).color(NamedTextColor.RED));
		}
    }
}
