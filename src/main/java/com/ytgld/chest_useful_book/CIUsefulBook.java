package com.ytgld.chest_useful_book;

import com.ytgld.chest_useful_book.event.CIModifyBookEvent;
import com.ytgld.chest_useful_book.event.PowerEvent;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CIUsefulBook.MODID)
public class CIUsefulBook {
    public static final String MODID = "chest_useful_book";
    public static final Logger LOGGER = LogUtils.getLogger();
    public CIUsefulBook(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(new PowerEvent());
        modContainer.registerConfig(ModConfig.Type.COMMON, BookConfig.fc);
        modEventBus.addListener(this::onGatherData);
    }
    public void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        gen.addProvider(event.includeClient(), new CIModifyBookEvent.CIUsefulBookText(packOutput));
      }

}
