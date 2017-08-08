package net.valueglobal.jira.plugins.feedback.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.common.base.Objects;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectModel {
	

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	private String projectKey;
	private String question;
	private String weightage;
	private String message;
	private boolean	messageList;
	
	
	
	public String getProjectKey() {
		return projectKey;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	
	public ProjectModel() {
		id = 0;
	}

	public ProjectModel(int id) {
		this.id = id;
	}

/*	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).
				append(id).
				append(projectKey).
				append(question).
				append(weightage).				
				build();
	}*/

	/*@Override
	public boolean equals(Object o) {
		if (o instanceof ProjectModel) {
			ProjectModel other = (ProjectModel) o;
			return (id == other.id) ;
		}
		return false;
	}*/

/*	@Override
	public String toString() {
		return Objects.toStringHelper(this).
				add("id", id).
				add("projectKey", projectKey).
				add("question", question).
				add("weightage", weightage).
				toString();
	}*/
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isMessageList() {
		return messageList;
	}
	public void setMessageList(boolean messageList) {
		this.messageList = messageList;
	}

}
