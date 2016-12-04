///*
// * This file is part of the OdinMS MapleStory Private Server
// * Copyright (C) 2011 Patrick Huy and Matthias Butz
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package server.quest.custom;
//
//import client.MapleCharacter;
//import client.MapleStat;
//import client.inventory.Item;
//import client.inventory.MapleInventoryType;
//import constants.GameConstants;
//import database.DatabaseConnection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import server.Randomizer;
//import tools.Pair;
//
//public class RandomQuestProvider {
//
//    private static HashMap<Integer, RandomQuest> activeQuests = new HashMap<>();
//    private static HashMap<Integer, LinkedList<Pair<Integer, Integer>>> helpEntries = new HashMap<>();
//    public static final long timeBetweenQuests = 1000 * 60 * 60 * 6; // 6 hours.
//    public final static int[] etcItems = {
//        4000000, 4000001, 4000003, 4000004, 4000005, 4000006,
//        4000007, 4000008, 4000009, 4000010,
//        4000011, 4000012, 4000013, 4000014, 4000015, 4000016,
//        4000018, 4000019, 4000020, 4000022,
//        4000023, 4000024, 4000025, 4000027, 4000028,
//        4000029, 4000030, 4000031, 4000032,
//        4000033, 4000034, 4000035, 4000036, 4000037, 4000039,
//        /*4000040,*/ 4000041, 4000042, 4000043,
//        4000044, 4000045, 4000046, 4000048, 4000049, 4000050,
//        4000051, 4000052, 4000053, 4000054,
//        4000055, 4000056, 4000057, 4000058, 4000059, 4000060,
//        4000061, 4000062, 4000063, /*4000064,
//        4000066, 4000067,*/ 4000068, 4000069, 4000070, 4000071,
//        4000072, 4000073, 4000074, 4000076,
//        4000078, 4000079, 4000080, 4000081, 4000083, 4000084,
//        4000085, 4000086, 4000087, 4000088,
//        /*4000092, 4000093,*/ 4000095, 4000096, 4000097, 4000098,
//        4000099, 4000100, 4000101, 4000102,
//        4000103, 4000104, 4000105, 4000106, 4000107, 4000108,
//        4000109, 4000110, 4000111, 4000112,
//        4000113, 4000114, 4000115, 4000116, 4000117, 4000118,
//        4000119, 4000120, 4000121, 4000122,
//        4000123, 4000125, /*4000126,*/ 4000127, 4000128, 4000129, 4000130, 4000131, 4000132, 4000133,
//        4000134, 4000135, /*4000137,*/ 4000143, 4000144, 4000145, 4000146, 4000147, 4000148, 4000149,
//        4000150, 4000151, 4000152, 4000153, 4000154, 4000155, 4000156, 4000157, 4000158, 4000159,
//        4000160, 4000161, 4000162, 4000163, 4000164, 4000165, 4000166, 4000167, 4000168, 4000169,
//        4000170, 4000171, 4000172, 4000173, /*4000176,*/ 4000177, 4000178, 4000179, 4000180, 4000181,
//        4000182, 4000183, 4000184, 4000185, 4000186, 4000195, 4000196, 4000197, 4000204, 4000205,
//        4000206, 4000207, 4000208, /*4000222, 4000223, 4000225, */ 4000226, 4000227, 4000228, 4000229,
//        4000230, 4000231, 4000232, 4000233, 4000234, /*4000235,*/ 4000236, 4000237, 4000238, 4000239,
//        4000240, 4000241, 4000242, /*4000243, 4000247,*/ 4000260, 4000261, 4000262, 4000263, 4000264,
//        4000265, 4000266, 4000267, 4000268, 4000269, 4000270, 4000271, 4000272, 4000273, 4000274,
//        4000276, 4000277, 4000278, 4000279, 4000280, 4000281, 4000282, 4000283, 4000284, 4000285,
//        4000286, 4000287, 4000288, 4000289, 4000291, 4000292, 4000293, 4000294, 4000295, 4000296,
//        4000297, 4000298, 4000299, 4000324, 4000325, 4000326, 4000327, 4000328, 4000329, 4000330,
//        4000331, 4000332, 4000333, 4000334, 4000335, 4000350, 4000351, 4000352, 4000353, 4000354,
//        4000355, 4000356, 4000357, 4000358, 4000359, 4000360, 4000361, /*4000362,*/ 4000363, 4000364,
//        /*4000365,*/ 4000366, 4000367, 4000368, 4000369, 4000370,
//        4000371, 4000372, 4000373, 4000374,
//        4000375, 4000376, 4000377, 4000378, 4000379,
//        4000380, 4000382, 4000383, 4000430, 4000431,
//        4000432, 4000433, 4000434, 4000465, 4000466,
//        4000467, 4000468, 4000469, 4000470, 4000471,
//        4000472, 4000473, 4000474, 4000475, 4000476, 4000477, 4000478,
//        // CWKPQ / Ninja Castle >.^ Might be hard for them.. if low lvls..
//        4032005, 4032007, 4032008, 4032010, 4032004, 4032003, 4000337,
//        4000338, 4000339, 4000340, 4000341};
//    public final static int[] etcOre = {4004000, 4004001, 4004002, 4004003, 4004004,
//        4010000,
//        4010001, 4010002, 4010003, 4010004, 4010005, 4010006, 4010007,
//        4011007, 4020000, 4020001, 4020002, 4020003, 4020004, 4020005,
//        4020006, 4020007, 4020008};
//    public final static int[] stimulators = {4130000, 4130001, 4130002,
//        4130003, 4130004, 4130005, 4130006, 4130007, 4130008, 4130009,
//        4130010, 4130011, 4130012, 4130013, 4130014, 4130015};
//
//    private RandomQuestProvider() {
//        // uninstantible
//    }
//
//    private static RandomQuest generateNewQuest(MapleCharacter chr) {
//        final int totalEtcItems = Randomizer.nextInt(4) + 2;
//        final int totalOres = Randomizer.nextInt(1) + 1;
//        final int totalStimulators = Randomizer.nextInt(2);
//
//        ArrayList<Item> reqItem = new ArrayList<>();
//        for (int i = 0; i < totalEtcItems; i++) {
//            reqItem.add(new Item(etcItems[Randomizer.nextInt(etcItems.length)], Byte.MIN_VALUE, (short) (((Randomizer.nextInt(4) + 2) * 10) + (Randomizer.nextBoolean() ? 5 : 0))));
//        }
//        for (int i = 0; i < totalOres; i++) {
//            reqItem.add(new Item(etcOre[Randomizer.nextInt(etcOre.length)], Byte.MIN_VALUE, (short) (Randomizer.nextInt(6) + 3)));
//        }
//        for (int i = 0; i < totalStimulators; i++) {
//            reqItem.add(new Item(stimulators[Randomizer.nextInt(stimulators.length)], Byte.MIN_VALUE, (short) (Randomizer.nextInt(1) + 1)));
//        }
//
//        chr.setLastQuest(System.currentTimeMillis());
//        PreparedStatement ps = null;
//        try {
//            ps = DatabaseConnection.getConnection().prepareStatement("UPDATE accounts SET iQuest = ? WHERE id = ?");
//            ps.setLong(1, chr.getLastQuest());
//            ps.setInt(2, chr.getAccountID());
//            ps.executeUpdate();
//        } catch (SQLException sqe) {
//        } finally {
//            try {
//                ps.close();
//            } catch (Exception e) {
//            }
//        }
//        final RandomQuest quest = new RandomQuest(chr, 0, 0, reqItem);
//        quest.saveToDB();
//        activeQuests.put(chr.getAccountID(), quest);
//        return quest;
//    }
//
//    public static void removeQuest(int accid) {
//        activeQuests.remove(accid);
//    }
//
//    public static boolean canGetNewQuest(MapleCharacter chr) {
//        return (chr.getLastQuest() + timeBetweenQuests) <= System.currentTimeMillis();
//    }
//
//    public static boolean hasActiveQuest(MapleCharacter chr) {
//        return activeQuests.get(chr.getAccountID()) != null;
//    }
//
//    public static RandomQuest tryGetQuest(MapleCharacter chr) {
//        if (chr.getLastQuest() + timeBetweenQuests > System.currentTimeMillis()) {
//            if (activeQuests.containsKey(chr.getAccountID())) {
//                return activeQuests.get(chr.getAccountID());
//            } else {
//                final RandomQuest quest = RandomQuest.loadFromDB(chr);
//                activeQuests.put(chr.getAccountID(), quest);
//                return quest;
//            }
//        }
//        return null;
//    }
//
//    public static RandomQuest getQuest(MapleCharacter chr) {
//        if (chr.getLastQuest() + timeBetweenQuests > System.currentTimeMillis()) {
//            if (activeQuests.containsKey(chr.getAccountID())) {
//                return activeQuests.get(chr.getAccountID());
//            } else {
//                final RandomQuest quest = RandomQuest.loadFromDB(chr);
//                activeQuests.put(chr.getAccountID(), quest);
//                return quest;
//            }
//        }
//        return generateNewQuest(chr);
//    }
//
//    public static boolean giveReward(MapleCharacter chr) {
//        for (int i = 1; i <= 5; i++) {
//            if (chr.getInventory(MapleInventoryType.getByType((byte) i)).getNumFreeSlot() < 3) {
//                return false;
//            }
//        }
//        // add gold key in cs?
//        final int fame = Randomizer.nextInt(2) + 1; // 1-2
//        chr.addFame(fame);
//        chr.updateSingleStat(MapleStat.FAME, chr.getFame());
//        chr.dropMessage(5, "You have gained " + fame + " fame.");
//        if (Randomizer.nextInt(2) == 0) { // 1/2 chance
//            chr.modifyCSPoints(0, Randomizer.nextInt(500) + 500, true); // 500~1000
//        }
//        final int experi = (GameConstants.getExpNeededForLevel(chr.getLevel()) / 100) * 10; // 10%
//        chr.gainExp(experi, true, true, true);
//        return true;
//    }
//
//    public static LinkedList<Pair<Integer, Integer>> getQuestHelpEntry(int itemid) {
//        if (helpEntries.containsKey(itemid) && helpEntries.get(itemid) != null) {
//            return helpEntries.get(itemid);
//        }
//        LinkedList<Pair<Integer, Integer>> ret = getQuestEntryFromDB(itemid);
//        if (ret != null) {
//            helpEntries.put(itemid, ret);
//        }
//        return ret;
//    }
//
//    private static LinkedList<Pair<Integer, Integer>> getQuestEntryFromDB(int itemid) {
//        LinkedList<Pair<Integer, Integer>> ret = new LinkedList<>();
//        PreparedStatement ps = null;
//        try {
//            ps = DatabaseConnection.getConnection().prepareStatement("SELECT dropperid, chance FROM drop_data WHERE itemid = ? ORDER BY chance");
//            ps.setInt(1, itemid);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                ret.add(new Pair<>(rs.getInt("dropperid"), rs.getInt("chance")));
//            }
//        } catch (SQLException ex) {
//            System.err.println("Error loading help entry..." + ex);
//            return null;
//        } finally {
//            try {
//                ps.close();
//            } catch (Exception e) {
//            }
//        }
//        return ret;
//    }
//}
