package me.domirusz24.pkmenu.manager.pk;

import com.projectkorra.projectkorra.Element;
import me.domirusz24.pkmenu.config.ColorCorrectionConfig;
import me.domirusz24.pkmenu.manager.magic.MenuElement;

import java.util.Optional;

public class PKSubElement extends PKElement {

    private final Element.SubElement element;


    public PKSubElement(Element.SubElement element, ColorCorrectionConfig correctionConfig) {
        super(element, correctionConfig);
        this.element = element;
    }

    public Element.SubElement getSubElement() {
        return element;
    }

    @Override
    public Optional<String> getParentElement() {
        return Optional.of(PKMagicManager.elementToId(element.getParentElement()));
    }
}
