package net.valueglobal.jira.plugins.FeedbackReport.rest;

//import java.sql.Date;
//import java.util.Date;

import java.sql.Timestamp;

import javax.xml.bind.annotation.*;
//@XmlRootElement(name = "question")
@XmlAccessorType(XmlAccessType.FIELD)

public class FeedbackReportResourceModel {

  //  @XmlElement(name = "value")
    
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String assignee;
	private String project;
	private String issuekey;
	private String summary;
	private String question;
	private String rating;
	private String overallrating;
	private String comment;	
	private Timestamp createdate;
/*	private String searchValue;
	private String dateFrom;
	private String dateTo;*/
	//private String reopened;
	
    public FeedbackReportResourceModel() {
    	
    	id = 0;
    }

    public FeedbackReportResourceModel(int id) {
        this.id = id;
    }

    public String getAssignee()
    {
    	return assignee;
    }
    public void setAssignee(String value)
    {
    	this.assignee = value;
    }
    
    public String getProject()
    {
    	return project;
    }
    public void setProject(String value)
    {
    	this.project = value;
    }
    public String getIssueKey()
    {
    	return issuekey;
    }
    public void setIssueKey(String value)
    {
    	this.issuekey = value;
    }
    
    public String getSummary()
    {
    	return summary;
    }
    public void setSummary(String summary)
    {
    	this.summary = summary;
    }
    
	  public String getQuestion()
	  {
		  return question;
	  }
	  public void setQuestion(String value)
	  {
		  this.question = value;
	  }
  
	  public String getRating()
	  {
		  return rating;
	  }
	  public void setRating(String rating)
	  {
		  this.rating = rating;
	  } 
	  
	    public String getOverallRating()
	    {
	    	return overallrating;
	    }
	    public void setOverallRating(String overallrating)
	    {
	    	this.overallrating = overallrating;
	    }
	    
	   public  String getComment()
	   {
	   return comment;
	   }
	   public  void setComment(String comment)
	   {
		   this.comment = comment;
	   }

	   public Timestamp getCreateDate() {
		return createdate;
	}
	public void setCreateDate(Timestamp date) {
		this.createdate =  date;
	}
/*	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}*/
	   
/*	   public  String getReopened()
	   {
	   return reopened;
	   }
	   public  void setReopened(String reopened)
	   {
		   this.reopened = reopened;
	   }*/
	  
/*	  @Override
		public int hashCode() {
			return new HashCodeBuilder(17, 37).
					append(id).
					append(assignee).
					append(issuekey).
					append(question).
					build();
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof FeedbackReportResourceModel) {
				FeedbackReportResourceModel other = (FeedbackReportResourceModel) o;
				return (id == other.id) ;
			}
			return false;
		}

		@SuppressWarnings("deprecation")
		@Override
		public String toString() {
			return Objects.toStringHelper(this).
					add("id", id).
					add("assignee", assignee).
					add("issuekey", issuekey).
					add("question", question).
					toString();
		}*/
	  
  
}