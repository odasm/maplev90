package server.maps.event;

import java.awt.Point;

import client.MapleCharacter;
import server.maps.MapleMap;
import tools.MaplePacketCreator;

public class PuppeteerMapEvent extends AbstractMapEvent {

	
	public PuppeteerMapEvent(MapleMap map) {
		super(map);
		
	}

	
	
	@Override
	public void onUserEnter(MapleCharacter c) {
		map.broadcastMessage(MaplePacketCreator.getClock((int) (10 * 60 )));
		map.spawnNpc(1104000, new Point(615, 249));
		
	}

	@Override
	public void onUserExit(MapleCharacter c) {
		map.killAllMonsters(true);
		
	}

}
