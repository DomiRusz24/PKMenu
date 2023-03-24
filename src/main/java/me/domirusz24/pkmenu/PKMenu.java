package me.domirusz24.pkmenu;

import dev.jorel.commandapi.CommandAPI;
import me.domirusz24.ext.chatgui.ChatGUIExtension;
import me.domirusz24.ext.config.ConfigExtension;
import me.domirusz24.ext.language.LanguageExtension;
import me.domirusz24.ext.util.PlaceholderExtension;
import me.domirusz24.ext.util.UtilPlugin;
import me.domirusz24.ext.util.worldedit.SelectionExtension;
import me.domirusz24.ext.util.worldedit.schematic.SchematicExtension;
import me.domirusz24.pkmenu.command.PKMenuCommand;
import me.domirusz24.pkmenu.config.ColorCorrectionConfig;
import me.domirusz24.pkmenu.manager.pk.PKMagicManager;
import me.domirusz24.pkmenu.menu.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PKMenu extends UtilPlugin {



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    protected void setUpExtensions() {
        CommandAPI.onEnable(this);
        ConfigExtension.onLoad(new ConfigExtension.Configuration(this, getLogger(), () -> setEnabled(false)));

        LanguageExtension.onLoad(new LanguageExtension.Configuration(
                getLogger(),
                "language.yml"
        ));

        PlaceholderExtension.onLoad(new PlaceholderExtension.Configuration());

        ChatGUIExtension.onLoad(new ChatGUIExtension.Configuration(asyncProvider, eventControl), this);

        LanguageExtension.getConfiguration().registerAllAnnotations(AbilityMenu.class);
        LanguageExtension.getConfiguration().registerAllAnnotations(ElementMenu.class);
        LanguageExtension.getConfiguration().registerAllAnnotations(MagicMenuBase.class);
        LanguageExtension.getConfiguration().registerAllAnnotations(BindMenu.class);
        LanguageExtension.getConfiguration().registerAllAnnotations(AbilityInfoMenu.class);
        LanguageExtension.getConfiguration().registerAllAnnotations(this);
    }

    private ColorCorrectionConfig correctionConfig;

    @Override
    protected void setUpConfigs() {
        correctionConfig = new ColorCorrectionConfig("ColorCorrection.yml");
    }

    private PKMagicManager pkManager;
    private MenuManager menuManager;

    @Override
    protected void setUpManagers() {
        pkManager = new PKMagicManager(correctionConfig);
        menuManager = new MenuManager(pkManager);

        Bukkit.getScheduler().runTaskLater(this, () -> menuManager.getManager().reloadElementsAndAbilities(), 1);
    }

    @Override
    protected void setUpListeners() {
        register(pkManager);
    }

    @Override
    protected void setUpCommands() {
        new PKMenuCommand(menuManager);
    }
}
