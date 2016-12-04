package handling.channel.handler;

import client.ISkill;
import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import handling.AbstractMaplePacketHandler;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class SkillEffectHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter chr = c.getPlayer();
		final int skillId = slea.readInt();
		final byte level = slea.readByte();
		final byte flags = slea.readByte();
		final byte speed = slea.readByte();
		final byte unk = slea.readByte(); // Added on v.82

		final ISkill skill = SkillFactory.getSkill(skillId);
		if (chr == null) {
			return;
		}
		if (!chr.isSkillBelongToJob(skillId)) {
			chr.dropMessage(5, "This skill cannot be used with the current job.");
			chr.getClient().getSession().write(MaplePacketCreator.enableActions());
			return;
		}
		final int skilllevel_serv = chr.getSkillLevel(skill);

		if (skilllevel_serv > 0 && skilllevel_serv == level && skill.isChargeSkill()) {
			chr.setKeyDownSkill_Time(System.currentTimeMillis());
			chr.getMap().broadcastMessage(chr, MaplePacketCreator.skillEffect(chr, skillId, level, flags, speed, unk),
					false);

		}
		if(skillId == 4341002){
			c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.skillCancel(c.getPlayer(), skillId));
		}

	}

}
