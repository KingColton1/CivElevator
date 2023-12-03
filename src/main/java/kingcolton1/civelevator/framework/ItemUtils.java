package kingcolton1.civelevator.framework;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Class of static APIs for Items. Replaces ISUtils.
 */
public final class ItemUtils {

    /**
     * @param item The item to get a translatable component for.
     * @return Returns a translatable component of the given item.
     */
    @Nonnull
    public static TranslatableComponent asTranslatable(@Nonnull final ItemStack item) {
        return Component.translatable(item.translationKey());
    }

    /**
     * Gets the name of an item based off a material, e.g: POLISHED_GRANITE to Polished Granite
     *
     * @param material The material to get the name of.
     * @return Returns the material name.
     *
     * @deprecated Use {@link MaterialUtils#asTranslatable(Material)} instead.
     */
    @Deprecated
    @Nonnull
    public static String getItemName(@Nonnull final Material material) {
        return material.toString();
        //return ChatUtils.stringify(MaterialUtils.asTranslatable(Objects.requireNonNull(material)));
    }

    /**
     * Gets the name of an item either based off its material or its custom item tag.
     *
     * @param item The item to get the name of.
     * @return Returns the item's name.
     *
     * @deprecated Use {@link #asTranslatable(ItemStack)} instead.
     */
    @Deprecated
    @Nullable
    public static String getItemName(@Nullable final ItemStack item) {
        return item == null ? null : item.toString();
        //return item == null ? null : ChatUtils.stringify(asTranslatable(item));
    }
}