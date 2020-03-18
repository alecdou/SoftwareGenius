package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.CombatDao;
import softwareGenius.model.Combat;
import softwareGenius.model.Question;

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

    Boolean
    startNewCombat(Combat combat) {

    }



}
