package me.domirusz24.pkmenu.manager.pk;

import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;


public record PKAbility(@Getter CoreAbility ability) implements MenuAbility {

    @Override
    public String name() {
        return ability.getName();
    }

    @Override
    public String getDescription() {
        return ability.getDescription();
    }

    @Override
    public String getUsage() {
        return ability.getInstructions();
    }

    @Override
    public String getElementId() {
        return PKMagicManager.elementToId(ability.getElement());
    }

    @Override
    public boolean isCombo() {
        return ability instanceof ComboAbility;
    }

    @Override
    public boolean isHidden() {
        return ability.isHiddenAbility();
    }
}
