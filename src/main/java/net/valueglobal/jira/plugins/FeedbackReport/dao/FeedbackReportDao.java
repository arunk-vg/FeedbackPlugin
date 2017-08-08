package net.valueglobal.jira.plugins.FeedbackReport.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import net.valueglobal.jira.plugins.FeedbackReport.rest.FeedbackReportResourceModel;

public interface FeedbackReportDao {
	
	//ProjectModel get(int projectId);
	List<FeedbackReportResourceModel> getAll();
	FeedbackReportResourceModel setAll(final FeedbackReportResourceModel report);
	FeedbackReportResourceModel get(int repId);
	void remove(int repId);
	List<FeedbackReportResourceModel> getByString(String key, Timestamp from, Timestamp to, String keyFilter);
	List<FeedbackReportResourceModel> getByString(String key, String keyFilter);//,Timestamp from,Timestamp to);	
	List<FeedbackReportResourceModel> getByProjectKey(final String key); 

	}
