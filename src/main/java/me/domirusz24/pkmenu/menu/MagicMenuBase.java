package me.domirusz24.pkmenu.menu;

import com.projectkorra.projectkorra.BendingPlayer;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.domirusz24.ext.chatgui.ChatGUI;
import me.domirusz24.ext.language.Language;
import me.domirusz24.ext.language.LanguageClass;
import me.domirusz24.pkmenu.manager.magic.MagicManager;
import org.bukkit.entity.Player;

@LanguageClass
public abstract class MagicMenuBase extends ChatGUI {

    @Getter
    protected final MagicManager manager;
    @Getter
    protected final MenuManager menuManager;

    public MagicMenuBase(Player player, MagicManager manager, MenuManager menuManager) {
        super(player);
        this.manager = manager;
        this.menuManager = menuManager;
    }

    @Language("Menu.LineColor")
    public static String LANG_LINE_COLOR = "&7";

    @Override
    protected void _update() {
        reset();
        line(LANG_LINE_COLOR);
        newLine(2);

        displayMenu();

        newLine();

        putX();

        newLine(2);
        line(LANG_LINE_COLOR);
        newLine();
    }


    protected abstract void displayMenu();


    @Override
    public boolean resetOnMove() {
        return false;
    }

    @Override
    public String applyPlaceholders(String s) {
        return PlaceholderAPI.setPlaceholders(getPlayer(), s);
    }
}
