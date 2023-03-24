package me.domirusz24.pkmenu.manager.pk;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import lombok.Getter;

public class PKAddonAbility extends PKAbility {
    @Getter
    private final AddonAbility addonAbility;
    public PKAddonAbility(CoreAbility ability, AddonAbility addonAbility) {
        super(ability);
        this.addonAbility = addonAbility;
    }

    @Override
    public String getAuthor() {
        return addonAbility.getAuthor();
    }

    @Override
    public String getVersion() {
        return addonAbility.getVersion();
    }
}
