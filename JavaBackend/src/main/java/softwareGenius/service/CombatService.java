package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.CombatDao;
import softwareGenius.model.Combat;
import softwareGenius.model.Question;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        combat.setStatus("pending");
        LocalDateTime now = LocalDateTime.now();
        combat.setCombatTime(Timestamp.valueOf(now));
        combat.setTotalNumOfQuestions(0);
        combat.setNumOfCorrectAns(0);
        combat.setEndTime(Timestamp.valueOf(now));
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

    public Boolean updateCombatResult(Integer combatId, String status, Integer totalNumOfQns, Integer numOfCorrectAns) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp endTime = Timestamp.valueOf(now);
        return combatDao.updateCombatResult(combatId, status, totalNumOfQns, numOfCorrectAns, endTime);
    }



}
