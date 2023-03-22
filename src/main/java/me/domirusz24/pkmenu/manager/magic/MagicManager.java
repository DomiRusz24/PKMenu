package me.domirusz24.pkmenu.manager.magic;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public interface MagicManager {

    Set<MenuElement> getAllElements();
    Set<MenuAbility> getAbilities();
    Set<String> getElementsById(Player player);
    boolean canBind(Player player, String ability);

    Map<Integer, String> getBindsByName(Player player);

    default Map<Integer, MenuAbility> getBinds(Player player) {
        Map<Integer, MenuAbility> abilityMap = new HashMap<>();
        getBindsByName(player).forEach((slot, abilityId) -> {
            if (abilityId == null) return;

            abilityMap.put(slot, getAbility(abilityId).orElse(new MockAbility(abilityId)));
        });

        return abilityMap;
    }

    void bind(Player player, String ability, int slot);

    default void bind(Player player, MenuAbility ability, int slot) {
        bind(player, ability.name(), slot);
    }

    default boolean canBind(Player player, MenuAbility ability) {
        return canBind(player, ability.name());
    }

    void unBind(Player player, int slot);

    default void clearBinds(Player player) {
        for (int i = 1; i <= 9; i++) {
            unBind(player, i);
        }
    }

    default Set<MenuElement> getElements(Player player) {
        Set<MenuElement> elements = new HashSet<>();
        for (String elementId : getElementsById(player)) {
            getElement(elementId).ifPresent(elements::add);
        }

        return elements;
    }

    default Set<MenuElement> getMainElements(Player player) {
        return getElements(player).stream().filter((s) -> s.getParentElement().isEmpty()).collect(Collectors.toSet());
    }

    default Set<MenuElement> getSubElements(MenuElement parent, Player player) {
        Set<MenuElement> elements = new HashSet<>();
        Set<MenuElement> playerElements = getElements(player);
        for (MenuElement subElement : getAllSubElements(parent)) {
            if (playerElements.contains(subElement)) {
                elements.add(subElement);
            }
        }
        return elements;
    }

    default Set<MenuAbility> getAbilities(MenuElement element, Player player) {
        Set<MenuAbility> abilities = new HashSet<>();
        for (MenuAbility ability : getAbilities(element)) {
            if (canBind(player, ability)) abilities.add(ability);
        }

        return abilities;
    }

    default Set<MenuAbility> getAbilitiesWithSubs(MenuElement element, Player player) {
        Set<MenuAbility> abilities = new HashSet<>();
        for (MenuAbility ability : getAbilitiesWithSubs(element)) {
            if (canBind(player, ability)) abilities.add(ability);
        }

        return abilities;
    }


    default Set<MenuElement> getAllMainElements() {
        return getAllElements().stream().filter((element) -> element.getParentElement().isEmpty()).collect(Collectors.toSet());
    }

    default Set<MenuElement> getAllSubElements() {
        return getAllElements().stream().filter((element) -> element.getParentElement().isPresent()).collect(Collectors.toSet());
    }

    default Set<MenuElement> getAllSubElements(String parentId) {
        return getAllElements().stream().filter(
                (element) -> element.getParentElement() // get parent element
                        .map((s) -> s.equals(parentId)) // if exists, map it to a boolean (parent == parentId)
                        .orElse(false) // if parent is found, return the comparison, if not, return false
        ).collect(Collectors.toSet());
    }

    default Set<MenuElement> getAllSubElements(MenuElement parent) {
        return getAllSubElements(parent.getId());
    }

    default Optional<MenuAbility> getAbility(String name) {
        for (MenuAbility ability : getAbilities()) {
            if (ability.name().equals(name)) {
                return Optional.of(ability);
            }
        }

        return Optional.empty();
    }

    default Optional<MenuElement> getElement(String id) {
        for (MenuElement element : getAllElements()) {
            if (element.getId().equals(id)) {
                return Optional.of(element);
            }
        }

        return Optional.empty();
    }

    default Optional<MenuElement> getElement(MenuAbility ability) {
        return getElement(ability.getElementId());
    }

    default Set<MenuAbility> getAbilities(String element) {
        return getAbilities().stream().filter((s) -> s.getElementId().equals(element)).collect(Collectors.toSet());
    }

    default Set<MenuAbility> getAbilities(MenuElement element) {
        return getAbilities(element.getId());
    }

    default Set<MenuAbility> getAbilitiesWithSubs(String element) {
        Set<MenuAbility> abilities = new HashSet<>(getAbilities(element));
        for (MenuElement subElement : getAllSubElements(element)) {
            abilities.addAll(getAbilities(subElement));
        }

        return abilities;
    }

    default Set<MenuAbility> getAbilitiesWithSubs(MenuElement element) {
        return getAbilitiesWithSubs(element.getId());
    }
}
