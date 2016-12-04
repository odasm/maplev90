package handling.channel.handler;

import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import scripting.EventManager;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class RequestBoatStatusHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient client) {
		int mapid = slea.readInt();

		if (client.getPlayer().getMap().getId() == mapid) {

			EventManager manager;
			if (mapid == 260000100 || mapid == 200000151) {// ariant
				manager = client.getChannelServer().getEventSM().getEventManager("Geenie");
			} else {
				manager = client.getChannelServer().getEventSM().getEventManager("Boats");
			}

			if (Boolean.valueOf(manager.getProperty("docked")) == true) {
				client.getSession().write(MaplePacketCreator.boatPacket(0));
			} else {
				client.getSession().write(MaplePacketCreator.boatPacket(2));
			}

		}
		;

	}

}
