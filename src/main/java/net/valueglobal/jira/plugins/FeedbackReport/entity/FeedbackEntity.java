package net.valueglobal.jira.plugins.FeedbackReport.entity;

import java.sql.Timestamp;

import net.java.ao.Entity;
import net.java.ao.Preload;
@Preload
public interface FeedbackEntity extends Entity {
    // This could equally have used an Integer 
	String Assignee = "ASSIGNEE";
	String PROJECT = "PROJECT";
	String ISSUE_KEY = "ISSUE_KEY";
	String SUMMARY = "SUMMARY";
	String QUESTION = "QUESTION";
	String RATING = "RATING";
	String OVERALLRATING = "OVERALL_RATING";
	String COMMENT = "COMMENT";
	String CREATE_DATE = "CREATE_DATE";
	
	String getAssignee();
	void setAssignee(String assignee);
	
	String getProject();
	void setProject(String project);
	
	String getIssueKey();
	void setIssueKey(String issuekey);
	
	String getSummary();
	void setSummary(String summary);
	
    String getQuestion();
    void  setQuestion(String question);
    
    String getRating();
    void  setRating(String rating);
    
    String getOverallRating();
    void setOverallRating(String overallrating);
    
    String getComment();
    void setComment(String comment);

	Timestamp getCreateDate();
	void setCreateDate(Timestamp createdate);

}
