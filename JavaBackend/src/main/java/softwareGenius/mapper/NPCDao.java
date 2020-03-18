package softwareGenius.mapper;

import softwareGenius.model.NPC;

public interface NPCDao {
    /**
     * add an NPC to NPCTable
     * @param npc
     * @return
     */
    Boolean addNPC(NPC npc);

    /**
     * delete an NPC from NPCTable
     * @param npcId
     * @return
     */
    Boolean deleteNPC(Integer npcId);

    /**
     * get a random NPC by a difficulty level from NPCTable
     * @param difficultyLevel
     * @return NPC object
     */
    NPC getNPCByDifficultyLevel(Integer difficultyLevel);
}
