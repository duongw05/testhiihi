package com.language.service.rest.clients.UserManagement;


import com.language.service.rest.dto.response.SuccessResponse;
import com.language.service.domain.dtos.UserMinDTO;
import com.language.service.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


/**
 * Looking for login? It is in LoginFilter
 */
@RestController
@RequestMapping(Constants.CLIENT_API)
public class AuthController {

    private MessageSource messageSource;



    @PostMapping("/logout")
    public SuccessResponse logout() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;
            UserMinDTO userMinDTO = (UserMinDTO) token.getPrincipal();
            String msg = messageSource.getMessage("loggedOutSuccessfully", null, LocaleContextHolder.getLocale());
            return new SuccessResponse(msg);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid authentication found");
        }
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


}
