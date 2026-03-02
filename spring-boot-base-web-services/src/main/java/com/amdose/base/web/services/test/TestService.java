package com.amdose.base.web.services.test;

import com.amdose.base.web.payloads.TestRequest;
import com.amdose.base.web.payloads.TestResponse;
import com.amdose.base.web.services.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Alaa Jawhar
 */
@Slf4j
@Service
public class TestService extends BaseService<TestRequest, TestResponse> {

    @Override
    public void validate(TestRequest request) {
        request.getInternal().setTest("ads");
    }

    @Override
    public TestResponse execute(TestRequest request) {
        log.debug("executing...");
        TestResponse testResponse = new TestResponse();
        testResponse.setS("SResult");
        return testResponse;
    }
}
