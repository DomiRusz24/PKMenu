package me.domirusz24.pkmenu.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.CommandArgument;
import lombok.Getter;
import me.domirusz24.ext.config.ConfigExtension;
import me.domirusz24.ext.language.BasicLanguage;
import me.domirusz24.pkmenu.menu.MenuManager;

public class PKMenuCommand {

    @Getter
    private final MenuManager manager;

    public PKMenuCommand(MenuManager manager) {
        this.manager = manager;
        init();
    }

    private void init() {
        new CommandAPICommand("pkmenu")
                .executesPlayer((p, info) -> {
                    manager.getElementMenu().build(p);
                }).withSubcommands(
                        new CommandAPICommand("reload")
                                .withPermission("pkmenu.reload")
                                .executes((p, info) -> {
                                    if (ConfigExtension.getConfiguration().getManager().reloadConfigs()) {
                                        p.sendMessage(BasicLanguage.SUCCESS);
                                    } else {
                                        p.sendMessage(BasicLanguage.FAILURE);
                                    }
                                })
                ).register();
    }
}
