package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.FamilyPacket;

public class FamilyOperationHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		if (c.getPlayer() == null) {
			return;
		}
		MapleCharacter addChr = c.getChannelServer().getPlayerStorage().getCharacterByName(slea.readMapleAsciiString());
		if (addChr == null) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x41, 0));
		} else if (addChr.getFamilyId() == c.getPlayer().getFamilyId() && addChr.getFamilyId() > 0) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x42, 0));
		} else if (addChr.getMapId() != c.getPlayer().getMapId()) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x45, 0));
		} else if (addChr.getSeniorId() != 0) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x46, 0));
		} else if (addChr.getLevel() >= c.getPlayer().getLevel()) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x47, 0));
		} else if (addChr.getLevel() < c.getPlayer().getLevel() - 20) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x48, 0));
			// } else if (c.getPlayer().getFamilyId() != 0 &&
			// c.getPlayer().getFamily().getMemberSize() >= 1000) {
			// c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x4C, 0));
		} else if (addChr.getLevel() < 10) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x4D, 0));
		} else if ((c.getPlayer().getJunior1() > 0 && c.getPlayer().getJunior2() > 0)
				|| (!c.getPlayer().isGM() && addChr.isGM())) {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x40, 0));
		} else if (c.getPlayer().isGM() || !addChr.isGM()) {
			addChr.getClient().getSession().write(FamilyPacket.sendFamilyInvite(c.getPlayer().getId(),
					c.getPlayer().getLevel(), c.getPlayer().getJob(), c.getPlayer().getName()));
		}
		c.getSession().write(MaplePacketCreator.enableActions());

	}

}
