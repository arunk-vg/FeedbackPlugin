package net.valueglobal.jira.plugins.feedback.ao;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Unique;

@Preload
public interface ProjectMapping extends Entity {

	String PROJECT_KEY = "PROJECT_KEY";
	String QUESTION = "QUESTION";
	String WEIGHTAGE = "WEIGHTAGE";

	
	
	String getProjectKey();
	void setProjectKey(String projectKey);
	
	@Unique
	@NotNull
	String getQuestion();
	void setQuestion(String question);
	
	@NotNull
	String getWeightage();
	void setWeightage(String weightage);
	
	
	
}
