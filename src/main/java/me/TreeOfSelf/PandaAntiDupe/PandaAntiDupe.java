package me.TreeOfSelf.PandaAntiDupe;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PandaAntiDupe implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("panda-anti-dupe");

	@Override
	public void onInitialize() {
		LOGGER.info("PandaAntiDupe Started!");
		PandaAntiDupeConfig.loadDupeConfig();
	}
}