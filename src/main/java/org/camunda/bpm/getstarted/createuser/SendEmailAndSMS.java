package org.camunda.bpm.getstarted.createuser;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class SendEmailAndSMS {
    private final static Logger LOGGER = Logger
            .getLogger(SendEmailAndSMS.class.getName());

    /**
     * @param client
     */
    public static void subscribeAndExecuteEmailAndSmsWorkflow(ExternalTaskClient client) {
        // subscribe to an external task topic as specified in the process
        client.subscribe("sendEmailAndSms").lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    // Get a process variable
                    LOGGER.info(
                            "*************** SMS EMAIL & SMS SENT ***************** ");
                    // Complete the task
                    externalTaskService.complete(externalTask);
                }).open();
    }
}
