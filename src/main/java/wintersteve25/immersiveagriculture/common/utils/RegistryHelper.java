package wintersteve25.immersiveagriculture.common.utils;

import fictioncraft.wintersteve25.fclib.common.helper.MiscHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
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

    public static <I extends TileEntity> RegistryObject<TileEntityType<I>> registerTE(String name, Supplier<I> te, Block block) {
        return Registration.TE.register(name, () -> TileEntityType.Builder.create(te, block).build(null));
    }

    public static <I extends Entity> RegistryObject<EntityType<I>> registerEntity(String name, EntityType.IFactory<I> entity, float xSize, float ySize) {
        return Registration.ENTITY.register(name, () -> EntityType.Builder.create(entity, EntityClassification.MISC).size(xSize, ySize).build(MiscHelper.langToReg(name)));
    }
}
