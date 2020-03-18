package softwareGenius.service;

import org.springframework.stereotype.Service;
import softwareGenius.mapper.NPCDao;
import softwareGenius.model.NPC;

@Service
public class NPCService {
    private NPCDao npcDao;

    public NPCService(NPCDao npcDao) {
        this.npcDao = npcDao;
    }

    /**
     * get an NPC by the difficulty level
     * @param difficultyLevel the difficulty level of NPC
     * @return NPC object
     */
    public NPC getNPCByDifficultyLevel(Integer difficultyLevel) {
        return npcDao.getNPCByDifficultyLevel(difficultyLevel);
    }
}
