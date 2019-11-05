/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.demo.workers;

import java.io.IOException;
import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.demo.util.EmailUtility;

public class SendEmailWorker {
    
    private final static Logger LOGGER = Logger
            .getLogger(SendEmailWorker.class.getName());

    /**
     * @param client
     */
    public static void subscribeAndExecuteEmailWorkflow(ExternalTaskClient client) {
        // subscribe to an external task topic as specified in the process
        client.subscribe("sendEmail").lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    String email = externalTask.getVariable("email");
                    
                    try {
                        EmailUtility.sendEmail(email);
                        // Get a process variable
                        LOGGER.info(
                                "*************** EMAIL SENT ***************** ");
                    } catch (IOException e) {
                        LOGGER.info("Failed to send email due to exception" + e.getMessage());
                    }
                    
                    // Complete the task
                    externalTaskService.complete(externalTask);
                }).open();
    }

    private static void sendEmail() {
        // TODO Auto-generated method stub
        
    }
}