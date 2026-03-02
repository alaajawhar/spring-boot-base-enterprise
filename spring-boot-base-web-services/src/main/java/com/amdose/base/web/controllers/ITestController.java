package com.amdose.base.web.controllers;

import com.amdose.base.web.constants.UriConstants;
import com.amdose.base.web.payloads.TestRequest;
import com.amdose.base.web.payloads.TestResponse;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Alaa Jawhar
 */
public interface ITestController {

    @PostMapping(UriConstants.SUCCESS_TEST)
    @Cacheable(value = "test", keyGenerator = "localizedGenerator")
    TestResponse successTest(@RequestBody @Valid TestRequest testRequest);

    @PostMapping(UriConstants.FAILURE_TEST)
    TestResponse failureTest(@RequestBody @Valid TestRequest testRequest);
}
