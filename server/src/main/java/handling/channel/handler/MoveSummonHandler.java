package handling.channel.handler;

import java.awt.Point;
import java.util.List;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import server.maps.MapleSummon;
import server.maps.SummonMovementType;
import server.movement.LifeMovementFragment;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class MoveSummonHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		final int oid = slea.readInt();
		slea.skip(8); // startPOS
		MapleCharacter chr = c.getPlayer();
		final List<LifeMovementFragment> res = MovementParse.parseMovement(slea, 4);
		if (chr == null) {
			return;
		}
		for (MapleSummon sum : chr.getSummons().values()) {
			if (sum.getObjectId() == oid && sum.getMovementType() != SummonMovementType.STATIONARY) {
				final Point pos = sum.getPosition();
				MovementParse.updatePosition(res, sum, 0);
				chr.getMap().broadcastMessage(chr, MaplePacketCreator.moveSummon(chr.getId(), oid, pos, res),
						sum.getPosition());
				break;
			}
		}

	}

}
