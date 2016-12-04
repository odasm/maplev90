package handling.channel.handler;

import java.awt.Point;
import java.util.List;

import client.MapleCharacter;
import client.MapleClient;
import handling.AbstractMaplePacketHandler;
import server.Randomizer;
import server.life.MapleMonster;
import server.life.MobSkill;
import server.life.MobSkillFactory;
import server.maps.MapleMap;
import server.movement.LifeMovementFragment;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MobPacket;

public class MoveLifeHandler extends AbstractMaplePacketHandler {

	@Override
	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		MapleCharacter chr = c.getPlayer();
		if (chr == null || chr.getMap() == null) {
			return; // ?
		}
		final int oid = slea.readInt();
		final MapleMonster monster = chr.getMap().getMonsterByOid(oid);

		if (monster == null) { // movin something which is not a monster
			return;
		}
		final short moveid = slea.readShort();
		final boolean useSkill = slea.readByte() > 0;
		final byte skill = slea.readByte();
		final int skill1 = slea.readByte() & 0xFF; // unsigned?
		final int skill2 = slea.readByte();
		final int skill3 = slea.readByte();
		final int skill4 = slea.readByte();
		int realskill = 0;
		int level = 0;

		if (useSkill) {// && (skill == -1 || skill == 0)) {
			final byte size = monster.getNoSkills();
			boolean used = false;

			if (size > 0) {
				final Pair<Integer, Integer> skillToUse = monster.getSkills().get((byte) Randomizer.nextInt(size));
				realskill = skillToUse.getLeft();
				level = skillToUse.getRight();
				// Skill ID and Level
				final MobSkill mobSkill = MobSkillFactory.getMobSkill(realskill, level);

				if (mobSkill != null && !mobSkill.checkCurrentBuff(chr, monster)) {
					final long now = System.currentTimeMillis();
					final long ls = monster.getLastSkillUsed(realskill);

					if (ls == 0 || ((now - ls) > mobSkill.getCoolTime())) {
						monster.setLastSkillUsed(realskill, now, mobSkill.getCoolTime());

						final int reqHp = (int) (((float) monster.getHp() / monster.getMobMaxHp()) * 100); // In
																											// case
																											// this
																											// monster
																											// have
																											// 2.1b
																											// and
																											// above
																											// HP
						if (reqHp <= mobSkill.getHP()) {
							used = true;
							mobSkill.applyEffect(chr, monster, true);
						}
					}
				}
			}
			if (!used) {
				realskill = 0;
				level = 0;
			}
		}
		slea.skip(33);
		final Point startPos = monster.getPosition();
		List<LifeMovementFragment> res = null;
		try {
			res = MovementParse.parseMovement(slea, 2);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("AIOBE Type2:\n" + slea.toString(true));
			return;
		}
		if (res != null && chr != null && res.size() > 0) {
			final MapleMap map = chr.getMap();
			c.getSession().write(MobPacket.moveMonsterResponse(monster.getObjectId(), moveid, monster.getMp(),
					monster.isControllerHasAggro(), realskill, level));
			if (slea.available() < 9 || slea.available() > 33) { // 9.. 0 ->
																	// endPos?
																	// -> endPos
																	// again? ->
																	// 0 -> 0
				System.out.println("slea.available != 17 (movement parsing error)");
				System.out.println(slea.toString(true));
				c.getSession().close();
				return;
			}

			MovementParse.updatePosition(res, monster, -1);
			final Point endPos = monster.getPosition();
			map.moveMonster(monster, endPos);
			map.broadcastMessage(chr, MobPacket.moveMonster(useSkill, skill, skill1, skill2, skill3, skill4,
					monster.getObjectId(), startPos, res), endPos);
			chr.getCheatTracker().checkMoveMonster(endPos);
		}

	}

}
