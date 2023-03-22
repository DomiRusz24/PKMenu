package me.domirusz24.pkmenu.manager.magic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;

public record MockAbility(@Getter String name) implements MenuAbility {

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getElementId() {
        return "unknown";
    }

    @Override
    public boolean isCombo() {
        return false;
    }

    @Override
    public boolean isHidden() {
        return false;
    }
}
