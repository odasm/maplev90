package handling.channel.handler;

import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import scripting.NPCScriptManager;
import tools.data.input.SeekableLittleEndianAccessor;

public class RemoteGachaponHandler extends AbstractMaplePacketHandler{

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c){
		slea.readInt();
		byte city = slea.readByte();
		if(city < 0 && city > 8){
			return;
		}
		int baseCity = 9100100 + city;
		NPCScriptManager.getInstance().start(c, baseCity);
		c.enableActions();
	}


}
