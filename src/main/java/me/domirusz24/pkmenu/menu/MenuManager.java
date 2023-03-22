package me.domirusz24.pkmenu.menu;

import lombok.Getter;
import me.domirusz24.ext.chatgui.ChatGUIDisplayer;
import me.domirusz24.ext.chatgui.ChatGUIDisplayerExtra;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import me.domirusz24.pkmenu.manager.pk.PKMagicManager;
import org.bukkit.entity.Player;

public class MenuManager {

    @Getter
    private final PKMagicManager manager;

    @Getter
    private final ChatGUIDisplayer<ElementMenu> elementMenu;

    @Getter
    private final ChatGUIDisplayerExtra<AbilityMenu, MenuElement> abilityMenu;

    @Getter
    private final BindMenuDisplayer bindMenu;

    public MenuManager(PKMagicManager manager) {
        this.manager = manager;

        elementMenu = new ChatGUIDisplayer<>((p) -> new ElementMenu(p, manager, this));
        abilityMenu = new ChatGUIDisplayerExtra<>((p, e) -> new AbilityMenu(p, e, manager, this));
        bindMenu = new BindMenuDisplayer((p, e) -> new BindMenu(p, e, manager, this));
    }

    public static class BindMenuDisplayer extends ChatGUIDisplayerExtra<BindMenu, BindMenu.BindMenuArguments> {

        public BindMenuDisplayer(ChatGUIBuilder<BindMenu, BindMenu.BindMenuArguments> builder) {
            super(builder);
        }

        public void build(Player player, MenuAbility ability, MenuElement previous) {
            build(player, new BindMenu.BindMenuArguments(ability, previous));
        }
    }


}
