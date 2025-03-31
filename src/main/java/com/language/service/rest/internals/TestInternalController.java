package com.language.service.rest.internals;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.language.service.common.Constants;

@RestController
@RequestMapping(Constants.INTERNAL_API + "/testinternal")
@SecurityRequirement(name = "BearerAuth")
public class TestInternalController {
    private static final Logger logger = LoggerFactory.getLogger(TestInternalController.class);

}
