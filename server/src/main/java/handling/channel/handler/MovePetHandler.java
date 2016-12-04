package handling.channel.handler;

import java.util.List;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import server.movement.LifeMovementFragment;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.PetPacket;

public class MovePetHandler extends AbstractMaplePacketHandler{

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		final int petId = slea.readInt();
		slea.skip(12);
		MapleCharacter chr = c.getPlayer();
		final List<LifeMovementFragment> res = MovementParse.parseMovement(slea, 3);

		if (res != null && chr != null && res.size() != 0) { // map crash hack
			final byte slot = chr.getPetIndex(petId);
			if (slot == -1) {
				return;
			}
			chr.getPet(slot).updatePosition(res);
			chr.getMap().broadcastMessage(chr, PetPacket.movePet(chr.getId(), petId, slot, res), false);
		}
		
	}

}
