package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.PetPacket;

public class UpdateCharacterHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter chr = c.getPlayer();
		chr.updatePetAuto();
		if (!chr.getCustomQuestData(170001).equals("") && chr.getPet(0) != null) {
			chr.getClient().getSession().write(PetPacket.loadExceptionList(chr.getId(), chr.getPet(0).getUniqueId(),
					chr.getCustomQuestData(170001)));
		}

	}

}
