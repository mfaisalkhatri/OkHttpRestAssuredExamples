/*
        Copyright (c) 2022 Mohammad Faisal Khatri

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.faisalkhatri.okhttppoc;

import static org.apache.commons.lang3.StringUtils.repeat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * @since Mar 8, 2020
 */
public class TestListener implements ITestListener {

    Logger log = LogManager.getLogger (TestListener.class);

    private void logMessage (final String message) {
        this.log.info ("\n");
        this.log.info (repeat ("=", 75));
        this.log.info (message);
        this.log.info (repeat ("=", 75));
        this.log.info ("\n");
    }

    @Override
    public void onTestStart (final ITestResult result) {
        logMessage ("Test Execution Started...." + result.getName ());
    }

    @Override
    public void onTestSuccess (final ITestResult result) {
        logMessage ("Test Passed Successfully." + result.getName ());

    }

    @Override
    public void onTestFailure (final ITestResult result) {
        logMessage ("Test Failed!!!!" + result.getName ());
    }

    @Override
    public void onFinish (final ITestContext context) {
        logMessage ("Test Execution Completed Successfully for all tests!!" + context.getSuite ()
            .getAllMethods ());

    }

}