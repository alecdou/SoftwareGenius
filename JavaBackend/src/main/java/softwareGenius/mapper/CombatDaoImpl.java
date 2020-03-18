//package softwareGenius.mapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//import softwareGenius.model.Combat;
//
//import java.util.List;
//
//@Repository("CombatDao")
//public class CombatDaoImpl implements CombatDao {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public CombatDaoImpl(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public Integer initCombat(Combat combat) {
//        final String sql = "INSERT INTO " +
//                "combat (difficultyLevel, mode, playerId, npcId, status, totalNumOfQuestions, numOfCorrectAns) " +
//                "VALUES (?, ? ,? ,? ,? ,? ,?)";
//
//        jdbcTemplate.update(sql,
//                combat.getDifficultyLevel(),
//                combat.getMode(),
//                combat.getPlayerId(),
//                combat.getNpcId(),
//                combat.getStatus(),
//                combat.getTotalNumOfQuestions(),
//                combat.getNumOfCorrectAns());
//        return jdbcTemplate.
//    }
//
//    @Override
//    public Combat getCombatById(Integer combatId) {
//        return null;
//    }
//
//    @Override
//    public Boolean updateCombatResult(Integer combatId, String status, Integer totalNumOfQuestions, Integer numOfCorrectAns) {
//        return null;
//    }
//
//    @Override
//    public List<Combat> getCombatByPlayerId(Integer playerId) {
//        return null;
//    }
//
//    @Override
//    public List<Combat> getCombatByPlayerIdAndMode(Integer playerId, String mode) {
//        return null;
//    }
//}
