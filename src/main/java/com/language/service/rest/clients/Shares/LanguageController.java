package com.language.service.rest.clients.Shares;

import com.language.service.common.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.language.service.domain.dtos.LanguageDTO;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(Constants.CLIENT_API)
public class LanguageController {

    @GetMapping("/supportedLanguages")
    public List<LanguageDTO> getSupportedLanguages() {
        return Arrays.asList(
                new LanguageDTO("vi", "Tiếng Việt"),
                new LanguageDTO("en_US", "English")
        );
    }
}
