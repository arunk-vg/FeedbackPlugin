package net.valueglobal.jira.plugins.FeedbackReport.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import net.valueglobal.jira.plugins.FeedbackReport.rest.FeedbackReportResourceModel;

public interface FeedbackReportService {
	//ProjectModel saveProject(ProjectModel project, String keyFilter);	
	List<FeedbackReportResourceModel> getAllReports();
    FeedbackReportResourceModel setAllReports(FeedbackReportResourceModel report);
	FeedbackReportResourceModel getReportById(String repId);
	void removeTemplate(FeedbackReportResourceModel rep);
	List<FeedbackReportResourceModel> getReportByString(String key, Timestamp from,Timestamp to,String keyFilter);
	List<FeedbackReportResourceModel> getReportByString(String key,String keyFilter);
	List<FeedbackReportResourceModel> getReportByProjectKey(String key); 


}
