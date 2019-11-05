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
package org.camunda.bpm.getstarted.createuser;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

/**
 * @author Surabhi.Bhawsar
 *
 */
public class SetupUserWorker {
    
    private final static Logger LOGGER = Logger
            .getLogger(SetupUserWorker.class.getName());

    public final static String camundaServerEngineURL = "http://localhost:8080/engine-rest";

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl(camundaServerEngineURL)
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        subscribeAndExecuteUserWorkflow(client);

        subscribeAndExecutePrintUserWorkflow(client);
        
        SendEmailWorker.subscribeAndExecuteEmailWorkflow(client);

        SendSMSWorker.subscribeAndExecuteSMSWorkflow(client);

        SendEmailAndSMS.subscribeAndExecuteEmailAndSmsWorkflow(client);

    }

    /**
     * @param client
     */
    private static void subscribeAndExecutePrintUserWorkflow(ExternalTaskClient client) {
        client.subscribe("LogUserDetails").lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    // Get a process variable
                    LOGGER.info(
                            "************* PRINTING USER DETAILS ********** ");

                    // Complete the task
                    externalTaskService.complete(externalTask);
                }).open();
    }

    /**
     * @param client
     */
    private static void subscribeAndExecuteUserWorkflow(ExternalTaskClient client) {
        // subscribe to an external task topic as specified in the process
        client.subscribe("createUser").lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here

                    // Get a process variable
                    LOGGER.info(
                            "Create user is invoked with inputs available .... .TODO: No inputs are given currently");

                    String username = externalTask.getVariable("username");

                    LOGGER.info("Create user with username - " + username);
                    // Complete the task
                    externalTaskService.complete(externalTask);
                }).open();
    }
}