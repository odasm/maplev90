package handling.login.handler;

import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import handling.channel.ChannelServer;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class CharSelectedHandler extends AbstractMaplePacketHandler{

	private static final boolean loginFailCount(final MapleClient c) {
		c.loginAttempt++;
		if (c.loginAttempt > 5) {
			return true;
		}
		return false;
	}

	
	
	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		slea.readMapleAsciiString();
		final int charId = slea.readInt();

		if (loginFailCount(c) || !c.login_Auth(charId)) { // This should not happen unlessplayer is hacking
			c.getSession().close();
			return;
		}

		if (c.getIdleTask() != null) {
			c.getIdleTask().cancel(true);
		}
		c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, c.getSessionIPAddress());
		c.getSession().write(MaplePacketCreator.getServerIP(
				Integer.parseInt(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[1]), charId));
		
	}

}
