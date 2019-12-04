package com.elseytd.theaurorian;

import com.elseytd.theaurorian.Particles.TAParticle_AurorianSlime;
import com.elseytd.theaurorian.Particles.TAParticle_StickySpiker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;

public class TAParticles {

	public enum Particles {
		AURORIANSLIME, STICKYSPIKER
	}

	private static Minecraft mcinstance = Minecraft.getMinecraft();

	public static Particle spawn(Particles type, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		if (mcinstance != null && mcinstance.getRenderViewEntity() != null && mcinstance.effectRenderer != null) {
			int particlesetting = mcinstance.gameSettings.particleSetting;

			if (particlesetting == 1 && mcinstance.world.rand.nextInt(3) == 0) {
				particlesetting = 2;
			}

			double boundsx = mcinstance.getRenderViewEntity().posX - xCoordIn;
			double boundsy = mcinstance.getRenderViewEntity().posY - yCoordIn;
			double boundsz = mcinstance.getRenderViewEntity().posZ - zCoordIn;;
			double bound = 16.0D;

			if (boundsx * boundsx + boundsy * boundsy + boundsz * boundsz > bound * bound) {
				return null;
			} else if (particlesetting > 1) {
				return null;
			} else {
				Particle particle = null;
				switch (type) {
				case AURORIANSLIME:
					particle = new TAParticle_AurorianSlime(mcinstance.world, xCoordIn, yCoordIn, zCoordIn);
					break;
				case STICKYSPIKER:
					particle = new TAParticle_StickySpiker(mcinstance.world, xCoordIn, yCoordIn, zCoordIn);
					break;
				default:
					particle = null;
					break;
				}
				mcinstance.effectRenderer.addEffect(particle);
				return particle;
			}
		}
		return null;
	}
}
