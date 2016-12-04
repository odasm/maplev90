package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import handling.world.family.MapleFamilyBuff;
import handling.world.family.MapleFamilyBuff.MapleFamilyBuffEntry;
import server.maps.FieldLimitType;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.FamilyPacket;

public class FamilySummonHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		int TYPE = 1; // the type of the summon request.
		MapleFamilyBuffEntry cost = MapleFamilyBuff.getBuffEntry(TYPE);
		MapleCharacter tt = c.getChannelServer().getPlayerStorage().getCharacterByName(slea.readMapleAsciiString());
		if (c.getPlayer().getFamilyId() > 0 && tt != null && tt.getFamilyId() == c.getPlayer().getFamilyId()
				&& !FieldLimitType.VipRock.check(tt.getMap().getFieldLimit())
				&& !FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit()) && c.getPlayer().isAlive()
				&& tt.isAlive() && tt.canUseFamilyBuff(cost) && c.getPlayer().getTeleportName().equals(tt.getName())
				&& tt.getCurrentRep() > cost.rep && c.getPlayer().getEventInstance() == null
				&& tt.getEventInstance() == null) {
			// whew lots of checks
			boolean accepted = slea.readByte() > 0;
			if (accepted) {
				c.getPlayer().changeMap(tt.getMap(), tt.getMap().getPortal(0));
				tt.setCurrentRep(tt.getCurrentRep() - cost.rep);
				tt.getClient().getSession().write(FamilyPacket.changeRep(-cost.rep, tt.getName()));
				tt.useFamilyBuff(cost);
			} else {
				tt.getClient().getSession().write(FamilyPacket.FamilyMessage((byte) 0x4B, 0));
			}
		} else {
			c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x4B, 0));
		}
		c.getPlayer().setTeleportName("");

	}

}
