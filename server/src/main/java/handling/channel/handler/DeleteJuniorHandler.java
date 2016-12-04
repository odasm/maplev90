package handling.channel.handler;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import constants.GameConstants;
import handling.AbstractMaplePacketHandler;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyCharacter;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.FamilyPacket;

public class DeleteJuniorHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		int juniorid = slea.readInt();
		if (c.getPlayer().getFamilyId() <= 0 || juniorid <= 0
				|| (c.getPlayer().getJunior1() != juniorid && c.getPlayer().getJunior2() != juniorid)) {
			return;
		}
		// junior is not required to be online.
		final MapleFamily fam = World.Family.getFamily(c.getPlayer().getFamilyId());
		final MapleFamilyCharacter other = fam.getMFC(juniorid);
		if (other == null) {
			return;
		}

		int diff = 0;
		if (c.getPlayer().getLevel() > other.getLevel()) { // char > junior
															// level = needd pay
			diff = c.getPlayer().getLevel() - other.getLevel();
		}
		int mesosReq = ((2501 * diff) + ((diff - 1) * diff));
		if (diff > 0) { // need mesos
			if (c.getPlayer().getMeso() < mesosReq || mesosReq <= 0) {
				c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x51, mesosReq));
				return;
			}
			c.getPlayer().gainMeso(-mesosReq, true, false, true);
		}
		int repCostOnMe = (GameConstants.getFamilyMultiplier(other.getLevel()) * other.getLevel());
		int repCostOnSenior = (GameConstants.getFamilyMultiplier(other.getLevel()) * other.getLevel()) / 2;

		int sensen = World.Family.minusRep(c.getPlayer().getFamilyId(), c.getPlayer().getId(), repCostOnMe);
		if (sensen > 0) {
			World.Family.minusRep(c.getPlayer().getFamilyId(), c.getPlayer().getSeniorId(), repCostOnSenior);
		}

		final MapleFamilyCharacter oth = c.getPlayer().getMFC();
		boolean junior2 = oth.getJunior2() == juniorid;
		if (junior2) {
			oth.setJunior2(0);
		} else {
			oth.setJunior1(0);
		}
		c.getPlayer().saveFamilyStatus();
		other.setSeniorId(0);
		MapleFamily.setOfflineFamilyStatus(other.getFamilyId(), other.getSeniorId(), other.getJunior1(),
				other.getJunior2(), other.getCurrentRep(), other.getTotalRep(), other.getId());
		MapleCharacterUtil.sendNote(other.getName(), c.getPlayer().getName(), c.getPlayer().getName()
				+ " has requested to sever ties with you, so the family relationship has ended.", 0);

		if (!fam.splitFamily(juniorid, other)) { // juniorid splits to make
													// their own family.
													// function should handle
													// the rest
			if (!junior2) {
				fam.resetDescendants();
			}
			fam.resetPedigree();
		}

		MapleCharacter ochr = c.getChannelServer().getPlayerStorage().getCharacterById(juniorid);
		if (ochr != null) {
			ochr.getClient().getSession().write(FamilyPacket.getFamilyInfo(ochr));
			ochr.getClient().getSession().write(FamilyPacket.getFamilyPedigree(ochr));
		}

		c.getPlayer().dropMessage(1,
				"You have severed ties with (" + other.getName() + "). Your family relationship has ended.");
		c.getSession().write(FamilyPacket.getFamilyInfo(c.getPlayer()));
		c.getSession().write(FamilyPacket.getFamilyPedigree(c.getPlayer()));
		c.getSession().write(MaplePacketCreator.enableActions());

	}

}
