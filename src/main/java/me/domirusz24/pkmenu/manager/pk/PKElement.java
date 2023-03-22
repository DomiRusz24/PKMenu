package me.domirusz24.pkmenu.manager.pk;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.domirusz24.pkmenu.config.ColorCorrectionConfig;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import net.md_5.bungee.api.ChatColor;

import java.util.Optional;


@RequiredArgsConstructor
public class PKElement implements MenuElement {

    @Getter
    private final Element element;

    @Getter
    private final ColorCorrectionConfig correctionConfig;

    @Override
    public String getId() {
        return PKMagicManager.elementToId(element);
    }

    @Override
    public String getName() {
        return element.getName();
    }

    @Override
    public ChatColor getPrimary() {
        return correctionConfig.getPrimary(element.getName()).orElse(element.getColor());
    }

    @Override
    public ChatColor getSecondary() {
        return correctionConfig.getSecondary(element.getName()).orElse(element.getSubColor());
    }

    @Override
    public Optional<String> getParentElement() {
        return Optional.empty();
    }
}
