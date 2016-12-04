package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleCustomQuestStatus;
import handling.AbstractMaplePacketHandler;
import server.quest.custom.CustomQuestProvider;
import tools.data.input.SeekableLittleEndianAccessor;

public class QuickSlotHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter chr = c.getPlayer();
		if (slea.available() == 32 && chr != null) {

			final StringBuilder ret = new StringBuilder();
			for (int i = 0; i < 8; i++) {
				ret.append(slea.readInt()).append(",");
			}
			ret.deleteCharAt(ret.length() - 1);

			final MapleCustomQuestStatus stat = new MapleCustomQuestStatus(CustomQuestProvider.getInstance(170000),
					(byte) 1);
			stat.setCustomData(ret.toString());
			chr.updateCustomQuest(stat);
		}

	}

}
