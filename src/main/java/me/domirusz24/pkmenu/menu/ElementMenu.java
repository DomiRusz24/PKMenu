package me.domirusz24.pkmenu.menu;

import me.domirusz24.ext.language.Language;
import me.domirusz24.ext.language.LanguageClass;
import me.domirusz24.pkmenu.manager.magic.MagicManager;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import org.bukkit.entity.Player;

@LanguageClass
public class ElementMenu extends MagicMenuBase {
    public ElementMenu(Player player, MagicManager manager, MenuManager menuManager) {
        super(player, manager, menuManager);

        update();
    }

    @Override
    protected void displayMenu() {
        for (MenuElement element : manager.getMainElements(getPlayer())) {
            display(element);
            newLine();
        }
    }

    @Language("Menu.ElementClick")
    public static String LANG_ELEMENT_CLICK = "&7Click to view all available abilities for this element.";

    private void display(MenuElement element) {
         addMessage(element.getSecondarySafe() + " → ");
         addHoverAndClickMessage(element.getPrimary() + element.getName(), LANG_ELEMENT_CLICK, (p) -> {
             getMenuManager().getAbilityMenu().build(p, element);
         });
    }
}
