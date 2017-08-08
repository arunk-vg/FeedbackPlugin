package net.valueglobal.jira.plugins.FeedbackReport.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.valueglobal.jira.plugins.FeedbackReport.dao.FeedbackReportDao;// yet to create
import net.valueglobal.jira.plugins.FeedbackReport.rest.FeedbackReportResourceModel;
import net.valueglobal.jira.plugins.FeedbackReport.service.FeedbackReportService;

import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
//import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
public class DefaultFeedbackReportService implements FeedbackReportService  {

	

	private final FeedbackReportDao feedbackReportDao; //yet to create
	

	
/*	private final Function<ProjectModel, Template> projectToTemplateFunction = new Function<ProjectModel, Template>() {
		@Override
		public Template apply(@Nullable ProjectModel from) {
			if (from == null) {
				return null;
			}
			Template template=getTemplateById(from.getId());
			return template;
		}
	};*/
	
	public DefaultFeedbackReportService(FeedbackReportDao feedbackReportDao) {
		super();
		this.feedbackReportDao = feedbackReportDao;

	}



@Override
public List<FeedbackReportResourceModel> getAllReports() {
	// TODO Auto-generated method stub
	return feedbackReportDao.getAll();
}

@Override
public FeedbackReportResourceModel setAllReports(FeedbackReportResourceModel report) {
	// TODO Auto-generated method stub
	return feedbackReportDao.setAll(report);
}

public FeedbackReportResourceModel getReportById(int repId) {
	return feedbackReportDao.get(repId);
}	


@Override
public FeedbackReportResourceModel getReportById(String repId) {
	int tempId = Integer.parseInt(repId);
	return getReportById(tempId);
}

@Override
public void removeTemplate(FeedbackReportResourceModel rep) {
	feedbackReportDao.remove(rep.getId());
}
/*	@Override
	public List<ProjectModel> getAllTemplates() {
		return projectDao.getAll();*/



@Override
public List<FeedbackReportResourceModel> getReportByString(String key, Timestamp from, Timestamp to,String keyFilter) 
{
	return feedbackReportDao.getByString(key,from,to,keyFilter);
}

@Override
public List<FeedbackReportResourceModel> getReportByString(String key,String keyFilter) 
{
	return feedbackReportDao.getByString(key,keyFilter);
}
@Override
public List<FeedbackReportResourceModel> getReportByProjectKey(String key) 
{
	return feedbackReportDao.getByProjectKey(key);
}
	
}



