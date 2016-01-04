package net.shadowfacts.shadowmc.gui.component;

import net.minecraft.client.Minecraft;
import net.shadowfacts.shadowmc.gui.AbstractGUI;
import net.shadowfacts.shadowmc.gui.BaseGUI;
import net.shadowfacts.shadowmc.util.Color;

/**
 * @author shadowfacts
 */
public class GUIComponentWindow extends BaseGUI {

	protected GUIComponentWindow(Minecraft mc, int x, int y, int width, int height) {
		super(mc, x, y, width, height);
	}

	public GUIComponentWindow(int x, int y, int width, int height) {
		this(Minecraft.getMinecraft(), x, y, width, height);
	}

	public GUIComponentWindow addComponent(AbstractGUI component) {
		addChild(component);
		return this;
	}

	@Override
	public boolean isWithinMovableBounds(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width &&
				mouseY >= y && mouseY <= y + 20;
	}

	@Override
	public void draw(int mouseX, int mouseY) {
		drawRect(x, y, width, height, Color.DARK_PURPLE);
		drawRect(x, y, width, 20, Color.BLACK);
		super.draw(mouseX, mouseY);
	}

}
