package com.elseytd.theaurorian;

import com.elseytd.theaurorian.Entities.TAEntityRender_CeruleanArrow;
import com.elseytd.theaurorian.Entities.TAEntityRender_CrystallineSprite;
import com.elseytd.theaurorian.Entities.TAEntityRender_StickySpiker;
import com.elseytd.theaurorian.Entities.TAEntity_CeruleanArrow;
import com.elseytd.theaurorian.Entities.TAEntity_CrystallineSprite;
import com.elseytd.theaurorian.Entities.TAEntity_StickySpiker;
import com.elseytd.theaurorian.Entities.AurorianPig.TAEntityRender_AurorianPig;
import com.elseytd.theaurorian.Entities.AurorianPig.TAEntity_AurorianPig;
import com.elseytd.theaurorian.Entities.AurorianRabbit.TAEntityRender_AurorianRabbit;
import com.elseytd.theaurorian.Entities.AurorianRabbit.TAEntity_AurorianRabbit;
import com.elseytd.theaurorian.Entities.AurorianSheep.TAEntityRender_AurorianSheep;
import com.elseytd.theaurorian.Entities.AurorianSheep.TAEntity_AurorianSheep;
import com.elseytd.theaurorian.Entities.AurorianSlime.TAEntityRender_AurorianSlime;
import com.elseytd.theaurorian.Entities.AurorianSlime.TAEntity_AurorianSlime;
import com.elseytd.theaurorian.Entities.Hollow.TAEntityRender_DisturbedHollow;
import com.elseytd.theaurorian.Entities.Hollow.TAEntity_DisturbedHollow;
import com.elseytd.theaurorian.Entities.Keeper.TAEntityRender_RunestoneDungeonKeeper;
import com.elseytd.theaurorian.Entities.Keeper.TAEntity_RunestoneDungeonKeeper;
import com.elseytd.theaurorian.Entities.MoonAcolyte.TAEntityRender_MoonAcolyte;
import com.elseytd.theaurorian.Entities.MoonAcolyte.TAEntity_MoonAcolyte;
import com.elseytd.theaurorian.Entities.MoonQueen.TAEntityRender_MoonQueen;
import com.elseytd.theaurorian.Entities.MoonQueen.TAEntity_MoonQueen;
import com.elseytd.theaurorian.Entities.UndeadKnight.TAEntityRender_UndeadKnight;
import com.elseytd.theaurorian.Entities.UndeadKnight.TAEntity_UndeadKnight;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TAEntities {
	public static void init() {
		int id = 0;

		quickRegEntity(TAEntity_DisturbedHollow.class, TAEntity_DisturbedHollow.EntityName, id++);
		quickRegEntity(TAEntity_RunestoneDungeonKeeper.class, TAEntity_RunestoneDungeonKeeper.EntityName, id++);
		quickRegEntity(TAEntity_UndeadKnight.class, TAEntity_UndeadKnight.EntityName, id++);
		quickRegEntity(TAEntity_MoonAcolyte.class, TAEntity_MoonAcolyte.EntityName, id++);
		quickRegEntity(TAEntity_MoonQueen.class, TAEntity_MoonQueen.EntityName, id++);
		quickRegEntity(TAEntity_AurorianRabbit.class, TAEntity_AurorianRabbit.EntityName, id++);
		quickRegEntity(TAEntity_AurorianSheep.class, TAEntity_AurorianSheep.EntityName, id++);
		quickRegEntity(TAEntity_AurorianPig.class, TAEntity_AurorianPig.EntityName, id++);
		quickRegEntity(TAEntity_AurorianSlime.class, TAEntity_AurorianSlime.EntityName, id++);
		quickRegNonlivingEntity(TAEntity_StickySpiker.class, TAEntity_StickySpiker.EntityName, id++);
		quickRegNonlivingEntity(TAEntity_CeruleanArrow.class, TAEntity_CeruleanArrow.EntityName, id++);
		quickRegNonlivingEntity(TAEntity_CrystallineSprite.class, TAEntity_CrystallineSprite.EntityName, id++);

	}

	private static void quickRegEntity(Class<? extends Entity> mob, String mobname, int id) {
		EntityRegistry.registerModEntity(new ResourceLocation(TAMod.MODID, mobname), mob, TAMod.MODID + "." + mobname, id, TAMod.INSTANCE, 64, 3, true, 0xade0f5, 0x3b4759);
	}

	private static void quickRegNonlivingEntity(Class<? extends Entity> mob, String mobname, int id) {
		EntityRegistry.registerModEntity(new ResourceLocation(TAMod.MODID, mobname), mob, TAMod.MODID + "." + mobname, id, TAMod.INSTANCE, 64, 3, true);
	}

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_DisturbedHollow.class, TAEntityRender_DisturbedHollow.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_RunestoneDungeonKeeper.class, TAEntityRender_RunestoneDungeonKeeper.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_UndeadKnight.class, TAEntityRender_UndeadKnight.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_MoonAcolyte.class, TAEntityRender_MoonAcolyte.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_MoonQueen.class, TAEntityRender_MoonQueen.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_AurorianRabbit.class, TAEntityRender_AurorianRabbit.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_AurorianSheep.class, TAEntityRender_AurorianSheep.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_AurorianPig.class, TAEntityRender_AurorianPig.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_AurorianSlime.class, TAEntityRender_AurorianSlime.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_StickySpiker.class, TAEntityRender_StickySpiker.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_CeruleanArrow.class, TAEntityRender_CeruleanArrow.FACTORY);
		RenderingRegistry.registerEntityRenderingHandler(TAEntity_CrystallineSprite.class, TAEntityRender_CrystallineSprite.FACTORY);
	}
}
