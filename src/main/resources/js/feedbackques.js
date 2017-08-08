jQuery(function(){
    var propRest = contextPath + "/rest/feedbackquestions/latest/admin-templates";
   // console.log(propRest);
    var appPropertyTable = jQuery("#project-feedbackques-table");
        new AJS.RestfulTable({
        	autoFocus: true,
        	allowReorder: true,
        	el: appPropertyTable, // table to add entries to. Entries are appended to the tables <tbody> element
            resources: {
                all: propRest,
                self: propRest
            },
            columns: [
            
               	{
                    id: "projectKey",
                    header: "Project Key",
                    allowEdit: false,
                    allowCreate: false,

                },
                
               {
                    id: "question",
                    header: "Question",
                    allowEdit: true
                },
             
               	{
                    id: "weightage",
                    header: "Weightage",
                    allowEdit: true
                }              
            ],
            allowCreate: true,
            allowDelete: true,
          
        });

// API Button Call        
        
/*        jQuery( "#apiButton" ).click(function() {
      	  alert( "Handler for .click() called." );  	
      	 // var iss = "TRN-99";
        	 jQuery.ajax(
        			 {
        				 url: contextPath + "/rest/api/2/search?jql=project=GDEV&maxResults=100",
        				 async: true,
        				 success: function(output) 
        				 {
        					// var dateCreated = output.fields.created;
        					 console.log(output);
        					date = new Date(dateCreated);
        					console.log(date);
        				//createddate = dateCreated;//(date.getFullYear()+'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+("0" + date.getDate()).slice(-2));//prints expected format.

        				//	createddate = getFormattedDate(dateCreated);
        					 
        					// console.log(createddate);
        				 },
        				 error: function (xhr, ajaxOptions, thrownError) {
        	       alert(xhr.status + " "+ thrownError);
        	     }});
      	//return "success";
        	 var issueKey = "TRN-99"
        	 jQuery.ajax(
        			 {
          				 url: contextPath + "/rest/api/2/issue/"+ issueKey,
        				 async: true,
        				 maxResults:100,
        				 success: function(output) 
        				 {
        					// var dateCreated = output.fields.created;
        					 console.log(output);
        					date = new Date(dateCreated);
        					console.log(date);
        				//createddate = dateCreated;//(date.getFullYear()+'-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-'+("0" + date.getDate()).slice(-2));//prints expected format.

        				//	createddate = getFormattedDate(dateCreated);
        					 
        					// console.log(createddate);
        				 },
        				 error: function (xhr, ajaxOptions, thrownError) {
        	       alert(xhr.status + " "+ thrownError);
        	     }});
      });*/
        
        
        AJS.RestfulTable.EntryModel = Backbone.Model.extend({
            /**
             * Overrides default save handler to only save (send to server) attributes that have changed.
             * Also provides some default error handling.
             *
             * @override
             * @param attributes
             * @param options
             */
            save: function (attributes, options) {
            	//alert(options);
                options = options || {};
            	console.log("Attributes : ",attributes);

                var instance = this,
                    Model,
                    syncModel,
                    error = options.error, // we override, so store original
                    success = options.success;

                // override error handler to provide some defaults
                options.error = function (model, xhr) {

                	//console.log(xhr.responseText);

                    var data = AJS.$.parseJSON(xhr.responseText || xhr.data);

                    //console.log("DATA : ",data);
                    //console.log("DATA : ",data.message.Error);
                    //console.log("DATA : ",data.message);

                    if(data.messageList)                    	
                    {
                    	console.log("Message List : ",data.messageList);
                    	console.log("attributes 1:",attributes);

                    JIRA.Messages.showErrorMsg(data.message, {closeable: false, timeout: 5});
                    }
                    
                    instance._serverErrorHandler(xhr);

                    // call original error handler
                    if (error) {
                        error.call(instance, instance, data, xhr);
                    }
                };

                // if it is a new model, we don't have to worry about updating only changed attributes because they are all new
                if (this.isNew()) {
                //	console.log("attributes 2:",attributes);
                    // call super
                    Backbone.Model.prototype.save.call(this, attributes, options);

                // only go to server if something has changed
                } else if (attributes) {
                    // create temporary model
                    Model = Backbone.Model.extend({
                        url: this.url()
                    });


                    syncModel = new Model({
                        id: this.id
                    });

                    options.success = function (model, xhr) {

                        // update original model with saved attributes
                        instance.clear().set(model.toJSON());

                        // call original success handler
                        if (success) {
                            success.call(instance, instance, xhr);
                        }
                    };

                    // update temporary model with the changed attributes
                    syncModel.save(attributes, options);
                }
            },

        _serverErrorHandler: function (xhr) {
            var data;
            if (xhr.status !== 400) {
                data = AJS.$.parseJSON(xhr.responseText || xhr.data);
                AJS.triggerEvtForInst(AJS.RestfulTable.Events.SERVER_ERROR, this, [data, xhr]);
                }
            },
            
          
           fetch: function (options) {

               options = options || {};

               var instance = this,
                   error = options.error;

               this.clear(); // clear the model, so we do not merge the old with the new

               options.error = function (model, xhr) {
                   instance._serverErrorHandler(xhr);
                   if (error) {
                       error.apply(this, arguments);
                   }
               };

               // call super
               Backbone.Model.prototype.fetch.call(this, options);
           }

        });

        
        
});


