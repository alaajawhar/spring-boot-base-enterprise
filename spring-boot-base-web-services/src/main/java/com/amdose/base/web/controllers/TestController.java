package com.amdose.base.web.controllers;

import com.amdose.base.web.payloads.TestRequest;
import com.amdose.base.web.payloads.TestResponse;
import com.amdose.base.web.services.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Alaa Jawhar
 */
@RestController
@RequestMapping("v1")
public class TestController implements ITestController {

    @Autowired
    private TestService testService;

    public TestResponse successTest(TestRequest testRequest) {
        return testService.serve(testRequest);
    }

    public TestResponse failureTest(TestRequest testRequest) {
        throw new RuntimeException("Just for testing");
    }
}
