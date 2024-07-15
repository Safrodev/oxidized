package safro.oxidized.entity;

import net.minecraft.block.Oxidizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import safro.oxidized.entity.goal.MoveToLightningRodGoal;
import safro.oxidized.entity.goal.UseButtonGoal;

public class CopperGolemEntity extends GolemEntity {
    private static final TrackedData<Boolean> IS_PRESSING_BUTTONS;
    private Oxidizable.OxidationLevel oxidizationLevel;
    private int tickOxidization = 0;

    public CopperGolemEntity(EntityType<? extends CopperGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MoveToLightningRodGoal(this, 0.6D));
        this.goalSelector.add(2, new UseButtonGoal(this, 0.8D));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.4));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createCopperGolemAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.27D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.75D);
    }

    public void setPressingButtons(boolean pressing) {
        this.dataTracker.set(IS_PRESSING_BUTTONS, pressing);
    }

    public boolean isPressingButtons() {
        return this.dataTracker.get(IS_PRESSING_BUTTONS);
    }

    public Oxidizable.OxidationLevel getOxidizationLevel() {
        return oxidizationLevel;
    }

    public boolean isStatue() {
        return oxidizationLevel.equals(Oxidizable.OxidationLevel.OXIDIZED);
    }

    @Override
    public void tick() {
        super.tick();
        this.oxidizationTick();
        if (this.oxidizationLevel != Oxidizable.OxidationLevel.OXIDIZED) {
            ++tickOxidization;
        }
        this.setAiDisabled(this.isStatue());
    }

    protected void oxidizationTick() {
        if (tickOxidization == 1200000) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.OXIDIZED;
        } else if (tickOxidization == 800000) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.WEATHERED;
        } else if (tickOxidization == 400000) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.EXPOSED;
        } else if (this.tickOxidization < 400000) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_PRESSING_BUTTONS, false);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        World world = player.getWorld();
        ItemStack stack = player.getStackInHand(hand);
        if (stack.getItem() instanceof AxeItem && this.oxidizationLevel != Oxidizable.OxidationLevel.UNAFFECTED) {
            this.degradeLevel();
            world.playSound(player, player.getBlockPos(), SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(player, 3005, player.getBlockPos(), 0);
            stack.damage(1, player, hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void degradeLevel() {
        if (this.oxidizationLevel == Oxidizable.OxidationLevel.OXIDIZED) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.WEATHERED;
        } else if (this.oxidizationLevel == Oxidizable.OxidationLevel.WEATHERED) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.EXPOSED;
        } else if (this.oxidizationLevel == Oxidizable.OxidationLevel.EXPOSED) {
            this.oxidizationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
        }
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        super.onStruckByLightning(world, lightning);
        this.tickOxidization = 0;
        this.oxidizationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
        ParticleUtil.spawnParticle(this.getMovementDirection().getAxis(), world, this.getBlockPos(), 0.125D, ParticleTypes.ELECTRIC_SPARK, UniformIntProvider.create(1, 2));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (source.getAttacker() instanceof LightningEntity) {
            return false;
        } else
            return bl;
    }

    @Override
    protected void pushAway(Entity entity) {
        if (entity instanceof PlayerEntity) {
            super.pushAway(entity);
        }
    }

    @Override
    protected int getNextAirUnderwater(int air) {
        return air;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_IRON_GOLEM_DEATH;
    }

    static {
        IS_PRESSING_BUTTONS = DataTracker.registerData(CopperGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
