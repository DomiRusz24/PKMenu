package me.domirusz24.pkmenu.manager.magic;

import lombok.Getter;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import net.md_5.bungee.api.ChatColor;

import java.util.Optional;

public class MockElement implements MenuElement {
    @Override
    public String getId() {
        return "unknown";
    }

    @Override
    public String getName() {
        return "Unknown";
    }

    @Override
    public ChatColor getPrimary() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ChatColor getSecondary() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public Optional<String> getParentElement() {
        return Optional.empty();
    }
}
