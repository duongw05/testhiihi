package com.language.service.rest.integrates;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;

@RestController
@RequestMapping(Constants.INTEGRATE_API + "/test")
@SecurityRequirement(name = "BearerAuth")
public class TestIntegrateController {
    private static final Logger logger = LoggerFactory.getLogger(TestIntegrateController.class);

}
