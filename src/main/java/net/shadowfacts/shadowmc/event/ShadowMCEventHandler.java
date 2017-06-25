package net.shadowfacts.shadowmc.event;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.shadowfacts.shadowmc.ShadowMC;
import net.shadowfacts.shadowmc.ShadowMCConfig;
import net.shadowfacts.shadowmc.achievement.AchievementProvider;
import net.shadowfacts.shadowmc.anvil.AnvilManager;
import net.shadowfacts.shadowmc.anvil.AnvilRecipe;

import java.util.Optional;

/**
 * @author shadowfacts
 */
public class ShadowMCEventHandler {

	@SubscribeEvent
	public void anvilUpdate(AnvilUpdateEvent event) {
		if (event.getLeft() == null || event.getRight() == null) return;

		Optional<AnvilRecipe> optionalRecipe = AnvilManager.getInstance().getRecipe(event.getLeft(), event.getRight());

		if (optionalRecipe.isPresent()) {
			AnvilRecipe recipe = optionalRecipe.get();

			event.setCost(Math.max(recipe.getXPCost(), 1));
			event.setOutput(recipe.getResult());
			event.setMaterialCost(1);
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().toLowerCase().equals(ShadowMC.modId)) {
			ShadowMCConfig.load();
		}
	}

}
