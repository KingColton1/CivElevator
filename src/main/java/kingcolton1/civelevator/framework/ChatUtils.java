package kingcolton1.civelevator.framework;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ChatUtils {

    /**
     * This is necessary as {@link ChatColor#values()} has all colours <i>and</i> all formats.
     *
     * @deprecated Use {@link NamedTextColor} instead.
     */
    @Deprecated
    public static final List<ChatColor> COLOURS = List.of(
            ChatColor.BLACK,
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_RED,
            ChatColor.DARK_PURPLE,
            ChatColor.GOLD,
            ChatColor.GRAY,
            ChatColor.DARK_GRAY,
            ChatColor.BLUE,
            ChatColor.GREEN,
            ChatColor.AQUA,
            ChatColor.RED,
            ChatColor.LIGHT_PURPLE,
            ChatColor.YELLOW);

    /**
     * Converts an RGB value into a Bungee ChatColor.
     *
     * @param r The red value.
     * @param g The green value.
     * @param b The blue value.
     * @return Returns a valid Bungee ChatColor.
     *
     * @deprecated Use {@link net.kyori.adventure.text.format.TextColor#color(int, int, int)} instead.
     */
    @Nonnull
    @Deprecated
    public static ChatColor fromRGB(final byte r, final byte g, final byte b) {
        return ChatColor.of(new Color(r, g, b));
    }

    /**
     * Attempts to collapse an RGB colour to established Minecraft colours.
     *
     * @param colour The given RGB colour.
     * @return Returns the closest Minecraft match, or null.
     */
    @Contract("!null -> !null")
    @Nullable
    public static ChatColor collapseColour(@Nullable final ChatColor colour) {
        if (colour == null) {
            return null;
        }
        final Color color = colour.getColor();
        ChatColor nearestColour = null;
        double nearestDistance = Double.MAX_VALUE;
        for (final ChatColor currentColour : COLOURS) {
            final Color currentColor = currentColour.getColor();
            final double distance = Math.sqrt(
                    Math.pow(color.getRed() - currentColor.getRed(), 2)
                            - Math.pow(color.getGreen() - currentColor.getGreen(), 2)
                            - Math.pow(color.getBlue() - currentColor.getBlue(), 2));
            if (nearestDistance > distance) {
                nearestDistance = distance;
                nearestColour = currentColour;
            }
        }
        return nearestColour;
    }

    // -------------------------------------------- //
    // Color parsing
    // -------------------------------------------- //

    /**
     * @deprecated Please use MiniMessage instead.
     * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
     */
    @Nonnull
    @Deprecated
    public static String parseColor(@Nonnull String string) {
        string = parseColorAmp(string);
        string = parseColorAcc(string);
        string = parseColorTags(string);
        return string;
    }

    /**
     * @deprecated Please use MiniMessage instead.
     * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
     */
    @Nonnull
    @Deprecated
    public static String parseColorAmp(@Nonnull String string) {
        string = string.replace("&&", "&");
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * @deprecated Please use MiniMessage instead.
     * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
     */
    @Nonnull
    @Deprecated
    public static String parseColorAcc(@Nonnull String string) {
        return ChatColor.translateAlternateColorCodes('`', string);
    }

    /**
     * @deprecated Please use MiniMessage instead.
     * <a href="https://docs.adventure.kyori.net/minimessage.html">Read More</a>.
     */
    @Nonnull
    @Deprecated
    public static String parseColorTags(@Nonnull String string) {
        return string
                .replace("<black>", ChatColor.BLACK.toString())
                .replace("<dblue>", ChatColor.DARK_BLUE.toString())
                .replace("<dgreen>", ChatColor.DARK_GREEN.toString())
                .replace("<daqua>", ChatColor.DARK_AQUA.toString())
                .replace("<dred>", ChatColor.DARK_RED.toString())
                .replace("<dpurple>", ChatColor.DARK_PURPLE.toString())
                .replace("<gold>", ChatColor.GOLD.toString())
                .replace("<lgray>", ChatColor.GRAY.toString()) // This has to be lgray because gray is already claimed.
                .replace("<dgray>", ChatColor.DARK_GRAY.toString())
                .replace("<blue>", ChatColor.BLUE.toString())
                .replace("<green>", ChatColor.GREEN.toString())
                .replace("<aqua>", ChatColor.AQUA.toString())
                .replace("<red>", ChatColor.RED.toString())
                .replace("<lpurple>", ChatColor.LIGHT_PURPLE.toString())
                .replace("<yellow>", ChatColor.YELLOW.toString())
                .replace("<white>", ChatColor.WHITE.toString())
                .replace("<s>", ChatColor.STRIKETHROUGH.toString())
                .replace("<u>", ChatColor.UNDERLINE.toString())
                .replace("<ul>", ChatColor.UNDERLINE.toString())
                .replace("<r>", ChatColor.RESET.toString())
                .replace("<strike>", ChatColor.STRIKETHROUGH.toString())
                .replace("<italic>", ChatColor.ITALIC.toString())
                .replace("<bold>", ChatColor.BOLD.toString())
                .replace("<reset>", ChatColor.RESET.toString())
                // Legacy support
                .replace("<empty>", "") // Just... why?
                .replace("<navy>", ChatColor.DARK_BLUE.toString())
                .replace("<teal>", ChatColor.DARK_AQUA.toString())
                .replace("<silver>", ChatColor.GRAY.toString())
                .replace("<gray>", ChatColor.DARK_GRAY.toString()) // REEE why name this gray?
                .replace("<lime>", ChatColor.GREEN.toString())
                .replace("<rose>", ChatColor.RED.toString())
                .replace("<pink>", ChatColor.LIGHT_PURPLE.toString())
                .replace("<it>", ChatColor.ITALIC.toString())
                .replace("<g>", ChatColor.GREEN.toString()) // Good
                .replace("<b>", ChatColor.RED.toString()) // Bad
                .replace("<i>", ChatColor.WHITE.toString()) // Info
                .replace("<a>", ChatColor.GOLD.toString()) // Art
                .replace("<l>", ChatColor.GREEN.toString()) // Logo
                .replace("<n>", ChatColor.GRAY.toString()) // Notice
                .replace("<h>", ChatColor.LIGHT_PURPLE.toString()) // Highlight
                .replace("<c>", ChatColor.AQUA.toString()) // Parameter
                .replace("<p>", ChatColor.DARK_AQUA.toString()) // Parameter
                .replace("<w>", ChatColor.WHITE.toString()) // Parameter
                .replace("<lp>", ChatColor.LIGHT_PURPLE.toString());
    }

    // -------------------------------------------- //
    // Component Stuff
    // -------------------------------------------- //

    /**
     * <p>Determines whether a given base component is null or empty.</p>
     *
     * <p>This is determined by converting the component into plain text, so a non-null component filled with
     * nothing but colour codes and hover text will likely return true.</p>
     *
     * @param component The component to test if null or empty.
     * @return Returns true if the component is null or has no visible content.
     *
     * @deprecated Has been deprecated due to Paper's move to Kyori's Adventure.
     */
    public static boolean isNullOrEmpty(@Nullable final Component component) {
        if (component == null || component == Component.empty()) {
            return true;
        }
        return StringUtils.isBlank(PlainTextComponentSerializer.plainText().serialize(component));
    }

    /**
     * <p>Determines whether a given base component is null or empty.</p>
     *
     * <p>This is determined by converting the component into plain text, so a non-null component filled with
     * nothing but colour codes and hover text will likely return true.</p>
     *
     * @param component The component to test if null or empty.
     * @return Returns true if the component is null or has no visible content.
     */
    public static boolean isBaseComponent(@Nullable final Component component) {
        if (component == null) {
            return false;
        }
        return (!(component instanceof TextComponent textComponent) || StringUtils.isEmpty(textComponent.content()))
                && !component.children().isEmpty()
                && component.clickEvent() == null
                && component.hoverEvent() == null
                && !component.hasStyling();
    }

    private static final Map<TextDecoration, TextDecoration.State> NORMALISED_DECORATION_MAP =
            Map.of(TextDecoration.ITALIC, TextDecoration.State.FALSE);

    /**
     * Checks whether a given component is the result of {@link #normaliseComponent(Component...)} or
     *
     * @param component The component to check.
     * @return Returns true if the given component is "normalised."
     */
    public static boolean isNormalisedComponent(@Nullable final Component component) {
        if (!(component instanceof final TextComponent textComponent)) {
            return false;
        }
        return StringUtils.isEmpty(textComponent.content())
                && !component.children().isEmpty()
                && component.clickEvent() == null
                && component.hoverEvent() == null
                && Objects.equals(component.color(), NamedTextColor.WHITE)
                && Objects.equals(component.decorations(), NORMALISED_DECORATION_MAP);
    }

    /**
     * Wraps a component or series of components into a "normalised" display component, meaning that the text is
     * white and non-italic by default.
     *
     * @param components The component / components to wrap.
     * @return Returns the normalised component, or empty if no components are passed.
     */
    @Nonnull
    public static Component normaliseComponent(final Component... components) {
        if (ArrayUtils.isEmpty(components)) {
            return Component.empty();
        }
        return Component.text()
                .color(NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                .append(components)
                .build();
    }

    /**
     * This will also happily translate any translatable components.
     *
     * @param component The component to stringify.
     * @return Returns a stringified version of the given component.
     */
    @Nonnull
    public static String stringify(@Nullable final Component component) {
        return component.toString();
    }

    /**
     * Upgrades a legacy string (eg: §6Hello, World!) to a Kyori component.
     *
     * @param string The string to convert into a component.
     * @return Returns a new component, or null if the given string was null.
     */
    @Contract("!null -> !null")
    @Nullable
    public static Component upgradeLegacyString(@Nullable final String string) {
        return string == null ? null : string.isEmpty() ? Component.empty() :
                LegacyComponentSerializer.legacySection().deserialize(string);
    }
}