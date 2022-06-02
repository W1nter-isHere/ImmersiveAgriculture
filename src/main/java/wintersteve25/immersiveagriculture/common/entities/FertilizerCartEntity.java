package wintersteve25.immersiveagriculture.common.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import wintersteve25.immersiveagriculture.common.block.farm_tile.FarmTileBlock;
import wintersteve25.immersiveagriculture.common.init.IAEntities;
import wintersteve25.immersiveagriculture.common.init.IAItems;

import javax.annotation.Nullable;

public class FertilizerCartEntity extends AnimalEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final DataParameter<Integer> FERTILIZER_LEVEL = EntityDataManager.createKey(FertilizerCartEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(FertilizerCartEntity.class, DataSerializers.BOOLEAN);

    public FertilizerCartEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
    }

    public FertilizerCartEntity(World worldIn, double x, double y, double z) {
        this(IAEntities.FERTILIZER_CART.get(), worldIn);
        this.setPosition(x, y, z);
        this.setMotion(Vector3d.ZERO);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(FERTILIZER_LEVEL, 0);
        this.dataManager.register(MOVING, false);
        super.registerData();
    }

    public static AttributeModifierMap.MutableAttribute setAttribute() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 1F);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isRemote && !this.removed) {
            this.markVelocityChanged();
            this.entityDropItem(IAItems.FERTILIZER_CART);
            if (getFertilizerLevel() > 0) {
                this.entityDropItem(new ItemStack(IAItems.FERTILIZER, getFertilizerLevel()));
            }
            this.remove();
        }
        return true;
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        setFertilizerLevel(compound.getInt("FertilizerLevel"));
        super.readAdditional(compound);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("FertilizerLevel", getFertilizerLevel());
        super.writeAdditional(compound);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        if (!this.isBeingRidden()) {

            if (player.isSneaking() && getFertilizerLevel() > 0) {
                player.addItemStackToInventory(new ItemStack(IAItems.FERTILIZER, getFertilizerLevel()));
                setFertilizerLevel(0);
                return ActionResultType.SUCCESS;
            }
            if (player.getHeldItem(hand).getItem() != IAItems.FERTILIZER) {
//                this.startRiding(player);
                player.startRiding(this);
                return ActionResultType.SUCCESS;
            } else {
                if (getFertilizerLevel() < 64) {
                    setFertilizerLevel(getFertilizerLevel() + 1);
                    player.getHeldItem(hand).shrink(1);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(IAItems.FERTILIZER_CART);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isBeingRidden()) {
            setMoving(false);
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
    }

    @Override
    public double getMountedYOffset() {
        return 0.2;
    }

    @Override
    public void travel(Vector3d pos) {
        if (this.isAlive() && this.isBeingRidden()) {
            LivingEntity passenger = (LivingEntity) this.getControllingPassenger();

            this.rotationYaw = passenger.rotationYaw;
            this.prevRotationYaw = passenger.prevRotationYaw;

            float f = passenger.moveStrafing * 0.5F;
            float f1 = passenger.moveForward;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }

            BlockState state = world.getBlockState(getPosition().down());
            if (state.getBlock() instanceof FarmTileBlock) {
                int i = state.get(FarmTileBlock.FERTILIZATION);

                if (i < 4 && getFertilizerLevel() > 0) {
                    world.setBlockState(getPosition().down(), state.with(FarmTileBlock.FERTILIZATION, i+1), 2);
                    this.setFertilizerLevel(getFertilizerLevel()-1);
                }
            }

            if (getMotion().x > 0 || getMotion().y > 0 || getMotion().z > 0) {
                setMoving(true);
            }

            this.setAIMoveSpeed(0.1F);
            super.travel(new Vector3d(f, pos.y, f1));
        }
    }

    @Override
    public boolean canBeSteered() {
        return false;
    }

    public void setFertilizerLevel(int fertilizerLevel) {
        this.dataManager.set(FERTILIZER_LEVEL, fertilizerLevel);
    }

    public int getFertilizerLevel() {
        return this.dataManager.get(FERTILIZER_LEVEL);
    }

    public void setMoving(boolean moving) {
        this.dataManager.set(MOVING, moving);
    }

    public boolean getMoving() {
        return this.dataManager.get(MOVING);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        return false;
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (getMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fertilizer_cart.start", false).addAnimation("fertilizer_cart.move", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder());
        }
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
    }

    public AnimationFactory getFactory() {
        return this.factory;
    }
}
