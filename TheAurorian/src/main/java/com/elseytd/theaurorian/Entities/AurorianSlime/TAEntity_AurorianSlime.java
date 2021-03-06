package com.elseytd.theaurorian.Entities.AurorianSlime;

import javax.annotation.Nullable;

import com.elseytd.theaurorian.TAItems;
import com.elseytd.theaurorian.TAMod;
import com.elseytd.theaurorian.TAParticles;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class TAEntity_AurorianSlime extends EntityLiving implements IMob {

	public static final String EntityName = "aurorianslime";
	public static final ResourceLocation LOOT = new ResourceLocation(TAMod.MODID, "entities/" + EntityName);
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	private boolean wasOnGround;

	public TAEntity_AurorianSlime(World worldIn) {
		super(worldIn);
		this.moveHelper = new TAEntity_AurorianSlime.SlimeMoveHelper(this);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new TAEntity_AurorianSlime.AISlimeFloat(this));
		this.tasks.addTask(2, new TAEntity_AurorianSlime.AISlimeAttack(this));
		this.tasks.addTask(3, new TAEntity_AurorianSlime.AISlimeFaceRandom(this));
		this.tasks.addTask(5, new TAEntity_AurorianSlime.AISlimeHop(this));
		this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
	}

	protected void setSlimeSize(int size, boolean resetHealth) {
		this.setSize(0.51000005F * (float) size, 0.51000005F * (float) size);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) (6));
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) (0.2F + 0.1F * (float) 3));
		if (resetHealth) {
			this.setHealth(this.getMaxHealth());
		}
		this.experienceValue = 3;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	public static void registerFixesSlime(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, TAEntity_AurorianSlime.class);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setBoolean("wasOnGround", this.wasOnGround);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.wasOnGround = compound.getBoolean("wasOnGround");
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if (net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier) == null) {
			return;
		}
		super.fall(0, 0);
	}

	@Override
	public void onUpdate() {
		if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.isDead = true;
		}
		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;
		super.onUpdate();
		if (this.onGround && !this.wasOnGround) {
			int i = 1;
			for (int j = 0; j < i * 8; ++j) {
				if (this.world.isRemote) {
					TAParticles.spawnParticle(TAParticles.Particles.AURORIANSLIME, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				}
			}
			this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			this.squishAmount = -0.5F;
		} else if (!this.onGround && this.wasOnGround) {
			this.squishAmount = 1.0F;
		}

		this.wasOnGround = this.onGround;
		this.alterSquishAmount();
	}

	protected void alterSquishAmount() {
		this.squishAmount *= 0.6F;
	}

	protected int getJumpDelay() {
		return this.rand.nextInt(20) + 10;
	}

	protected TAEntity_AurorianSlime createInstance() {
		return new TAEntity_AurorianSlime(this.world);
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		double i = 2;
		if (this.canEntityBeSeen(entityIn) && this.getDistanceSq(entityIn) < 0.6D * i * 0.6D * i && entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttackStrength())) {
			this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.applyEnchantments(this, entityIn);
		}
	}

	@Override
	public float getEyeHeight() {
		return 0.625F * this.height;
	}

	protected int getAttackStrength() {
		return 2;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_SMALL_SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SMALL_SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		return SoundEvents.ENTITY_SMALL_SLIME_SQUISH;
	}

	protected SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_SMALL_SLIME_JUMP;
	}

	@Override
	protected Item getDropItem() {
		return TAItems.aurorianslimeball;
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return LOOT;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public int getVerticalFaceSpeed() {
		return 0;
	}

	protected boolean makesSoundOnJump() {
		return true;
	}

	@Override
	protected void jump() {
		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		this.setSlimeSize(1, true);
		return super.onInitialSpawn(difficulty, livingdata);
	}

	static class AISlimeAttack extends EntityAIBase {
		private final TAEntity_AurorianSlime slime;
		private int growTieredTimer;

		public AISlimeAttack(TAEntity_AurorianSlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
			if (entitylivingbase == null) {
				return false;
			} else if (!entitylivingbase.isEntityAlive()) {
				return false;
			} else {
				return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).capabilities.disableDamage;
			}
		}

		@Override
		public void startExecuting() {
			this.growTieredTimer = 300;
			super.startExecuting();
		}

		@Override
		public boolean shouldContinueExecuting() {
			EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
			if (entitylivingbase == null) {
				return false;
			} else if (!entitylivingbase.isEntityAlive()) {
				return false;
			} else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).capabilities.disableDamage) {
				return false;
			} else {
				return --this.growTieredTimer > 0;
			}
		}

		@Override
		public void updateTask() {
			this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
			((TAEntity_AurorianSlime.SlimeMoveHelper) this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, true);
		}
	}

	static class AISlimeFaceRandom extends EntityAIBase {
		private final TAEntity_AurorianSlime slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public AISlimeFaceRandom(TAEntity_AurorianSlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			return this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(MobEffects.LEVITATION));
		}

		@Override
		public void updateTask() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
				this.chosenDegrees = (float) this.slime.getRNG().nextInt(360);
			}
			((TAEntity_AurorianSlime.SlimeMoveHelper) this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
		}
	}

	static class AISlimeFloat extends EntityAIBase {
		private final TAEntity_AurorianSlime slime;

		public AISlimeFloat(TAEntity_AurorianSlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(5);
			((PathNavigateGround) slimeIn.getNavigator()).setCanSwim(true);
		}

		@Override
		public boolean shouldExecute() {
			return this.slime.isInWater() || this.slime.isInLava();
		}

		@Override
		public void updateTask() {
			if (this.slime.getRNG().nextFloat() < 0.8F) {
				this.slime.getJumpHelper().setJumping();
			}
			((TAEntity_AurorianSlime.SlimeMoveHelper) this.slime.getMoveHelper()).setSpeed(1.2D);
		}
	}

	static class AISlimeHop extends EntityAIBase {
		private final TAEntity_AurorianSlime slime;

		public AISlimeHop(TAEntity_AurorianSlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(5);
		}

		@Override
		public boolean shouldExecute() {
			return true;
		}

		@Override
		public void updateTask() {
			((TAEntity_AurorianSlime.SlimeMoveHelper) this.slime.getMoveHelper()).setSpeed(1.5D);
		}
	}

	static class SlimeMoveHelper extends EntityMoveHelper {
		private float yRot;
		private int jumpDelay;
		private final TAEntity_AurorianSlime slime;
		private boolean isAggressive;

		public SlimeMoveHelper(TAEntity_AurorianSlime slimeIn) {
			super(slimeIn);
			this.slime = slimeIn;
			this.yRot = 180.0F * slimeIn.rotationYaw / (float) Math.PI;
		}

		public void setDirection(float p_179920_1_, boolean p_179920_2_) {
			this.yRot = p_179920_1_;
			this.isAggressive = p_179920_2_;
		}

		public void setSpeed(double speedIn) {
			this.speed = speedIn;
			this.action = EntityMoveHelper.Action.MOVE_TO;
		}

		public void onUpdateMoveHelper() {
			this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
			this.entity.rotationYawHead = this.entity.rotationYaw;
			this.entity.renderYawOffset = this.entity.rotationYaw;
			if (this.action != EntityMoveHelper.Action.MOVE_TO) {
				this.entity.setMoveForward(0.0F);
			} else {
				this.action = EntityMoveHelper.Action.WAIT;
				if (this.entity.onGround) {
					this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
					if (this.jumpDelay-- <= 0) {
						this.jumpDelay = this.slime.getJumpDelay();
						if (this.isAggressive) {
							this.jumpDelay /= 3;
						}
						this.slime.getJumpHelper().setJumping();
						this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
					} else {
						this.slime.moveStrafing = 0.0F;
						this.slime.moveForward = 0.0F;
						this.entity.setAIMoveSpeed(0.0F);
					}
				} else {
					this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
				}
			}
		}
	}

}
