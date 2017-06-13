package net.shadowfacts.shadowmc.item;

import net.minecraft.item.Item;
import net.shadowfacts.shadowmc.ShadowMC;

/**
 * @author shadowfacts
 */
public class ItemBase extends Item implements ItemModelProvider {

	protected final String name;

	public ItemBase(String name) {
		this.name = name;

		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
	}


	@Override
	public void initItemModel() {
		ShadowMC.proxy.registerItemModel(this, 0, getRegistryName());
	}

}
