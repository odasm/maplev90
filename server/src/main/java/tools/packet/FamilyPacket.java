/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tools.packet;

import java.util.List;

import client.MapleCharacter;
import handling.SendPacketOpcode;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyBuff;
import handling.world.family.MapleFamilyBuff.MapleFamilyBuffEntry;
import handling.world.family.MapleFamilyCharacter;
import tools.Pair;
import tools.data.output.MaplePacketLittleEndianWriter;

public class FamilyPacket {

    public static byte[] getFamilyData() {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.FAMILY_PRIVILEDGE_LIST.getValue());
        List<MapleFamilyBuffEntry> entries = MapleFamilyBuff.getBuffEntry();
        mplew.writeInt(entries.size()); // Number of events

        for (MapleFamilyBuffEntry entry : entries) {
            mplew.write(entry.type);
            mplew.writeInt(entry.rep);
            mplew.writeInt(entry.count);
            mplew.writeMapleAsciiString(entry.name);
            mplew.writeMapleAsciiString(entry.desc);
        }
        return mplew.getPacket();
    }

    public static byte[] getFamilyInfo(MapleCharacter chr) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_INFO.getValue());
        mplew.writeInt(chr.getCurrentRep()); //rep
        mplew.writeInt(chr.getTotalRep()); // total rep
        boolean nullFamily = false;

        MapleFamily family = World.Family.getFamily(chr.getFamilyId());
        if (family == null || (chr.getSeniorId() <= 0 && chr.getJunior1() <= 0 && chr.getJunior2() <= 0)) {
            nullFamily = true;
        }
        int todaysrep = 0;
        if (!nullFamily) {
            if (chr.getJunior1() > 0) { // id
                todaysrep += World.Family.getSenRep(chr.getJunior1(), chr.getId()); // I am his senior
                // 2 more juniors
                MapleFamilyCharacter alljj = family.getMFC(chr.getJunior1());
                if (alljj.getJunior1() > 0) { // id
                    todaysrep += World.Family.getSenSenRep(alljj.getJunior1(), chr.getId()); // I am his senior's senior
                }
                if (alljj.getJunior2() > 0) { // id
                    todaysrep += World.Family.getSenSenRep(alljj.getJunior2(), chr.getId()); // I am his senior's senior
                }
            }
            if (chr.getJunior2() > 0) { // id
                todaysrep += World.Family.getSenRep(chr.getJunior2(), chr.getId()); // I am his senior
                // 2 more juniors
                MapleFamilyCharacter alljj = family.getMFC(chr.getJunior2());
                if (alljj.getJunior1() > 0) { // id
                    todaysrep += World.Family.getSenSenRep(alljj.getJunior1(), chr.getId()); // I am his senior's senior
                }
                if (alljj.getJunior2() > 0) { // id
                    todaysrep += World.Family.getSenSenRep(alljj.getJunior2(), chr.getId()); // I am his senior's senior
                }
            }
        }
        todaysrep -= World.Family.getMinusReps(chr.getId()); // no family also -

        mplew.writeInt(todaysrep); //rep recorded today
        mplew.writeShort(chr.getNoJuniors());
        mplew.writeShort(2);
        mplew.writeShort(chr.getNoJuniors());
        if (!nullFamily) {
            mplew.writeInt(family.getLeaderId()); //??? 9D 60 03 00
            mplew.writeMapleAsciiString(family.getLeaderName());
            mplew.writeMapleAsciiString(family.getNotice()); //message?
        } else {
            mplew.writeLong(0);
        }
        List<Pair<Integer, Integer>> b = chr.usedBuffs();
        mplew.writeInt(b.size());
        for (Pair<Integer, Integer> ii : b) {
            mplew.writeInt(ii.getLeft()); //buffid
            mplew.writeInt(ii.getRight()); //times used
        }
        return mplew.getPacket();
    }

    public static void addFamilyCharInfo(MapleFamilyCharacter ldr, MaplePacketLittleEndianWriter mplew, MapleFamily family) {
        mplew.writeInt(ldr.getId());
        mplew.writeInt(ldr.getSeniorId());
        mplew.writeShort(ldr.getJobId());
        mplew.write(ldr.getLevel());
        mplew.write(ldr.isOnline() ? 1 : 0);
        mplew.writeInt(ldr.getCurrentRep());
        mplew.writeInt(ldr.getTotalRep());
        int senreps = 0, sensenreps = 0;
        if (ldr.getSeniorId() > 0) {
            MapleFamilyCharacter senior = family.getMFC(ldr.getSeniorId());
            if (senior != null) {
                senreps = World.Family.getSenRep(ldr.getId(), ldr.getSeniorId());
                if (senior.getSeniorId() > 0) {
                    sensenreps = World.Family.getSenSenRep(ldr.getId(), senior.getSeniorId());
                }
            }
        }
        mplew.writeInt(senreps); //Reps recorded to senior
        mplew.writeInt(sensenreps); //Reps recorded to senior's senior
        mplew.writeInt(Math.max(ldr.getChannel(), 0)); // channel
        mplew.writeInt((int) (ldr.getLoginTime() > 0 ? ((System.currentTimeMillis() - ldr.getLoginTime()) / (60 * 1000)) : 0)); // Online time in minutes
        mplew.writeMapleAsciiString(ldr.getName());
    }

    public static byte[] getFamilyPedigree(MapleCharacter chr) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_CHART.getValue());
        mplew.writeInt(chr.getId());
        MapleFamily family = World.Family.getFamily(chr.getFamilyId());
        int gens = 0, generations = 0;
        boolean nullFamily = false;
        if (family == null || (chr.getSeniorId() <= 0 && chr.getJunior1() <= 0 && chr.getJunior2() <= 0)) {
            nullFamily = true;
            mplew.writeInt(2);
            addFamilyCharInfo(new MapleFamilyCharacter(chr, 0, 0, 0, 0), mplew, family); //leader
        } else {
            mplew.writeInt(family.getMFC(chr.getId()).getPedigree().size() + 1); //+ 1 for leader, but we don't want leader seeing all msgs
            addFamilyCharInfo(family.getMFC(family.getLeaderId()), mplew, family);

            if (chr.getSeniorId() > 0) {
                MapleFamilyCharacter senior = family.getMFC(chr.getSeniorId());
                if (senior != null) {
                    if (senior.getSeniorId() > 0) {
                        addFamilyCharInfo(family.getMFC(senior.getSeniorId()), mplew, family);
                    }
                    addFamilyCharInfo(senior, mplew, family);
                }
            }
        }
        addFamilyCharInfo(chr.getMFC() == null ? new MapleFamilyCharacter(chr, 0, 0, 0, 0) : chr.getMFC(), mplew, family);
        if (family != null && !nullFamily) {
            if (chr.getSeniorId() > 0) {
                MapleFamilyCharacter senior = family.getMFC(chr.getSeniorId());
                if (senior != null) {
                    if (senior.getJunior1() > 0 && senior.getJunior1() != chr.getId()) {
                        addFamilyCharInfo(family.getMFC(senior.getJunior1()), mplew, family);
                    } else if (senior.getJunior2() > 0 && senior.getJunior2() != chr.getId()) {
                        addFamilyCharInfo(family.getMFC(senior.getJunior2()), mplew, family);
                    }
                }
            }
            if (chr.getJunior1() > 0) {
                MapleFamilyCharacter junior = family.getMFC(chr.getJunior1());
                if (junior != null) {
                    addFamilyCharInfo(junior, mplew, family);
                }
            }
            if (chr.getJunior2() > 0) {
                MapleFamilyCharacter junior = family.getMFC(chr.getJunior2());
                if (junior != null) {
                    addFamilyCharInfo(junior, mplew, family);
                }
            }
            if (chr.getJunior1() > 0) {
                MapleFamilyCharacter junior = family.getMFC(chr.getJunior1());
                if (junior != null) {
                    if (junior.getJunior1() > 0 && family.getMFC(junior.getJunior1()) != null) {
                        gens++;
                        addFamilyCharInfo(family.getMFC(junior.getJunior1()), mplew, family);
                    }
                    if (junior.getJunior2() > 0 && family.getMFC(junior.getJunior2()) != null) {
                        gens++;
                        addFamilyCharInfo(family.getMFC(junior.getJunior2()), mplew, family);
                    }
                }
            }
            if (chr.getJunior2() > 0) {
                MapleFamilyCharacter junior = family.getMFC(chr.getJunior2());
                if (junior != null) {
                    if (junior.getJunior1() > 0 && family.getMFC(junior.getJunior1()) != null) {
                        gens++;
                        addFamilyCharInfo(family.getMFC(junior.getJunior1()), mplew, family);
                    }
                    if (junior.getJunior2() > 0 && family.getMFC(junior.getJunior2()) != null) {
                        gens++;
                        addFamilyCharInfo(family.getMFC(junior.getJunior2()), mplew, family);
                    }
                }
            }
            generations = family.getMemberSize();
        }
        mplew.writeLong(2 + gens);
        mplew.writeInt(gens);
        mplew.writeInt(-1);
        mplew.writeInt(generations); // total family members
        if (family != null && !nullFamily) {
            if (chr.getJunior1() > 0) {
                MapleFamilyCharacter junior = family.getMFC(chr.getJunior1());
                if (junior != null) {
                    if (junior.getJunior1() > 0 && family.getMFC(junior.getJunior1()) != null) {
                        mplew.writeInt(junior.getJunior1());
                        mplew.writeInt(family.getMFC(junior.getJunior1()).getDescendants());
                    }
                    if (junior.getJunior2() > 0 && family.getMFC(junior.getJunior2()) != null) {
                        mplew.writeInt(junior.getJunior2());
                        mplew.writeInt(family.getMFC(junior.getJunior2()).getDescendants());
                    }
                }
            }
            if (chr.getJunior2() > 0) {
                MapleFamilyCharacter junior = family.getMFC(chr.getJunior2());
                if (junior != null) {
                    if (junior.getJunior1() > 0 && family.getMFC(junior.getJunior1()) != null) {
                        mplew.writeInt(junior.getJunior1());
                        mplew.writeInt(family.getMFC(junior.getJunior1()).getDescendants());
                    }
                    if (junior.getJunior2() > 0 && family.getMFC(junior.getJunior2()) != null) {
                        mplew.writeInt(junior.getJunior2());
                        mplew.writeInt(family.getMFC(junior.getJunior2()).getDescendants());
                    }
                }
            }
        }

        List<Pair<Integer, Integer>> b = chr.usedBuffs();
        mplew.writeInt(b.size());
        for (Pair<Integer, Integer> ii : b) {
            mplew.writeInt(ii.getLeft()); //buffid
            mplew.writeInt(ii.getRight()); //times used
        }
        mplew.writeShort(2);
        return mplew.getPacket();
    }

    public static byte[] sendFamilyInvite(int cid, int otherLevel, int otherJob, String inviter) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.FAMILY_INVITE.getValue());
        mplew.writeInt(cid); //the inviter
        mplew.writeInt(otherLevel);
        mplew.writeInt(otherJob);
        mplew.writeMapleAsciiString(inviter);

        return mplew.getPacket();
    }

    public static byte[] sendFamilyJoinResponse(boolean accepted, String added) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_INVITE_RESULT.getValue());
        mplew.write(accepted ? 1 : 0);
        mplew.writeMapleAsciiString(added);
        return mplew.getPacket();
    }

    public static byte[] getSeniorMessage(String name) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_JOIN_ACCEPTED.getValue());
        mplew.writeMapleAsciiString(name);
        return mplew.getPacket();
    }

    public static byte[] changeRep(int r, String name) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_REP_INC.getValue());
        mplew.writeInt(r);
        mplew.writeMapleAsciiString(name);
        return mplew.getPacket();
    }

    public static byte[] familyLoggedIn(boolean online, String name) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_LOGGEDIN.getValue());
        mplew.write(online ? 1 : 0);
        mplew.writeMapleAsciiString(name);
        return mplew.getPacket();
    }

    public static byte[] cancelFamilyBuff() {
        return familyBuff(0, 0, 0, 0);
    }

    public static byte[] familyBuff(int type, int buffnr, int amount, int time) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_BUFF.getValue());
        mplew.write(type);
        if (type >= 2 && type <= 4) {
            mplew.writeInt(buffnr);
            //first int = exp, second int = drop
            mplew.writeInt(type == 3 ? 0 : amount);
            mplew.writeInt(type == 2 ? 0 : amount);
            mplew.write(0);
            mplew.writeInt(time);
        }
        return mplew.getPacket();
    }

    public static byte[] familySummonRequest(String name, String mapname) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.FAMILY_SUMMON_REQUEST.getValue());
        mplew.writeMapleAsciiString(name);
        mplew.writeMapleAsciiString(mapname);
        return mplew.getPacket();
    }

    public static byte[] FamilyMessage(byte type, int meso) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        // 01 = You have severed ties with (null). Your family relationship has ended.
        // 40 = You cannot add this character as a Junior.
        // 41 = The name you entered is incorrect or he/she is currently not logged in.
        // 42 = You belong to the same family.
        // 43 = You do not belong to the same family.
        // 45 = The character you wish to add as a Junior must be in the same map.
        // 46 = The character is already a Junior of another character.
        // 47 = The Junior you wish to add must be at a lower rank.
        // 48 = The gap between you and your junior must be within 20 levels.
        // 49 = Another character has requested to add this character. Please try again later.
        // 4A = Another character has requested a summon. Please try again later.
        // 4B = The summons has failed. Your current location or state does not allow a summons.
        // 4C = The family cannot extend more than 1000 generations from above and below.
        // 4D = The Junior you wish to add must be over Level 10.
        // 4E = You cannot add a Junior that has requested to change worlds.
        // 4F = You cannot add a Junior since you've requested to change worlds.
        // 50 = Separation is not possible due to insufficient Mesos. You will need X Mesos to separate with a Senior.
        // 51 = Separation is not possible due to insufficient Mesos. You will need X Mesos to separate with a Junior.
        // 52 = The Entitlement does not apply because your level does not match the corresponding area. /  < Nexon forget to add a break...so go -> 4E > LOL ! > You cannot add a Junior that has requested to change worlds
        mplew.writeShort(SendPacketOpcode.FAMILY_MESSAGE.getValue());
        mplew.writeInt(type);
        mplew.writeInt(meso); // used on type 50/51

        return mplew.getPacket();
    }
}
