package me.domirusz24.pkmenu.menu;

import lombok.Getter;
import me.domirusz24.ext.language.Language;
import me.domirusz24.ext.language.LanguageClass;
import me.domirusz24.pkmenu.manager.magic.MagicManager;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import org.bukkit.entity.Player;

import static me.domirusz24.pkmenu.menu.AbilityMenu.LANG_ABILITY_BIND_HOVER;

@LanguageClass
public class AbilityInfoMenu extends MagicMenuBase {

    @Getter
    private final MenuAbility ability;
    private final MenuElement abilityElement;
    @Getter
    private final MenuElement previous;

    @Language("Menu.InfoMenu")
    public static String LANG_INFO = " %element_secondary%~ %element_primary%%ability_name% %element_secondary%~ || ||%element_primary%Description:||&7%ability_desc%|| ||%element_secondary%Usage:||&7%ability_usage%|| ||%element_primary%Author: &7%ability_author%||%element_secondary%Version: &7%ability_version%";

    @Language("Menu.InfoMenuBind")
    public static String LANG_BIND = "%element_secondary%[ %element_primary%Bind %element_secondary%]";

    public AbilityInfoMenu(Player player, BindMenu.AbilityAndElementArg args, MagicManager manager, MenuManager menuManager) {
        super(player, manager, menuManager);
        this.ability = args.ability();
        this.previous = args.previous();
        this.abilityElement = manager.getElement(ability).get();

        update();
    }

    @Override
    protected void displayMenu() {
        putPrevious((p) -> menuManager.getAbilityMenu().build(p, previous));
        newLine(2);
        addMessage(LANG_INFO, ability, abilityElement);
        newLine(2);
        addHoverAndClickMessage(
                LANG_BIND,
                LANG_ABILITY_BIND_HOVER,
                (p) -> openBindMenu(), ability, abilityElement
        );
        newLine();
    }

    private void openBindMenu() {
        menuManager.getBindMenu().build(getPlayer(), ability, previous);
    }
}
