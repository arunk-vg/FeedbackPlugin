package net.valueglobal.jira.plugins.FeedbackReport.dao;

import java.sql.Timestamp;
//import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import net.java.ao.Query;
import net.valueglobal.jira.plugins.FeedbackReport.entity.FeedbackEntity;
import net.valueglobal.jira.plugins.FeedbackReport.dao.FeedbackReportDao;
import net.valueglobal.jira.plugins.FeedbackReport.rest.FeedbackReportResourceModel;


public class DefaultFeedbackReportDao implements FeedbackReportDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFeedbackReportDao.class);
	
	private final ActiveObjects ao;
	public boolean flag;
	private final Function<FeedbackEntity, FeedbackReportResourceModel> projectMappingToProjectFunction = new Function<FeedbackEntity, FeedbackReportResourceModel>() {
		@Override
		public FeedbackReportResourceModel apply(@Nullable FeedbackEntity from) {
			if (from == null) {
				return null;
			}
			FeedbackReportResourceModel report = new FeedbackReportResourceModel(from.getID());
			report.setAssignee(from.getAssignee());
			report.setProject(from.getProject());
			report.setIssueKey(from.getIssueKey());
			report.setSummary(from.getSummary());
			report.setQuestion(from.getQuestion());
			report.setQuestion(from.getQuestion());
			report.setRating(from.getRating());	
			report.setOverallRating(from.getOverallRating());
			report.setComment(from.getComment());
			report.setCreateDate(from.getCreateDate());
			//report.setReopened(from.getReopened());
			return report;			
		}
	};

	public DefaultFeedbackReportDao(ActiveObjects ao) {
		this.ao = ao;
	}
	@Override
	public FeedbackReportResourceModel get(final int repId) {
		FeedbackEntity mapping = ao.executeInTransaction(new TransactionCallback<FeedbackEntity>() {
			@Override
			public FeedbackEntity doInTransaction() {
								return ao.get(FeedbackEntity.class, repId);
			}
		});
		return projectMappingToProjectFunction.apply(mapping);
	}
	
	@Override
	public List<FeedbackReportResourceModel> getAll() {
		final List<FeedbackEntity> entity = ao.executeInTransaction(new TransactionCallback<List<FeedbackEntity>>() {
			@Override
			public List<FeedbackEntity> doInTransaction() {
				return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().order("PROJECT,ISSUE_KEY ASC,SUMMARY,QUESTION DESC,OVERALL_RATING ASC,COMMENT")));
			}
		});
		return Lists.transform(entity, projectMappingToProjectFunction);
	}
	
	@Override
	public FeedbackReportResourceModel setAll(final FeedbackReportResourceModel report) {
		LOGGER.debug("Saving {}", report.toString());
		FeedbackEntity mapping = ao.executeInTransaction(new TransactionCallback<FeedbackEntity>() {
			@Override
			public FeedbackEntity doInTransaction() {
				final FeedbackEntity mapping = ao.create(FeedbackEntity.class);
				mapping.setAssignee(report.getAssignee());	
				mapping.setProject(report.getProject());
				mapping.setIssueKey(report.getIssueKey());
				mapping.setSummary(report.getSummary());
				mapping.setQuestion(report.getQuestion());
				mapping.setRating(report.getRating());
				mapping.setOverallRating(report.getOverallRating());
				mapping.setComment(report.getComment());	
				mapping.setCreateDate(report.getCreateDate());
			/*	Timestamp tm = report.getCreatedDate();
				System.out.println("System output for feedback questions" + tm);
				mapping.setCreatedDate(tm);	*/

				
				mapping.save();
				return mapping;			
				
			}
		});
		System.out.println("System output for feedback questions" + mapping);
		return projectMappingToProjectFunction.apply(mapping);
	}
	
	
/*	public int reOpenedStatus(String issKey){
		final int countOfissue = ao.count(FeedbackReport.class, Query.select().where("ISSUE_KEY = ?", issKey));				
		flag = true;
		return countOfissue;		
	}*/

	@Override
	public void remove(int repId) {
		ao.delete(ao.get(FeedbackEntity.class, repId));		
	}
	
/*	public List<FeedbackReportResourceModel> getByString(final String key, final Timestamp from, final Timestamp to) {
		       
		final List<FeedbackEntity> entity = ao.executeInTransaction(new TransactionCallback<List<FeedbackEntity>>() {
			@Override
			public List<FeedbackEntity> doInTransaction() {
			//	String val = null;
			//	key.trim();
				String text = "1970-01-01 00:00:00";
		        Timestamp ts = Timestamp.valueOf(text);
		        if(to.equals(ts))
			        {
						return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" >= ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",from).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));
			        }
		        else if(from.equals(ts))
			        {
						return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" <= ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",to).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));	
			        }
		       
		        else
		        {
		   			return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" BETWEEN ? AND ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",from,to).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));

		        }
			}
		});
		return Lists.transform(entity, projectMappingToProjectFunction);
	}
*/
	
