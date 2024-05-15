package cc.unilock.third_chance;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThirdChance implements ModInitializer {
	public static final String MOD_ID = "third_chance";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ThirdChanceConfig CONFIG = ThirdChanceConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", MOD_ID, ThirdChanceConfig.class);

	public static final TagKey<DamageType> IGNORED = TagKey.of(RegistryKeys.DAMAGE_TYPE, id("ignored"));

	@Override
	public void onInitialize() {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
			// Skip if the entity is not a player
			if (!(entity instanceof PlayerEntity)) {
				return true;
			}

			// Skip if the damage is from an ignored source
			if (source.isIn(IGNORED)) {
				return true;
			}

			// Skip if the amount of damage is less than the entity's current health
			if (amount < entity.getHealth()) {
				return true;
			}

			// Calculate the damage threshold from the entity's max health multiplied by the configured percentage thereof
			final float threshold = entity.getMaxHealth() * CONFIG.percent.value();

			// Skip if the amount of damage does not meet the threshold
			if (amount < threshold) {
				return true;
			}

			// Skip if the entity's health does not meet the threshold
			if (entity.getHealth() < threshold) {
				return true;
			}

			// Damage the entity to 1 HP
			entity.damage(entity.getDamageSources().generic(), entity.getHealth() - 1.0F);

			return false;
		});
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}
