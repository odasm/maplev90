package server.quest.custom;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import client.MapleCharacter;
import client.MapleCustomQuestStatus;
import tools.MaplePacketCreator;
import tools.Pair;

public class CustomQuest
{
  private final int id;
  private final int skillId;
  private final boolean repeatable;
  private final Map<Integer, Integer> reqMobs;
  private final Map<Integer, Integer> reqItems;
  private List<CustomQuestRequirement> startReqs;
  private List<CustomQuestRequirement> completeReqs;
  
  protected CustomQuest(int id, int skillId, boolean repeatable, List<CustomQuestRequirement> startReqs, List<CustomQuestRequirement> completeReqs)
  {
    this.id = id;
    this.skillId = skillId;
    this.repeatable = repeatable;
    this.reqMobs = new LinkedHashMap();
    this.reqItems = new LinkedHashMap();
    this.startReqs = startReqs;
    this.completeReqs = completeReqs;
    for (CustomQuestRequirement cqr : completeReqs) {
      if (cqr.getType().equals(CustomQuestRequirementType.mob))
      {
        List<Pair<Integer, Integer>> mobList = cqr.getDataStore();
        for (Pair<Integer, Integer> mobs : mobList) {
          this.reqMobs.put(mobs.getLeft(), mobs.getRight());
        }
      }
      else if (cqr.getType().equals(CustomQuestRequirementType.item))
      {
        List<Pair<Integer, Integer>> itemss = cqr.getDataStore();
        for (Pair<Integer, Integer> it : itemss) {
          this.reqItems.put(it.getLeft(), it.getRight());
        }
      }
    }
  }
  
  public int getQuestId()
  {
    return this.id;
  }
  
  public int getSkillId()
  {
    return this.skillId;
  }
  
  public Map<Integer, Integer> getReqMobs()
  {
    return this.reqMobs;
  }
  
  public Map<Integer, Integer> getReqItems()
  {
    return this.reqItems;
  }
  
  public boolean canStart(MapleCharacter c, Integer npcid)
  {
    if ((c.getCustomQuest(this).getStatus() != 0) && ((c.getCustomQuest(this).getStatus() != 2) || (!this.repeatable))) {
      return false;
    }
    for (CustomQuestRequirement r : this.startReqs) {
      if (!r.check(c, npcid)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean canComplete(MapleCharacter c, Integer npcid)
  {
    if (c.getCustomQuest(this).getStatus() != 1) {
      return false;
    }
    for (CustomQuestRequirement r : this.completeReqs) {
      if (!r.check(c, npcid)) {
        return false;
      }
    }
    return true;
  }
  
  public boolean start(MapleCharacter c, int npc)
  {
    if (canStart(c, Integer.valueOf(npc)))
    {
      MapleCustomQuestStatus newStatus = new MapleCustomQuestStatus(this, (byte)1, npc);
      newStatus.setCompletionTime(c.getCustomQuest(this).getCompletionTime());
      c.updateCustomQuest(newStatus);
      return true;
    }
    return false;
  }
  
  public boolean complete(MapleCharacter c, int npc)
  {
    if (canComplete(c, Integer.valueOf(npc)))
    {
      MapleCustomQuestStatus newStatus = new MapleCustomQuestStatus(this, (byte)2, npc);
      c.updateCustomQuest(newStatus);
      
      c.getClient().getSession().write(MaplePacketCreator.showSpecialEffect(9));
      c.getMap().broadcastMessage(c, MaplePacketCreator.showSpecialEffect(c.getId(), 9), false);
      return true;
    }
    return false;
  }
}