public List<FeedbackReportResourceModel> getByString(final String key, final Timestamp from, final Timestamp to, final String keyFilter) 
	{
	       
		final List<FeedbackEntity> entity = ao.executeInTransaction(new TransactionCallback<List<FeedbackEntity>>() {
			@Override
			public List<FeedbackEntity> doInTransaction() {
			//	String val = null;
			//	key.trim();
				String text = "1970-01-01 00:00:00";
		        Timestamp ts = Timestamp.valueOf(text);
		        if(keyFilter != null){
		        	System.out.println(" Key Filter :"+keyFilter);
		        	System.out.println(" From Date :"+from);
		        	System.out.println(" From Date :"+to);
		        	if(to.equals(ts))
			        {
		        		System.out.println("If Block");
						return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" >= ?) AND (LOWER(\"ISSUE_KEY\") LIKE LOWER(?))","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%", "%" + key + "%",from,keyFilter + "%").order("ISSUE_KEY ASC,OVERALL_RATING ASC")));
			        }
		        	else if(from.equals(ts))
			        {System.out.println("Else If Block");
						return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" <= ?) AND (LOWER(\"ISSUE_KEY\") LIKE LOWER(?))","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",to,keyFilter + "%").order("ISSUE_KEY ASC,OVERALL_RATING ASC")));	
			        }		       
		        	else
		        	{System.out.println("Else Block");
		        		return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (LOWER(\"ISSUE_KEY\") LIKE LOWER(?)) AND (\"CREATE_DATE\" >= ? AND \"CREATE_DATE\" <= ?)","%" + key + "%","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",keyFilter + "%",from,to).order("PROJECT,ISSUE_KEY ASC,QUESTION DESC,OVERALL_RATING ASC,COMMENT")));
		        	}
		        }
		        else
		        {
		        	System.out.println(" Key Filter :"+keyFilter);
		        	System.out.println(" From Date :"+from);
		        	System.out.println(" From Date :"+to);
		        	if(to.equals(ts))
			        {
						return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" >= ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",from).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));
			        }
		        	else if(from.equals(ts))
			        {
						return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" <= ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",to).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));	
			        }
		       
			        else
			        {
			   			return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?))","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%").order("PROJECT,ISSUE_KEY ASC,QUESTION DESC,OVERALL_RATING ASC,COMMENT")));
			        }
		        }	
			}
		});
		return Lists.transform(entity, projectMappingToProjectFunction);
	}

public List<FeedbackReportResourceModel> getByString(final String key, final String keyFilter) 
{
       
	final List<FeedbackEntity> entity = ao.executeInTransaction(new TransactionCallback<List<FeedbackEntity>>() {
		@Override
		public List<FeedbackEntity> doInTransaction() {
		//	String val = null;
		//	key.trim();
		/*	String text = "1970-01-01 00:00:00";
	        Timestamp ts = Timestamp.valueOf(text);
	        if(to.equals(ts))
		        {
					return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" >= ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",from).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));
		        }
	        else if(from.equals(ts))
		        {
					return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (\"CREATE_DATE\" <= ?)","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",to).order("ISSUE_KEY ASC,OVERALL_RATING ASC")));	
		        }
	       
	        else
	        {*/
			if(keyFilter != null){
	   			return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?)) AND (LOWER(\"ISSUE_KEY\") LIKE LOWER(?))","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%",keyFilter + "%").order("PROJECT,ISSUE_KEY ASC,QUESTION DESC,OVERALL_RATING ASC,COMMENT")));

			}
			else{
	   			return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("(LOWER(\"ASSIGNEE\") LIKE LOWER(?) OR LOWER(\"PROJECT\") LIKE LOWER(?) OR LOWER(\"ISSUE_KEY\") LIKE LOWER(?) OR LOWER(\"SUMMARY\") LIKE LOWER(?) OR LOWER(\"QUESTION\") LIKE LOWER(?) OR RATING LIKE ? OR OVERALL_RATING LIKE ? OR LOWER(\"COMMENT\") LIKE LOWER(?))","%" + key + "%", "%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%","%" + key + "%" ,"%" + key + "%","%" + key + "%").order("PROJECT,ISSUE_KEY ASC,QUESTION DESC,OVERALL_RATING ASC,COMMENT")));

			}
	    //   }
		}
	});
	return Lists.transform(entity, projectMappingToProjectFunction);
}

public List<FeedbackReportResourceModel> getByProjectKey(final String key) 
{
       
	final List<FeedbackEntity> entity = ao.executeInTransaction(new TransactionCallback<List<FeedbackEntity>>() {
		@Override
		public List<FeedbackEntity> doInTransaction() {
	   			return Arrays.asList(ao.find(FeedbackEntity.class, Query.select().where("ISSUE_KEY LIKE ? ", key + "%").order("PROJECT,ISSUE_KEY ASC,QUESTION DESC,OVERALL_RATING ASC,COMMENT")));
	   					//where("(LOWER(\"ISSUE_KEY\") LIKE LOWER(?)","%" + key + "%")));
		}
	});
	return Lists.transform(entity, projectMappingToProjectFunction);
}

}
	