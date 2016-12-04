package handling.login.handler;

import java.util.List;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.LoginPacket;

public class CharlistRequestHandler extends AbstractMaplePacketHandler{

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		slea.readByte();
		final int server = slea.readByte();
		final int channel = slea.readByte() + 1;

		c.setWorld(server);
		c.setChannel(channel);

		final List<MapleCharacter> chars = c.loadCharacters(server);
		if (chars != null) {
			c.getSession().write(LoginPacket.getCharList(c.getSecondPassword() != null, chars, c.getCharacterSlots()));
		} else {
			c.getSession().close();
		}
		
	}
	
}
