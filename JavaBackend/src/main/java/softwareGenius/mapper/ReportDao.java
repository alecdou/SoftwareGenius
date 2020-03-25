package softwareGenius.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import softwareGenius.model.Report;
import java.util.List;

@Mapper
@Component
public interface ReportDao {
    /**
     * Insert new report object to the database
     * @param report report of the given user
     * @return id of the report
     */
    Integer addReport(Report report);

    /**
     * Fetch the report object with matching userId
     * @param userId unique id of the user
     * @return Report object
     */
    Report getReportByUserId(Integer userId);

    /**
     * Fetch the report object with matching reportId
     * @param reportId unique id of the report
     * @return Report object
     */
    Report getReportByReportId(Integer reportId);

    /**
     * Get all reports
     * @return List of Report objects
     */
    List<Report> getAll();

    /**
     * Delete the report with matching userId
     * @param userId unique id the user
     * @return status of the query (ex. True if query succeed)
     */
    Boolean deleteReport(Integer userId);

    /**
     * Update the report object with the given new user
     * @param report new report obejct with updated fields
     * @return status of the query (ex. True if query succeed)
     */
    Boolean updateReport(Report report);
}