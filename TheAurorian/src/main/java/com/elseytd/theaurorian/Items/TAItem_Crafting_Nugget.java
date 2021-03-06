package com.elseytd.theaurorian.Items;

import com.elseytd.theaurorian.TAMod;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TAItem_Crafting_Nugget extends Item {

	public static final String ITEMNAME_CERULEAN = "ceruleannugget";
	public static final String ITEMNAME_MOONSTONE = "moonstonenugget";
	public static final String ITEMNAME_COAL = "auroriancoalnugget";

	public TAItem_Crafting_Nugget(String name) {
		this.setCreativeTab(TAMod.CREATIVE_TAB);
		this.setRegistryName(name);
		this.setUnlocalizedName(TAMod.MODID + "." + name);

	}

	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		if (this.getRegistryName().toString().contains(ITEMNAME_COAL)) {
			return 200;
		} else {
			return -1;
		}
	}
}
