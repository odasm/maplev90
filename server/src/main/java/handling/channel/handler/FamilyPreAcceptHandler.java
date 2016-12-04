package handling.channel.handler;

import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import handling.world.World;
import handling.world.family.MapleFamily;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.FamilyPacket;

public class FamilyPreAcceptHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleFamily fam = World.Family.getFamily(c.getPlayer().getFamilyId());
		if (fam == null || fam.getLeaderId() != c.getPlayer().getId()) {
			return;
		}
		fam.setNotice(slea.readMapleAsciiString());
		c.getSession().write(FamilyPacket.getFamilyInfo(c.getPlayer()));

	}

}
