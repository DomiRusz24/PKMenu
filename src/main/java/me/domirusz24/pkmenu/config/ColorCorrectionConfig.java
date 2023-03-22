package me.domirusz24.pkmenu.config;

import me.domirusz24.ext.config.Config;
import net.md_5.bungee.api.ChatColor;

import java.util.Optional;

public class ColorCorrectionConfig extends Config {
    public ColorCorrectionConfig(String path) {
        super(path);
    }

    public Optional<ChatColor> getPrimary(String element) {
        if (!isString(element + ".primary")) {
            return Optional.empty();
        }

        return Optional.of(ChatColor.getByChar(getString(element + ".primary").charAt(0)));
    }

    public Optional<ChatColor> getSecondary(String element) {
        if (!isString(element + ".secondary")) {
            return Optional.empty();
        }

        return Optional.of(ChatColor.getByChar(getString(element + ".secondary").charAt(0)));
    }
}
