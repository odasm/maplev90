package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import server.maps.FieldLimitType;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MTSCSPacket;

public class TrockAddMapHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter chr = c.getPlayer();
		final byte addrem = slea.readByte();
		final byte vip = slea.readByte();

		if (vip == 1) {
			if (addrem == 0) {
				chr.deleteFromRocks(slea.readInt());
			} else if (addrem == 1) {
				if (!FieldLimitType.VipRock.check(chr.getMap().getFieldLimit())) {
					chr.addRockMap();
				} else {
					chr.dropMessage(1, "You may not add this map.");
				}
			}
		} else {
			if (addrem == 0) {
				chr.deleteFromRegRocks(slea.readInt());
			} else if (addrem == 1) {
				if (!FieldLimitType.VipRock.check(chr.getMap().getFieldLimit())) {
					chr.addRegRockMap();
				} else {
					chr.dropMessage(1, "You may not add this map.");
				}
			}
		}
		c.getSession().write(MTSCSPacket.getTrockRefresh(chr, vip == 1, addrem == 3));

	}

}
