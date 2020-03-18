package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.CombatDao;
import softwareGenius.model.Combat;
import softwareGenius.model.Question;

import java.util.List;

@Service
public class CombatService {

    @Autowired
    private CombatDao combatDao;
    @Autowired
    private LandService landService;
    @Autowired
    private NPCService npcService;
    @Autowired
    private QuestionService questionService;

    public Integer startNewCombat(Combat combat) {
        return combatDao.addCombat(combat);
    }

    public Combat getCombatById(Integer combatId) {
        return combatDao.getCombatById(combatId);
    }

    public List<Combat> getCombatByPlayerId(Integer playerId) {
        return combatDao.getCombatByPlayerId(playerId);
    }

    public List<Combat> getCombatByPlayerIdAndMode(Integer playerId, String mode) {
        return combatDao.getCombatByPlayerIdAndMode(playerId, mode);
    }



}
