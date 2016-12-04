package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyCharacter;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.FamilyPacket;

public class AcceptFamilyHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter inviter = c.getPlayer().getMap().getCharacterById(slea.readInt());
		if (inviter != null && c.getPlayer().getSeniorId() == 0 && (c.getPlayer().isGM() || !inviter.isHidden())
				&& inviter.getLevel() - 20 < c.getPlayer().getLevel() && inviter.getLevel() >= 10
				&& inviter.getName().equals(slea.readMapleAsciiString()) && inviter.getNoJuniors() < 2
				/* && inviter.getFamily().getMemberSize() < 1000 */ && c.getPlayer().getLevel() >= 10) {
			boolean accepted = slea.readByte() > 0;
			inviter.getClient().getSession()
					.write(FamilyPacket.sendFamilyJoinResponse(accepted, c.getPlayer().getName()));
			if (accepted) {
				c.getSession().write(FamilyPacket.getSeniorMessage(inviter.getName()));

				int old = c.getPlayer().getMFC() == null ? 0 : c.getPlayer().getMFC().getFamilyId();
				int oldj1 = c.getPlayer().getMFC() == null ? 0 : c.getPlayer().getMFC().getJunior1();
				int oldj2 = c.getPlayer().getMFC() == null ? 0 : c.getPlayer().getMFC().getJunior2();
				if (inviter.getFamilyId() > 0 && World.Family.getFamily(inviter.getFamilyId()) != null) {
					MapleFamily fam = World.Family.getFamily(inviter.getFamilyId());

					c.getPlayer().setFamily(old <= 0 ? inviter.getFamilyId() : old, inviter.getId(),
							oldj1 <= 0 ? 0 : oldj1, oldj2 <= 0 ? 0 : oldj2, c.getPlayer().getLevel(),
							c.getPlayer().getJob());
					MapleFamilyCharacter mf = inviter.getMFC();
					if (mf.getJunior1() > 0) {
						mf.setJunior2(c.getPlayer().getId());
					} else {
						mf.setJunior1(c.getPlayer().getId());
					}
					inviter.saveFamilyStatus();

					final MapleFamily OldFam = World.Family.getFamily(old);
					if (old > 0 && OldFam != null) {
						final MapleFamilyCharacter mgc = OldFam.getMFC(c.getPlayer().getId()); // change
																								// acceptor's
																								// senior
						mgc.setSeniorId(inviter.getId());
						MapleFamily.mergeFamily(fam, OldFam);
					} else {
						c.getPlayer().setFamily(inviter.getFamilyId(), inviter.getId(), oldj1 <= 0 ? 0 : oldj1,
								oldj2 <= 0 ? 0 : oldj2, c.getPlayer().getLevel(), c.getPlayer().getJob());
						fam.setOnline(c.getPlayer().getId(), true, c.getChannel(), c.getPlayer().getLoginTime());
						c.getPlayer().saveFamilyStatus();
					}
					if (fam != null) {
						if (inviter.getNoJuniors() == 1 || old > 0) {
							fam.resetDescendants();
						}
						fam.resetPedigree();
					}
				} else {
					int id = MapleFamily.createFamily(inviter.getId());
					if (id > 0) {
						MapleFamily.setOfflineFamilyStatus(id, 0, c.getPlayer().getId(), 0, inviter.getCurrentRep(),
								inviter.getTotalRep(), inviter.getId());
						MapleFamily.setOfflineFamilyStatus(id, inviter.getId(), oldj1 <= 0 ? 0 : oldj1,
								oldj2 <= 0 ? 0 : oldj2, c.getPlayer().getCurrentRep(), c.getPlayer().getTotalRep(),
								c.getPlayer().getId());

						inviter.setFamily(id, 0, c.getPlayer().getId(), 0, inviter.getLevel(), inviter.getJob()); // new
																													// family,
																													// sure
																													// junior
																													// 1
						c.getPlayer().setFamily(id, inviter.getId(), oldj1 <= 0 ? 0 : oldj1, oldj2 <= 0 ? 0 : oldj2,
								c.getPlayer().getLevel(), c.getPlayer().getJob());

						final MapleFamily newFam = World.Family.getFamily(id);
						newFam.setOnline(inviter.getId(), true, inviter.getClient().getChannel(),
								c.getPlayer().getLoginTime());

						final MapleFamily OldFam = World.Family.getFamily(old);
						if (old > 0 && OldFam != null) {
							final MapleFamilyCharacter mgc = OldFam.getMFC(c.getPlayer().getId()); // change
																									// acceptor's
																									// senior
							mgc.setSeniorId(inviter.getId());
							MapleFamily.mergeFamily(newFam, OldFam);
						} else {
							newFam.setOnline(c.getPlayer().getId(), true, c.getChannel(), c.getPlayer().getLoginTime());
						}
						newFam.resetDescendants();
						newFam.resetPedigree();
					}
				}
				c.getSession().write(FamilyPacket.getFamilyInfo(c.getPlayer()));
			}
		}

	}

}
