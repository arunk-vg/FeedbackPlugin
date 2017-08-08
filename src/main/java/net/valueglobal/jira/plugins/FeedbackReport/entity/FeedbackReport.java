package net.valueglobal.jira.plugins.FeedbackReport.entity;
//import java.sql.Date;
//import java.util.Date;

//import org.joda.time.DateTime;


import net.java.ao.Entity;
import net.java.ao.Preload;
import java.sql.Timestamp;

@Preload
public interface FeedbackReport extends Entity {
    // This could equally have used an Integer 
	String Assignee = "ASSIGNEE";
	String PROJECT = "PROJECT";
	String ISSUE_KEY = "ISSUE_KEY";
	String QUESTION = "QUESTION";
	String RATING = "RATING";
	String OVERALLRATING = "OVERALL_RATING";
	String COMMENT = "COMMENT";	
	String CREATEDDATE = "CREATED_DATE";
	
	//String REOPENED = "REOPENED";
	

	
	String getAssignee();
	void setAssignee(String assignee);
	
	String getProject();
	void setProject(String project);
	
	String getIssueKey();
	void setIssueKey(String issuekey);
	
    String getQuestion();
    void  setQuestion(String question);
    
    String getRating();
    void  setRating(String rating);
    
    String getOverallRating();
    void setOverallRating(String overallrating);
    
    String getComment();
    void setComment(String comment);  
    
	Timestamp getCreatedDate();
	void setCreatedDate(Timestamp createddate);
    
/*    String getReopened();
    void  setReopened(String reopened);*/
}
