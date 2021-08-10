package wintersteve25.immersiveagriculture.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;
import wintersteve25.immersiveagriculture.client.renderers.SprinklerRenderer;
import wintersteve25.immersiveagriculture.common.block.farm_tile.FarmTileBlock;
import wintersteve25.immersiveagriculture.common.block.hydroponic_tile.HydroponicTileTE;
import wintersteve25.immersiveagriculture.common.config.Config;
import wintersteve25.immersiveagriculture.common.config.ConfigObject;
import wintersteve25.immersiveagriculture.common.init.IABlocks;

public class EventsHandler {
    public static void registerCommandsInit(RegisterCommandsEvent event) {
        ImmersiveAgriculture.registerCommands(event.getDispatcher());
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientInit(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(IABlocks.SPRINKLER_TE.get(), SprinklerRenderer::new);
    }

    @SuppressWarnings("all")
    public static void onCropGrowth(BlockEvent.CropGrowEvent.Pre event) {
        BlockState cropState = event.getState();
        ResourceLocation cropRL = cropState.getBlock().getRegistryName();
        World world = (World) event.getWorld();
        BlockPos farmTilePos = event.getPos().down();
        BlockState farmTileState = world.getBlockState(farmTilePos);
        ConfigObject configuration = Config.globalConfig;

        if (!world.isRemote()) {
            for (ConfigObject.CropConfiguration config : configuration.getConfigurations()) {
                if (config != null) {
                    if (config.getCropName() != null) {
                        if (!config.getCropName().isEmpty()) {
                            if (cropRL != null) {
                                if (config.getCropName().equals(cropRL.getNamespace() + ":" + cropRL.getPath())) {

                                    int requiredFertilizerAmountToGrow = config.getFertilizerAmount();
                                    boolean requireHighTierFarmTileToGrow = config.isRequireHighTier();
                                    int i = world.getBlockState(farmTilePos).get(FarmTileBlock.FERTILIZATION);
                                    int m = world.getBlockState(farmTilePos).get(FarmlandBlock.MOISTURE);

                                    if (m > 0) {
                                        if (i >= requiredFertilizerAmountToGrow) {
                                            if (!requireHighTierFarmTileToGrow) {
                                                world.setBlockState(farmTilePos, farmTileState.with(FarmTileBlock.FERTILIZATION, Integer.valueOf(i - requiredFertilizerAmountToGrow)), 2);
                                                event.setResult(Event.Result.ALLOW);
                                                return;
                                            }

                                            if (world.getBlockState(farmTilePos).getBlock() instanceof FarmTileBlock) {
                                                FarmTileBlock farmTileBlock = (FarmTileBlock) world.getBlockState(farmTilePos).getBlock();
                                                if (farmTileBlock.isAdvanced()) {
                                                    if (config.getRequiredFluid() == null || config.getRequiredFluid().isEmpty()) {
                                                        world.setBlockState(farmTilePos, farmTileState.with(FarmTileBlock.FERTILIZATION, Integer.valueOf(i - requiredFertilizerAmountToGrow)), 2);
                                                        event.setResult(Event.Result.ALLOW);
                                                        return;
                                                    }

                                                    int fluidAmount = config.getRequiredFluidAmount();
                                                    Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(config.getRequiredFluid()));
                                                    boolean consumeRequiredFluid = config.isConsumeFluid();

                                                    if (fluid != null) {
                                                        FluidStack requiredFluid = new FluidStack(fluid, fluidAmount);
                                                        if (requiredFluid != null) {
                                                            TileEntity tile = world.getTileEntity(farmTilePos);
                                                            if (tile instanceof HydroponicTileTE) {
                                                                HydroponicTileTE te = (HydroponicTileTE) tile;
                                                                if (te.getFluidTank().getFluid().isFluidEqual(requiredFluid)) {
                                                                    if (te.getFluidTank().getFluid().getAmount() >= requiredFluid.getAmount()) {
                                                                        world.setBlockState(farmTilePos, farmTileState.with(FarmTileBlock.FERTILIZATION, Integer.valueOf(i - requiredFertilizerAmountToGrow)), 2);
                                                                        if (consumeRequiredFluid) {
                                                                            te.getFluidTank().drain(fluidAmount, IFluidHandler.FluidAction.EXECUTE);
                                                                        }
                                                                        event.setResult(Event.Result.ALLOW);
                                                                        return;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        ImmersiveAgriculture.LOGGER.info("Blocked" + cropState.getBlock().getRegistryName().toString() + "from growing as it does not meet growing requirements");
        event.setResult(Event.Result.DENY);
    }
}
