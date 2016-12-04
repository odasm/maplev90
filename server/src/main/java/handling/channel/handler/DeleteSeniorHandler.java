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

public class DeleteSeniorHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		if (c.getPlayer().getFamilyId() <= 0 || c.getPlayer().getSeniorId() <= 0) {
			return;
		}
		// not required to be online
		final MapleFamily fam = World.Family.getFamily(c.getPlayer().getFamilyId()); // this
																						// is
																						// old
																						// family
		final MapleFamilyCharacter mgc = fam.getMFC(c.getPlayer().getSeniorId());
		final MapleFamilyCharacter mgc_ = c.getPlayer().getMFC();

		int diff = 0;
		if (c.getPlayer().getLevel() < mgc.getLevel()) { // char > junior level
															// = needd pay
			diff = mgc.getLevel() - c.getPlayer().getLevel();
		}
		int mesosReq = ((2501 * diff) + ((diff - 1) * diff));
		if (diff > 0) { // need mesos
			if (c.getPlayer().getMeso() < mesosReq || mesosReq <= 0) {
				c.getSession().write(FamilyPacket.FamilyMessage((byte) 0x50, mesosReq));
				return;
			}
			c.getPlayer().gainMeso(-mesosReq, true, false, true);
		}
		int repCostOnSenior = (GameConstants.getFamilyMultiplier(c.getPlayer().getLevel()) * c.getPlayer().getLevel());
		int repCostOnSS = (GameConstants.getFamilyMultiplier(c.getPlayer().getLevel()) * c.getPlayer().getLevel()) / 2;

		int sensen = World.Family.minusRep(c.getPlayer().getFamilyId(), c.getPlayer().getSeniorId(), repCostOnSenior);
		if (sensen > 0) {
			World.Family.minusRep(c.getPlayer().getFamilyId(), sensen, repCostOnSS);
		}

		int sid = mgc_.getSeniorId();

		mgc_.setSeniorId(0);
		boolean junior2 = mgc.getJunior2() == c.getPlayer().getId();
		if (junior2) {
			mgc.setJunior2(0);
		} else {
			mgc.setJunior1(0);
		}
		// if (!mgc.isOnline()) {
		MapleFamily.setOfflineFamilyStatus(mgc.getFamilyId(), mgc.getSeniorId(), mgc.getJunior1(), mgc.getJunior2(),
				mgc.getCurrentRep(), mgc.getTotalRep(), mgc.getId());
		// }
		c.getPlayer().saveFamilyStatus();
		MapleCharacterUtil.sendNote(mgc.getName(), c.getPlayer().getName(), c.getPlayer().getName()
				+ " has requested to sever ties with you, so the family relationship has ended.", 0);
		if (!fam.splitFamily(c.getPlayer().getId(), mgc_)) { // now, we're the
																// family leader
			if (!junior2) {
				fam.resetDescendants();
			}
			fam.resetPedigree();
		}
		MapleCharacter ochr = c.getChannelServer().getPlayerStorage().getCharacterById(sid);
		if (ochr != null) {
			ochr.getClient().getSession().write(FamilyPacket.getFamilyInfo(ochr));
			ochr.getClient().getSession().write(FamilyPacket.getFamilyPedigree(ochr));
		}
		c.getPlayer().dropMessage(1,
				"You have severed ties with (" + mgc.getName() + "). Your family relationship has ended.");
		c.getSession().write(FamilyPacket.getFamilyInfo(c.getPlayer()));
		c.getSession().write(FamilyPacket.getFamilyPedigree(c.getPlayer()));
		c.getSession().write(MaplePacketCreator.enableActions());

	}

}
