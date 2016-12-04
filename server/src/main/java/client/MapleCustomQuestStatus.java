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
package client;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import server.quest.custom.CustomQuest;

/**
 *
 * @author AuroX
 */
public class MapleCustomQuestStatus implements Serializable {

    private static final long serialVersionUID = 3293978661695566906L;
    private transient CustomQuest quest;
    private byte status;
    private int npc;
    private long completionTime;
    private String customData;
    private Map<Integer, Integer> killedMobs = null;

    public MapleCustomQuestStatus(final CustomQuest quest, final byte status) {
        this.quest = quest;
        this.setStatus(status);
        this.completionTime = System.currentTimeMillis();
        if (status == 1 && quest != null) { // Started
            if (quest.getReqMobs() != null && !quest.getReqMobs().isEmpty()) {
                registerMobs();
            }
        }
    }

    public MapleCustomQuestStatus(final CustomQuest quest, final byte status, final int npc) {
        this.quest = quest;
        this.setStatus(status);
        this.npc = npc;
        this.completionTime = System.currentTimeMillis();
        if (status == 1) { // Started
            if (!quest.getReqMobs().isEmpty()) {
                registerMobs();
            }
        }
    }

    public final CustomQuest getQuest() {
        return quest;
    }

    public final byte getStatus() {
        return status;
    }

    public final void setStatus(final byte status) {
        this.status = status;
    }

    public final int getNpc() {
        return npc;
    }

    private void registerMobs() {
        killedMobs = new LinkedHashMap<>();
        for (final int i : quest.getReqMobs().keySet()) {
            killedMobs.put(i, 0);
        }
    }

    public int maxMob(final int mobid) {
        for (final Map.Entry<Integer, Integer> qs : quest.getReqMobs().entrySet()) {
            if (qs.getKey() == mobid) {
                return qs.getValue();
            }
        }
        return 0;
    }

    public final boolean mobKilled(final int id, final int skillID) {
        if (quest != null && quest.getSkillId() > 0) {
            if (quest.getSkillId() != skillID) {
                return false;
            }
        }
        final Integer mob = killedMobs.get(id);
        if (mob != null) {
            final int mo = maxMob(id);
            if (mob >= mo) {
                return false; //nothing happened
            }
            killedMobs.put(id, Math.min(mob + 1, mo));
            return true;
        }
        return false;
    }

    public final void setMobKills(final int id, final int count) {
        if (killedMobs == null) {
            registerMobs();
        }
        killedMobs.put(id, count);
    }

    public final boolean hasMobKills() {
        if (killedMobs == null) {
            return false;
        }
        return killedMobs.size() > 0;
    }

    public final int getMobKills(final int id) {
        final Integer mob = killedMobs.get(id);
        if (mob == null) {
            return 0;
        }
        return mob;
    }

    public final Map<Integer, Integer> getMobKills() {
        return killedMobs;
    }

    public final long getCompletionTime() {
        return completionTime;
    }

    public final void setCompletionTime(final long completionTime) {
        this.completionTime = completionTime;
    }

    public final void setCustomData(final String customData) {
        this.customData = customData;
    }

    public final String getCustomData() {
        return customData;
    }
}