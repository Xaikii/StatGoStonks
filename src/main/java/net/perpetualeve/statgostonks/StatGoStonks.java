package net.perpetualeve.statgostonks;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(StatGoStonks.MODID)
public class StatGoStonks {
	public static final String	MODID	= "statgostonks";
	private static final Logger	LOGGER	= LogUtils.getLogger( );

	public StatGoStonks( ) {
		IEventBus modEventBus = FMLJavaModLoadingContext.get( ).getModEventBus( );

		MinecraftForge.EVENT_BUS.register(this);

	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
	}
}
