var dataProjectKey; 
jQuery(function(){


   // var propRest = contextPath + "/rest/feedbackquestions/latest/project-templates";
   // var appPropertyTable = jQuery("#project-feedbackques-table");
    
	var $el = AJS.$('#project-feedbackques-table');

	dataProjectKey = $el.attr('data-project-key');
	console.log(dataProjectKey);
	var propRest = contextPath + "/rest/feedbackquestions/latest/project-templates?keyFilter=" + dataProjectKey;
	var selfPropRest = contextPath + "/rest/feedbackquestions/latest/project-templates";
   	var appPropertyTable = jQuery("#project-feedbackques-table");
        new AJS.RestfulTable({
        	autoFocus: true,
        	allowReorder: false,
        	el: appPropertyTable, // table to add entries to. Entries are appended to the tables <tbody> element
            resources: {
                all: propRest,
                self: selfPropRest
            },
            columns: [
            
               /*	{
                    id: "projectKey",
                    header: "Project Key",
                    allowEdit: true
                },*/
                
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
           // allowEdit: true,
            allowCreate: true,
            allowDelete: true
            
            
        });                       
});


/**
 * A class provided to fill some gaps with the out of the box Backbone.Model class. Most notiably the inability
 * to send ONLY modified attributes back to the server.
 *
 * @class EntryModel
 * @namespace AJS.RestfulTable
 */
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
    	console.log("Attributes : ",attributes);

attributes.projectKey = dataProjectKey;
console.log("console 4:",attributes.projectKey);
        options = options || {};

        var instance = this,
            Model,
            syncModel,
            error = options.error, // we override, so store original
            success = options.success;

        // override error handler to provide some defaults
        options.error = function (model, xhr) {

            var data = AJS.$.parseJSON(xhr.responseText || xhr.data);

            console.log("DATA : ",data);
            console.log("DATA : ",data.message.Error);
            console.log("DATA : ",data.message);
console.log(data);
            if(data.messageList)
            {
            	console.log("attributes 1:",attributes);

            JIRA.Messages.showErrorMsg(data.message, {closeable: false, timeout: 10});
            }
            
            
            instance._serverErrorHandler(xhr);

            // call original error handler
            if (error) {
                error.call(instance, instance, data, xhr);
            }
        };

        // if it is a new model, we don't have to worry about updating only changed attributes because they are all new
        if (this.isNew()) {
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
        
  
        
});
