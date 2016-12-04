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
//import client.inventory.Item;
//import constants.GameConstants;
//import database.DatabaseConnection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import server.MapleInventoryManipulator;
//import tools.MaplePacketCreator;
//
///**
// *
// * @author AuroX
// * Account Type. This is a class itself, everything will be stored/checked here (World based)
// */
//public final class RandomQuest {
//
//    private int accID;
//    private ArrayList<Item> reqItems; // Its better to store in a map i guess..since i don't need the stats of the item
//
//    private RandomQuest() {
//        // uninstantible
//    }
//
//    public int getAccID() {
//        return accID;
//    }
//
//    public ArrayList<Item> getReqItems() {
//        return reqItems;
//    }
//
//    public void setReqItems(ArrayList<Item> reqItems) {
//        this.reqItems = reqItems;
//    }
//
//    public RandomQuest(MapleCharacter mc, int mesos, int points, ArrayList<Item> items) {
//        this.accID = mc.getClient().getAccID();
//        this.setReqItems(items);
//        this.saveToDB();
//    }
//
//    public boolean canComplete(MapleCharacter chr) {
//        if (chr.getClient().getAccID() != this.getAccID()) {
//            return false;
//        }
//        if (this.getReqItems() != null) {
//            for (Item i : this.getReqItems()) {
//                if (chr.getItemQuantity(i.getItemId(), true) < i.getQuantity()) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    public void completeQuest(MapleCharacter chr) {
//        removeFromDB();
//        RandomQuestProvider.removeQuest(chr.getAccountID());
//        for (Item i : this.getReqItems()) {
//            MapleInventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(i.getItemId()), i.getItemId(), i.getQuantity(), true, false);
//            chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(i.getItemId(), (short) -i.getQuantity(), true));
//        }
//    }
//
//    public static RandomQuest loadFromDB(MapleCharacter mc) {
//        PreparedStatement ps = null;
//        ArrayList<Item> reqItem = new ArrayList<>();
//        try {
//            ps = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM `ancient_quest` WHERE `accid` = ?");
//            ps.setInt(1, mc.getClient().getAccID());
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                return null;
//            }
//            rs.beforeFirst();
//            while (rs.next()) {
//                reqItem.add(new Item(rs.getInt("itemid"), Byte.MIN_VALUE, rs.getShort("qty")));
//            }
//        } catch (SQLException se) {
//        } finally {
//            try {
//                ps.close();
//            } catch (Exception e) {
//            }
//        }
//        return new RandomQuest(mc, 0, 0, reqItem);
//    }
//
//    public void saveToDB() {
//        PreparedStatement ps = null;
//        try {
//            ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM `ancient_quest` WHERE `accid` = ?");
//            ps.setInt(1, this.getAccID());
//            ps.executeUpdate();
//            ps.close();
//
//            ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO `ancient_quest` (accid, itemid, qty) VALUES (?,?,?)");
//            ps.setInt(1, this.getAccID());
//            for (Item i : this.getReqItems()) {
//                ps.setInt(2, i.getItemId());
//                ps.setShort(3, i.getQuantity());
//                ps.addBatch();
//            }
//            ps.executeBatch();
//        } catch (SQLException sqe) {
//        } finally {
//            try {
//                ps.close();
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    private void removeFromDB() {
//        PreparedStatement ps = null;
//        try {
//            ps = DatabaseConnection.getConnection().prepareStatement("DELETE FROM `ancient_quest` WHERE `accid` = ?");
//            ps.setInt(1, this.getAccID());
//            ps.executeUpdate();
//            ps.close();
//        } catch (SQLException sqe) {
//        } finally {
//            try {
//                ps.close();
//            } catch (Exception e) {
//            }
//        }
//    }
//}
