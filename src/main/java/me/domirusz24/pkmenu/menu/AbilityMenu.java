package me.domirusz24.pkmenu.menu;

import me.domirusz24.ext.language.Language;
import me.domirusz24.ext.language.LanguageClass;
import me.domirusz24.pkmenu.manager.magic.MagicManager;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import org.bukkit.entity.Player;

import java.util.Map;

@LanguageClass
public class AbilityMenu extends MagicMenuBase {
    private final MenuElement element;

    private final Map<Integer, MenuAbility> binds;
    public AbilityMenu(Player player, MenuElement element, MagicManager manager, MenuManager menuManager) {
        super(player, manager, menuManager);
        this.element = element;
        this.binds = manager.getBinds(player);

        update();
    }

    @Override
    protected void displayMenu() {
        putPrevious((p) -> menuManager.getElementMenu().build(p));
        newLine(2);
        display(element);
        for (MenuAbility ability : getManager().getAbilities(element, getPlayer())) {
            if (ability.isHidden()) continue;
            tab();
            display(element, ability);
            newLine();
        }

        newLine();

        for (MenuElement subElement : getManager().getAllSubElements(element)) {
            display(subElement);
            for (MenuAbility ability : getManager().getAbilities(subElement, getPlayer())) {
                if (ability.isHidden()) continue;
                tab();
                display(subElement, ability);
                newLine();
            }

            newLine();
        }
    }

    @Language("Menu.AbilityHover")
    public static String LANG_ABILITY_HOVER = "&7%ability_description%";

    @Language("Menu.AbilityInfo")
    public static String LANG_ABILITY_INFO = "&7[ %element_primary%i&7 ]";

    @Language("Menu.AbilityBind")
    public static String LANG_ABILITY_BIND = "&7[ %element_primary%+&7 ]";

    @Language("Menu.AbilityBindHover")
    public static String LANG_ABILITY_BIND_HOVER = "&7Click to bind this ability.";

    @Language("Menu.AlreadyBound")
    public static String LANG_ALREADY_BINDED = "%element_primary%[%element_secondary%%slot%%element_primary%]";

    @Language("Menu.AlreadyBoundHover")
    public static String LANG_ALREADY_BINDED_HOVER = "&7This ability is already binded on slot %slot%";


    @Language("Menu.AbilityInfoHover")
    public static String LANG_ABILITY_INFO_HOVER = "&7Usage:||&7%ability_usage%";

    private void display(MenuElement element) {
        addMessage("§l" + element.getPrimary() + element.getName() + ":", element);
        newLine();
    }


    private void display(MenuElement element, MenuAbility ability) {
        newLine();
        addMessage(element.getSecondarySafe() + " → ", ability, element);
        if (ability.getDescription() != null && !ability.getDescription().isBlank()) {
            addHoverMessage(element.getPrimary() + ability.name(), LANG_ABILITY_HOVER, ability, element);
        } else {
            addMessage(element.getPrimary() + ability.name(), ability, element);
        }
        addMessage(" ");
        if (ability.getUsage() != null && !ability.getUsage().isBlank()) {
            addHoverAndClickMessage(LANG_ABILITY_INFO, LANG_ABILITY_INFO_HOVER, (p) -> openInfoMenu(ability), ability, element);
        } else {
            addClickableMessage(LANG_ABILITY_INFO, (p) -> openInfoMenu(ability), ability, element);
        }
        addMessage(" ");

        addHoverAndClickMessage(
                LANG_ABILITY_BIND,
                LANG_ABILITY_BIND_HOVER,
                (p) -> openBindMenu(ability), ability, element);

        for (Integer slot : binds.keySet()) {
            MenuAbility bindedAbility = binds.get(slot);
            if (bindedAbility == null) continue;
            if (ability.name().equals(bindedAbility.name())) {
                addMessage(" ");
                addHoverMessage(
                        LANG_ALREADY_BINDED.replaceAll("%slot%", String.valueOf(slot)),
                        LANG_ALREADY_BINDED_HOVER.replaceAll("%slot%", String.valueOf(slot)),
                        ability,
                        element
                );
                break;
            }
        }

    }

    private void openBindMenu(MenuAbility ability) {
        menuManager.getBindMenu().build(getPlayer(), ability, element);
    }

    private void openInfoMenu(MenuAbility ability) {
        menuManager.getAbilityInfoMenu().build(getPlayer(), ability, element);
    }
}
