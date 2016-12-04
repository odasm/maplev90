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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import client.MapleCharacter;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import tools.Pair;

/**
 *
 * @author AuroX
 */
public class CustomQuestRequirement implements Serializable {

    private static final long serialVersionUID = 8666501779674897071L;
    private CustomQuestRequirementType type;
    private int questId, intStore;
    private List<Pair<Integer, Integer>> dataStore;

    public CustomQuestRequirement(int quest, CustomQuestRequirementType type, int data, Pair<Integer, Integer> data2) {
        this.type = type;
        this.questId = quest;
        switch (type) {
            case interval:
            case lvmax:
            case lvmin: {
                intStore = data;
                break;
            }
            case item:
            case mob: {
                dataStore = new LinkedList<>();
                dataStore.add(data2);
                break;
            }
        }
    }

    public List<Pair<Integer, Integer>> getDataStore() {
        return dataStore;
    }

    public boolean check(MapleCharacter c, Integer npcid) {
        switch (type) {
            case interval:
                return c.getCustomQuest(CustomQuestProvider.getInstance(questId)).getStatus() != 2 || c.getCustomQuest(CustomQuestProvider.getInstance(questId)).getCompletionTime() <= System.currentTimeMillis() - intStore * 60 * 1000L;
            case lvmin:
                return c.getLevel() >= intStore;
            case lvmax:
                return c.getLevel() <= intStore;
            case item:
                MapleInventoryType iType;
                int itemId;
                short quantity;

                for (Pair<Integer, Integer> a : dataStore) {
                    itemId = a.getLeft();
                    quantity = 0;
                    iType = GameConstants.getInventoryType(itemId);
                    for (IItem item : c.getInventory(iType).listById(itemId)) {
                        quantity += item.getQuantity();
                    }
                    final int count = a.getRight();
                    if (quantity < count || count <= 0 && quantity > 0) {
                        return false;
                    }
                }
                return true;
            case mob:
                for (Pair<Integer, Integer> a : dataStore) {
                    final int mobId = a.getLeft();
                    final int killReq = a.getRight();
                    if (c.getCustomQuest(CustomQuestProvider.getInstance(questId)).getMobKills(mobId) < killReq) {
                        return false;
                    }
                }
                return true;
            default:
                return true;
        }
    }

    public CustomQuestRequirementType getType() {
        return type;
    }
}