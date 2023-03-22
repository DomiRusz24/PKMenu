package me.domirusz24.pkmenu.manager.magic;

import me.domirusz24.ext.util.placeholders.PlaceholderObject;
import net.md_5.bungee.api.ChatColor;

import java.util.Optional;

public interface MenuElement extends PlaceholderObject {
    String getId();
    String getName();
    ChatColor getPrimary();
    ChatColor getSecondary();

    default ChatColor getSecondarySafe() {
        if (getSecondary() == null) {
            return getPrimary();
        }
        return getSecondary();
    }
    Optional<String> getParentElement();

    @Override
    default String onPlaceholderRequest(String s) {
        return switch (s.toLowerCase()) {
            case "name" -> getName();
            case "primary" -> getPrimary().toString();
            case "secondary" -> getSecondary().toString();
            default -> null;
        };
    }

    @Override
    default String placeHolderPrefix() {
        return "element";
    }
}
