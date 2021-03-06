package meldexun.better_diving.network.handler;

import meldexun.better_diving.BetterDiving;
import meldexun.better_diving.entity.EntitySeamoth;
import meldexun.better_diving.network.packet.CPacketSyncSeamothInput;
import meldexun.better_diving.network.packet.SPacketSyncSeamothInput;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SPacketHandlerSyncSeamothInput implements IMessageHandler<CPacketSyncSeamothInput, IMessage> {

	@Override
	public IMessage onMessage(CPacketSyncSeamothInput message, MessageContext ctx) {
		FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
			if (ctx.side.isServer()) {
				EntityPlayer player = BetterDiving.proxy.getPlayer(ctx);
				World world = BetterDiving.proxy.getWorld(ctx);
				Entity entity = world.getEntityByID(message.getEntityId());

				if (entity instanceof EntitySeamoth) {
					EntitySeamoth seamoth = (EntitySeamoth) entity;

					seamoth.inputForward = message.getInputForward();
					seamoth.inputRight = message.getInputRight();
					seamoth.inputBack = message.getInputBack();
					seamoth.inputLeft = message.getInputLeft();
					seamoth.inputUp = message.getInputUp();
					seamoth.inputDown = message.getInputDown();
					seamoth.rotationYaw = message.getYaw();
					seamoth.rotationPitch = message.getPitch();

					BetterDiving.network.sendToAllTracking(new SPacketSyncSeamothInput(seamoth), entity);
				}
			}
		});
		return null;
	}

}
