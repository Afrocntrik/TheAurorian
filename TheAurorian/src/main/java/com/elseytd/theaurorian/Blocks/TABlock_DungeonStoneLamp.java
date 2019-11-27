package com.elseytd.theaurorian.Blocks;

import com.elseytd.theaurorian.TAMod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TABlock_DungeonStoneLamp extends Block {

	public static final String BLOCKNAME_RUNESTONE = "runestonelamp";
	public static final String BLOCKNAME_MOONTEMPLE = "moontemplelamp";
	public static final String BLOCKNAME_DARK = "darkstonelamp";

	public TABlock_DungeonStoneLamp(String blockname) {
		super(Material.ROCK);
		this.setCreativeTab(TAMod.CREATIVE_TAB);
		this.setHardness(500F);
		this.setSoundType(SoundType.STONE);
		this.setUnlocalizedName(TAMod.MODID + "." + blockname);
		this.setRegistryName(blockname);
		this.setLightLevel(1F);
	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

}
