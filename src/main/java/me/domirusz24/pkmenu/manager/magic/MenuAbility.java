package me.domirusz24.pkmenu.manager.magic;


import me.domirusz24.ext.util.placeholders.PlaceholderObject;

public interface MenuAbility extends PlaceholderObject {
    String name();
    String getDescription();
    String getUsage();
    String getElementId();

    boolean isCombo();
    boolean isHidden();

    @Override
    default String onPlaceholderRequest(String s) {
        return switch (s.toLowerCase()) {
            case "name" -> name();
            case "description", "desc" -> getDescription();
            case "usage", "instructions" -> getUsage();
            case "combo" -> isCombo() ? "✔" : "✕";
            default -> null;
        };
    }

    @Override
    default String placeHolderPrefix() {
        return "ability";
    }
}
