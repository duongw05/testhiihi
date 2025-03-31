package com.language.service.rest.clients.UserManagement;


import com.language.service.security.LoginRequest;
import com.language.service.common.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    /**
     * Việc login thực sự nằm ở {@link com.language.service.security.LoginFilter}
     * Phương thức này ở đây chỉ nhằm mục đích sinh tài liệu swagger
     */
    @PostMapping(Constants.LOGIN_URL)
    public void login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Username and password")
            @RequestBody LoginRequest loginRequest) {

    }
}
