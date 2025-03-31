package com.language.service.rest.clients.Shares;

import com.language.service.common.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(Constants.CLIENT_API)
public class TimezoneController {

    @GetMapping("availableTimezones")
    public List<String> getAvailableTimezones() {
        String[] zoneArray = TimeZone.getAvailableIDs();
        List<String> list = Arrays.asList(zoneArray);
        list.sort(Comparator.naturalOrder());
        return list;
    }
}
