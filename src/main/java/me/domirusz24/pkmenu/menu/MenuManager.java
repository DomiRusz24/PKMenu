package me.domirusz24.pkmenu.menu;

import lombok.Getter;
import me.domirusz24.ext.chatgui.ChatGUI;
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
    private final AbilityDisplayer<BindMenu> bindMenu;

    @Getter
    private final AbilityDisplayer<AbilityInfoMenu> abilityInfoMenu;

    public MenuManager(PKMagicManager manager) {
        this.manager = manager;

        elementMenu = new ChatGUIDisplayer<>((p) -> new ElementMenu(p, manager, this));
        abilityMenu = new ChatGUIDisplayerExtra<>((p, e) -> new AbilityMenu(p, e, manager, this));
        bindMenu = new AbilityDisplayer<>((p, e) -> new BindMenu(p, e, manager, this));
        abilityInfoMenu = new AbilityDisplayer<>((p, e) -> new AbilityInfoMenu(p, e, manager, this));
    }

    public static class AbilityDisplayer<T extends ChatGUI> extends ChatGUIDisplayerExtra<T, BindMenu.AbilityAndElementArg> {

        public AbilityDisplayer(ChatGUIBuilder<T, BindMenu.AbilityAndElementArg> builder) {
            super(builder);
        }

        public void build(Player player, MenuAbility ability, MenuElement previous) {
            build(player, new BindMenu.AbilityAndElementArg(ability, previous));
        }
    }


}
