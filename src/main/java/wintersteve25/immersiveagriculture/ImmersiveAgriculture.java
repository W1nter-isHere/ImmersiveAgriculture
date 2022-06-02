package wintersteve25.immersiveagriculture;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wintersteve25.immersiveagriculture.common.IAReloadCommand;
import wintersteve25.immersiveagriculture.common.config.Config;
import wintersteve25.immersiveagriculture.common.config.IAConfigs;
import wintersteve25.immersiveagriculture.common.events.ServerEventsHandler;
import wintersteve25.immersiveagriculture.common.init.IAItems;
import wintersteve25.immersiveagriculture.common.utils.Registration;

@Mod(ImmersiveAgriculture.MODID)
public class ImmersiveAgriculture {
    public static final String MODID = "immersiveagriculture";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public ImmersiveAgriculture() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        LOGGER.info("Hallo! :D");

        Registration.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, IAConfigs.SERVER_CONFIG);
        Config.createConfig();
        if (IAConfigs.PRINT_EXAMPLE.get()) {
            Config.printExample();
        }

        modEventBus.addListener(ServerEventsHandler::entityAttributeEvent);
        forgeEventBus.addListener(ServerEventsHandler::registerCommandsInit);
        forgeEventBus.addListener(ServerEventsHandler::onCropGrowth);
        forgeEventBus.addListener(ServerEventsHandler::onBonemeal);
        forgeEventBus.addListener(ServerEventsHandler::serverStart);

        forgeEventBus.register(this);
    }

    public static final ItemGroup creativeTab = new ItemGroup("immersiveagriculture") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(IAItems.IRON_SCYTHE);
        }
    };

    public static void registerCommands(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> requires = Commands.literal("ia").requires((commandSource) -> commandSource.hasPermissionLevel(3));
        LiteralCommandNode<CommandSource> source = dispatcher.register(requires.then(IAReloadCommand.register(dispatcher)));
        dispatcher.register(Commands.literal("ia").redirect(source));
        dispatcher.register(Commands.literal("immersiveagriculture").redirect(source));
        LOGGER.info("Registered Immersive Agriculture Commands!");
    }
}
