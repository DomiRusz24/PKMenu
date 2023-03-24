package me.domirusz24.pkmenu.menu;

import me.domirusz24.ext.language.Language;
import me.domirusz24.pkmenu.manager.magic.MagicManager;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import org.bukkit.entity.Player;

import java.util.Map;

public class BindMenu extends MagicMenuBase {

    @Language("Menu.Clear")
    public static String LANG_CLEAR = "&c&l[ &4&lClear abilities&r&c&l ]";

    @Language("Menu.ClearHover")
    public static String LANG_CLEAR_HOVER = "&7Click here to clear bound abilities.";

    @Language("Menu.Binding.Ability")
    public static String LANG_ABILITY = "&7(%slot%)&r";

    @Language("Menu.Binding.AbilityHover")
    public static String LANG_ABILITY_HOVER = "&7Click here to bind %element_primary%%ability_name% &r&7to the slot %slot%";

    @Language("Menu.Binding.AbilityBound")
    public static String LANG_ABILITY_BOUND = "&l%element_primary%(%element_secondary%%slot%%element_primary%)&r";

    @Language("Menu.Binding.AbilityBoundHover")
    public static String LANG_ABILITY_BOUND_HOVER = "%element_secondary%%slot% → %element_primary%%ability_name%";

    @Language("Menu.Binding.Unbind")
    public static String LANG_UNBIND = "&c(x)";

    @Language("Menu.Binding.UnbindEmpty")
    public static String LANG_UNBIND_EMPTY = "&7(x)";

    @Language("Menu.Binding.UnbindHover")
    public static String LANG_UNBIND_HOVER = "&7Click to unbind slot %slot%";

    @Language("Menu.Binding.Title")
    public static String LANG_TITLE = "%element_primary%Binding &l%ability_name%";



    private final MenuAbility ability;
    private final MenuElement previous;

    private Map<Integer, MenuAbility> binds;

    public record AbilityAndElementArg(MenuAbility ability, MenuElement previous){}

    public BindMenu(Player player, AbilityAndElementArg args, MagicManager manager, MenuManager menuManager) {
        super(player, manager, menuManager);
        this.ability = args.ability;
        this.previous = args.previous;

        update();
    }

    @Override
    protected void displayMenu() {
        this.binds = manager.getBinds(getPlayer());

        putPrevious((p) -> menuManager.getAbilityMenu().build(p, previous));
        newLine(2);
        tab().addMessage(LANG_TITLE, ability, manager.getElement(ability).get());
        newLine(2);
        tab();
        for (int i = 1; i <= 9; i++) {
            display(i);
            addMessage(" ");
        }

        newLine();
        tab();

        for (int i = 1; i <= 9; i++) {
            displayX(i);
            addMessage(" ");
        }

        newLine(2);
        tab();
        addHoverAndClickMessage(LANG_CLEAR, LANG_CLEAR_HOVER, (p) -> {
            menuManager.getManager().clearBinds(p);
            update();
        });

        newLine();

    }

    public void display(int slot) {
        if (!binds.containsKey(slot)) {
            displayNoBound(slot);
            return;
        }

        display(slot, binds.get(slot));
    }

    public void displayNoBound(int slot) {
        addHoverAndClickMessage(LANG_ABILITY.replaceAll("%slot%", String.valueOf(slot)),
                LANG_ABILITY_HOVER.replaceAll("%slot%", String.valueOf(slot)),
                (p) -> bind(slot), ability, previous
        );
    }

    public void display(int slot, MenuAbility ability) {
        addHoverAndClickMessage(
                LANG_ABILITY_BOUND.replaceAll("%slot%", String.valueOf(slot)),
                LANG_ABILITY_BOUND_HOVER.replaceAll("%slot%", String.valueOf(slot)),
                (p) -> bind(slot), ability, manager.getElement(ability).get()
        );
    }

    public void displayX(int slot) {
        if (!binds.containsKey(slot)) {
            addMessage(LANG_UNBIND_EMPTY);
            return;
        }

        MenuAbility boundAbility = binds.get(slot);

        addHoverAndClickMessage(
                LANG_UNBIND.replaceAll("%slot%", String.valueOf(slot)),
                LANG_UNBIND_HOVER.replaceAll("%slot%", String.valueOf(slot)),
                p -> unBind(slot),
                boundAbility, manager.getElement(boundAbility).get()
        );
    }

    public void bind(int slot) {
        manager.bind(getPlayer(), ability, slot);
        update();
    }

    public void unBind(int slot) {
        manager.unBind(getPlayer(), slot);
        update();
    }
}
