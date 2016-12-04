package handling.channel.handler;

import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import server.shops.HiredMerchant;
import server.shops.IMaplePlayerShop;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.PlayerShopPacket;

public class RemoteStoreHandler extends AbstractMaplePacketHandler{

	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		final IMaplePlayerShop ips = c.getPlayer().getPlayerShop();
        if (ips instanceof HiredMerchant) {
            final HiredMerchant merchant = (HiredMerchant) ips;
            if (merchant.isOwner(c.getPlayer())) {
                merchant.setOpen(false);
                merchant.removeAllVisitors((byte) 16, (byte) 0);
                c.getPlayer().setPlayerShop(ips);
                c.getSession().write(PlayerShopPacket.getHiredMerch(c.getPlayer(), merchant, false));
            }
        }
        c.enableActions();
		
	}

	
}
