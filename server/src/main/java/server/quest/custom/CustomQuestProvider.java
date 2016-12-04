/*
 * This file is part of the OdinMS MapleStory Private Server
 * Copyright (C) 2011 Patrick Huy and Matthias Butz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package server.quest.custom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import database.DatabaseConnection;
import tools.Pair;

/**
 *
 * @author AuroX
 */
public class CustomQuestProvider {

    private final static Map<Integer, CustomQuest> quests = new LinkedHashMap<>();

    public static void initQuests() {
        if (!quests.isEmpty()) {
            return;
        }
        //System.out.println("Loading Custom Quest Data...");
        try {
            Connection con = DatabaseConnection.getConnection();
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM `customquestinfo`"); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    final int questid = rs.getInt("questid");
                    final int skillid = rs.getInt("skillid");
                    boolean repeatable = false;

                    final String[] partsA = rs.getString("startReqs").split(";");
                    final List<CustomQuestRequirement> start = new LinkedList<>();
                    for (final String mini : partsA) {
                        if (mini.equals("0,0,0")) {
                            break;
                        }
                        final String[] triple = mini.split(",");
                        final CustomQuestRequirementType type = CustomQuestRequirementType.getByWZName(triple[0]);

                        if (type.equals(CustomQuestRequirementType.interval)) {
                            repeatable = true;
                        }
                        start.add(new CustomQuestRequirement(questid, type, Integer.parseInt(triple[1]), new Pair<>(Integer.parseInt(triple[1]), Integer.parseInt(triple[2]))));  // id, quantity
                    }

                    final String[] partsB = rs.getString("completeReqs").split(";");
                    final List<CustomQuestRequirement> end = new LinkedList<>();
                    for (final String mini : partsB) {
                        if (mini.equals("0,0,0")) {
                            break;
                        }
                        final String[] triple = mini.split(",");
                        final CustomQuestRequirementType type = CustomQuestRequirementType.getByWZName(triple[0]);

                        if (type.equals(CustomQuestRequirementType.interval)) {
                            repeatable = true; // fall through
                        }
                        end.add(new CustomQuestRequirement(questid, type, Integer.parseInt(triple[1]), new Pair<>(Integer.parseInt(triple[1]), Integer.parseInt(triple[2]))));  // id, quantity
                    }

                    quests.put(questid, new CustomQuest(questid, skillid, repeatable, start, end));
                }
            }
           // System.out.println("Successfully loaded " + quests.size() + " custom quests");
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Failed to load Custom Quest data. Reason: " + e);
        }
    }

    private CustomQuestProvider() {
        // uninstantible
    }

    public static void clearQuests() {
        quests.clear();
        initQuests();
    }

    public static CustomQuest getInstance(int id) {
        CustomQuest ret = quests.get(id);
        if (ret == null) {
            //System.out.println("Custom quest without data " + id);
        }
        return ret;
    }
}
