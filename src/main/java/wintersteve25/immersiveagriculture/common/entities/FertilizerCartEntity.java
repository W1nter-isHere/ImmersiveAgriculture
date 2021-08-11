package wintersteve25.immersiveagriculture.common.entities;

//public class FertilizerCartEntity extends AnimalEntity implements IAnimatable, IFCDataGenObject<EntityType<?>> {
//
//    private AnimationFactory factory = new AnimationFactory(this);
//    private static final DataParameter<Integer> FERTILIZER_LEVEL = EntityDataManager.createKey(FertilizerCartEntity.class, DataSerializers.VARINT);
//
//    public FertilizerCartEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
//        super(type, worldIn);
//        this.preventEntitySpawning = true;
//    }
//
//    public FertilizerCartEntity(World worldIn, double x, double y, double z) {
//        this(IAEntities.FERTILIZER_CART.get(), worldIn);
//        this.setPosition(x, y, z);
//        this.setMotion(Vector3d.ZERO);
//        this.prevPosX = x;
//        this.prevPosY = y;
//        this.prevPosZ = z;
//    }
//
//    @Override
//    protected void registerData() {
//        this.dataManager.register(FERTILIZER_LEVEL, 0);
//        super.registerData();
//    }
//
//    @Override
//    public boolean attackEntityFrom(DamageSource source, float amount) {
//        if (this.isInvulnerableTo(source)) {
//            return false;
//        } else if (!this.world.isRemote && !this.removed) {
//            this.markVelocityChanged();
//            this.entityDropItem(IAItems.FERTILIZER_CART);
//            this.remove();
//            return true;
//        } else {
//            return true;
//        }
//    }
//
//    @Override
//    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
//        if (!this.isBeingRidden()) {
//            if (player.getHeldItem(hand).getItem() != IAItems.FERTILIZER) {
//                player.startRiding(this);
//            } else {
//                setFertilizerLevel(getFertilizerLevel() + 1);
//            }
//        }
//        return super.func_230254_b_(player, hand);
//    }
//
//    @Override
//    public void travel(Vector3d pos) {
//        if (this.isAlive() && this.isBeingRidden()) {
//            LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
//
//            float f = livingentity.moveStrafing * 0.5F;
//            float f1 = livingentity.moveForward;
//            if (f1 <= 0.0F) {
//                f1 *= 0.25F;
//            }
//
//            this.setAIMoveSpeed(0.1F);
//            super.travel(new Vector3d(f, pos.y, f1));
//        }
//    }
//
//    public void setFertilizerLevel(int fertilizerLevel) {
//        this.dataManager.set(FERTILIZER_LEVEL, fertilizerLevel);
//    }
//
//    public int getFertilizerLevel() {
//        return this.dataManager.get(FERTILIZER_LEVEL);
//    }
//
//    @Override
//    public boolean canDespawn(double distanceToClosestPlayer) {
//        return false;
//    }
//
//    @Override
//    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
//        return false;
//    }
//
//    @Nullable
//    @Override
//    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
//        return null;
//    }
//
//    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        event.getController().setAnimation((new AnimationBuilder()).addAnimation("fertilizer_cart.idle", true));
//        return PlayState.CONTINUE;
//    }
//
//    public void registerControllers(AnimationData data) {
//        data.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
//    }
//
//    public AnimationFactory getFactory() {
//        return this.factory;
//    }
//
//    @Override
//    public String regName() {
//        return "Fertilizer Cart";
//    }
//
//    @Override
//    public EntityType<?> getOg() {
//        return IAEntities.FERTILIZER_CART.get();
//    }
//}
