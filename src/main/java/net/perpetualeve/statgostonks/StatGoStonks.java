package net.perpetualeve.statgostonks;

import java.util.UUID;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.api.ConfigType;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry.DoubleValue;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;
import carbonconfiglib.config.ConfigSettings;
import carbonconfiglib.utils.AutomationType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(StatGoStonks.MODID)
public class StatGoStonks {
	public static final String	MODID	= "statgostonks";

	public static final UUID		healthUUID	= UUID.fromString("11759fff-daca-4217-b173-d263d64c87f5");
	public static AttributeModifier	healthModifier;

	public static ConfigHandler	CONFIG;
	public static DoubleValue	MULTIPLIER;
	public static DoubleValue	DURABILITY_MULTIPLIER;

	public StatGoStonks( ) {
		Config config = new Config("statgostonks");
		CONFIG = CarbonConfig.CONFIGS.createConfig(config, ConfigSettings.withConfigType(ConfigType.SERVER)
			.withAutomations(AutomationType.AUTO_RELOAD, AutomationType.AUTO_SYNC, AutomationType.AUTO_LOAD));

		ConfigSection values = new ConfigSection("values");

		MULTIPLIER				= values.addDouble("multiplier", 5, "The general multiplier for stuff").setMax(1000).setMin(1E-3);
		DURABILITY_MULTIPLIER	= values.addDouble("durabilityMultiplier", 3, "The multiplier for durability").setMax(1000).setMin(1E-3);
		config.add(values);

		MinecraftForge.EVENT_BUS.register(this);

		CONFIG.addLoadedListener(( ) ->
		{
			healthModifier = new AttributeModifier(healthUUID, "", MULTIPLIER.getValue( ) - 1,
				Operation.MULTIPLY_TOTAL);
		});
		CONFIG.register( );

	}

	@SubscribeEvent
	public void onJoin(EntityJoinLevelEvent event) {
		if (event.getEntity( ) instanceof LivingEntity entity) {
			AttributeInstance inst = entity.getAttribute(Attributes.MAX_HEALTH);
			inst.removeModifier(healthUUID);
			inst.addTransientModifier(healthModifier);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onHurt(LivingDamageEvent event) {
		event.setAmount(event.getAmount( ) * MULTIPLIER.getValue( ).floatValue( ));
	}

}
