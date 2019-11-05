package org.camunda.bpm.demo.workers;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

/**
 * @author Surabhi.Bhawsar
 *
 */
public class SendSMSWorker {

    private final static Logger LOGGER = Logger
            .getLogger(SendSMSWorker.class.getName());

    /**
     * 
     */
    public static void subscribeAndExecuteSMSWorkflow(ExternalTaskClient client) {
        // subscribe to an external task topic as specified in the process
        client.subscribe("sendSMS").lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    // Get a process variable
                    LOGGER.info("*************** SMS SENT ***************** ");
                    // Complete the task
                    externalTaskService.complete(externalTask);
                }).open();
    }
}
