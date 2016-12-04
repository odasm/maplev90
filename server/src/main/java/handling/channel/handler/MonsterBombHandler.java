package handling.channel.handler;

import java.util.Random;

import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import handling.AbstractMaplePacketHandler;
import server.life.MapleMonster;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class MonsterBombHandler extends AbstractMaplePacketHandler{

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter chr = c.getPlayer();
		final MapleMonster monster = chr.getMap().getMonsterByOid(slea.readInt());
        final int xpos = slea.readInt();
        final int ypos = slea.readInt();
        if (monster != null) {
			c.getSession().write(MaplePacketCreator.damageMonster(4341003, xpos, ypos));
		}
        if (monster == null || chr.getJob() != 434 || chr.getMap() == null || !chr.isAlive() || chr.isHidden()) {
            return;
        }
        final ISkill skill = SkillFactory.getSkill(4341003);
        if (skill != null) {
        	MapleMapObject mob = c.getPlayer().getMap().getMapObject(monster.getObjectId(), MapleMapObjectType.MONSTER);
        	if(mob != null){
        		MapleMonster mob2 = ((MapleMonster)mob);
        		int damage = c.getPlayer().getLevel() * 100 + new Random().nextInt(100 * c.getPlayer().getLevel());
        		c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.damageMonster(mob.getObjectId(), damage), true);
        		mob2.damage(c.getPlayer(), damage, true);
        		c.getSession().write(MaplePacketCreator.skillCooldown(skill.getId(), 30000));
        		chr.addCooldown(4341003, System.currentTimeMillis(), 30 * 1000);
        		
        	}
        	c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.showMonsterBombEffect(xpos, ypos, chr.getSkillLevel(skill)), true);
        }
		
        c.getSession().write(MaplePacketCreator.enableActions());
	}

}
