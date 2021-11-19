package me.miquiis.minecraftschool;

import me.miquiis.minecraftschool.entity.ModEntityTypes;
import me.miquiis.minecraftschool.entity.render.BabyPlayerRenderer;
import me.miquiis.minecraftschool.entity.render.FakePlayerRenderer;
import me.miquiis.minecraftschool.managers.FileManager;
import me.miquiis.minecraftschool.managers.RecordManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MinecraftSchool.MOD_ID)
public class MinecraftSchool
{
    public static final String MOD_ID = "minecraftschool";

    private static MinecraftSchool instance;

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();


    private RecordManager recordManager;
    private FileManager pathfindingFile;

    public MinecraftSchool() {
        instance = this;
        // Register the setup method for modloading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntityTypes.register(modEventBus);

        modEventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        modEventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        modEventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        modEventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        recordManager = new RecordManager(this);
        pathfindingFile = new FileManager("pathfinding");

        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FAKE_PLAYER.get(), FakePlayerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BABY_PLAYER.get(), BabyPlayerRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
        }
    }

    public FileManager getPathfindingFile() {
        return pathfindingFile;
    }

    public RecordManager getRecordManager() {
        return recordManager;
    }

    public static MinecraftSchool getInstance() {
        return instance;
    }
}
