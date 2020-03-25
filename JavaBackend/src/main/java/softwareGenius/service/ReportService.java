package softwareGenius.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softwareGenius.mapper.ReportDao;
import softwareGenius.model.Report;

import java.util.List;

@Service
public class ReportService {
    private final ReportDao reportDao;

    @Autowired
    public ReportService(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    /**
     * Add and initiate a new user to database with given user object
     * @param report report object
     * @return id of the report
     */

    public Integer addNewReport(Report report) {
        return reportDao.addReport(report);
    }

    /**
     * Get User by the given userId
     * @param userId id of the user
     * @return the matching report object
     */
    public Report getReportByUserId(Integer userId) {
        return reportDao.getReportByUserId(userId);
    }

    /**
     * Get User by the given userId
     * @param reportId id of the report
     * @return the matching report object
     */
    public Report getReportByReportId(Integer reportId) {
        return reportDao.getReportByReportId(reportId);
    }

    /**
     * Get all reports
     * @return a list of all report objects
     */
    public List<Report> getAll() {
        return reportDao.getAll();
    }

    /**
     * Delete report with given userId
     * @param userId id of the user
     * @return status of the request (ex. True if succeed)
     */
    public Boolean deleteUser(Integer userId) {
        return reportDao.deleteReport(userId);
    }

}
