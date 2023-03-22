package me.domirusz24.pkmenu.manager.pk;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.BendingReloadEvent;
import me.domirusz24.pkmenu.config.ColorCorrectionConfig;
import me.domirusz24.pkmenu.manager.magic.MagicManager;
import me.domirusz24.pkmenu.manager.magic.MenuAbility;
import me.domirusz24.pkmenu.manager.magic.MenuElement;
import me.domirusz24.pkmenu.manager.magic.MockElement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.stream.Collectors;

public class PKMagicManager implements MagicManager, Listener {

    public static String elementToId(Element element) {
        return element.getName().toLowerCase().replaceAll(" ", "_");
    }

    private final HashMap<String, MenuAbility> abilities = new HashMap<>();
    private final HashMap<String, MenuElement> elements = new HashMap<>();


    private final ColorCorrectionConfig correctionConfig;
    public PKMagicManager(ColorCorrectionConfig correctionConfig) {
        this.correctionConfig = correctionConfig;
        reloadElementsAndAbilities();
    }

    @EventHandler
    public void onPKReload(BendingReloadEvent event) {
        reloadElementsAndAbilities();
    }

    public void reloadElementsAndAbilities() {
        abilities.clear();
        elements.clear();
        for (CoreAbility ability : CoreAbility.getAbilities()) {
            abilities.put(ability.getName(), new PKAbility(ability));
        }

        for (Element element : Element.getAllElements()) {
            elements.put(elementToId(element), new PKElement(element, correctionConfig));
        }

        for (Element.SubElement element : Element.getAllSubElements()) {
            elements.put(elementToId(element), new PKSubElement(element, correctionConfig));
        }

        elements.put("unknown", new MockElement());
    }

    @Override
    public Set<MenuElement> getAllElements() {
        return new HashSet<>(elements.values());
    }

    @Override
    public Set<MenuAbility> getAbilities() {
        return new HashSet<>(abilities.values());
    }

    @Override
    public Set<String> getElementsById(Player player) {
        Set<String> elements = BendingPlayer.getBendingPlayer(player).getElements().stream().map(PKMagicManager::elementToId).collect(Collectors.toSet());
        elements.addAll(BendingPlayer.getBendingPlayer(player).getSubElements().stream().map(PKMagicManager::elementToId).toList());
        return elements;
    }

    @Override
    public boolean canBind(Player player, String ability) {
        return BendingPlayer.getBendingPlayer(player).canBind(CoreAbility.getAbility(ability));
    }

    @Override
    public Map<Integer, String> getBindsByName(Player player) {
        return BendingPlayer.getBendingPlayer(player).getAbilities();
    }

    @Override
    public void bind(Player player, String ability, int slot) {
        BendingPlayer.getBendingPlayer(player).bindAbility(ability, slot);
    }

    @Override
    public void unBind(Player player, int slot) {
        BendingPlayer.getBendingPlayer(player).getAbilities().remove(slot);
    }

    @Override
    public Optional<MenuAbility> getAbility(String name) {
        return Optional.ofNullable(abilities.get(name));
    }
}
