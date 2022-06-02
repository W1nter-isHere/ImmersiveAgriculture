package wintersteve25.immersiveagriculture.common.utils;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import fictioncraft.wintersteve25.fclib.common.interfaces.IFCDataGenObject;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.common.enchantment.IFCEnchantment;
import wintersteve25.immersiveagriculture.common.init.*;
import wintersteve25.immersiveagriculture.common.items.IFCItem;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ImmersiveAgriculture.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ImmersiveAgriculture.MODID);
    public static final DeferredRegister<Enchantment> ENCHANTMENT = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ImmersiveAgriculture.MODID);
    public static final DeferredRegister<TileEntityType<?>> TE = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ImmersiveAgriculture.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(ForgeRegistries.ENTITIES, ImmersiveAgriculture.MODID);

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        ENCHANTMENT.register(eventBus);
        TE.register(eventBus);
        ENTITY.register(eventBus);

        IAItems.register();
        registerItems();
        IAEnchantments.register();
        registerEnchantments();
        IABlocks.register();
        registerBlocks();
        IATags.register();
        IAEntities.register();

        ImmersiveAgriculture.LOGGER.info("Immersive Agriculture Registration Completed");
    }

    public static void registerBlocks() {
        for (IFCDataGenObject<Block> b : IABlocks.BlockList) {
            if (b.getOg() != null) {
                RegistryHelper.register(MiscHelper.langToReg(b.regName()), b::getOg);
                ImmersiveAgriculture.LOGGER.info("Registered " + b.regName());
            }
        }

        for (IFCDataGenObject<Block> b : IABlocks.BlockMap.keySet()) {
            if (b.getOg() != null) {
                RegistryHelper.register(MiscHelper.langToReg(b.regName()), b::getOg, IABlocks.BlockMap.get(b));
                ImmersiveAgriculture.LOGGER.info("Registered " + b.regName());
            }
        }
    }

    public static void registerItems() {
        for (IFCItem interfaceItem : IAItems.ItemList) {
            if (interfaceItem.ogItem() != null) {
                Item ogItem = (Item) interfaceItem.ogItem();
                RegistryHelper.registerItem(MiscHelper.langToReg(interfaceItem.regName()), () -> ogItem);

                ImmersiveAgriculture.LOGGER.info("Registered " + interfaceItem.regName());
            }
        }
    }

    public static void registerEnchantments() {
        for (IFCEnchantment interfaceEnchantment : IAEnchantments.EnchantList) {
            if (interfaceEnchantment.og() != null) {
                Enchantment ogItem = (Enchantment) interfaceEnchantment.og();
                RegistryHelper.registerEnchantment(MiscHelper.langToReg(interfaceEnchantment.regName()), () -> ogItem);
            }
        }
    }
}
