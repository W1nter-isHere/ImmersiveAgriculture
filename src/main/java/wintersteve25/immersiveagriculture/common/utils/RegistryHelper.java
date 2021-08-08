package wintersteve25.immersiveagriculture.common.utils;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;

import java.util.function.Supplier;

public class RegistryHelper {
    public static <I extends Block> RegistryObject<I> register(String name, Supplier<? extends I> block) {
        RegistryObject<I> registryObject = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().group(ImmersiveAgriculture.creativeTab)));
        return registryObject;
    }

    public static <I extends Block> RegistryObject<I> register(String name, Supplier<? extends I> block, BlockItem blockItem) {
        RegistryObject<I> registryObject = Registration.BLOCKS.register(name, block);
        Registration.ITEMS.register(name, () -> blockItem);
        return registryObject;
    }

    public static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<? extends I> item) {
        return Registration.ITEMS.register(name, item);
    }

    public static <I extends Enchantment> RegistryObject<I> registerEnchantment(String name, Supplier<? extends I> enchantment) {
        return Registration.ENCHANTMENT.register(name, enchantment);
    }

    public static <I extends TileEntityType<?>> RegistryObject<I> registerTE(String name, Supplier<? extends I> te) {
        return Registration.TE.register(name, te);
    }

    public static <I extends ContainerType<?>> RegistryObject<I> registerContainer(String name, Supplier<? extends I> container) {
        return Registration.CONTAINER.register(name, container);
    }
}
