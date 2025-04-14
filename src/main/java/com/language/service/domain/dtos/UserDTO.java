package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.language.service.common.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static com.language.service.common.utils.DateUtil.DDMMYYYY;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private String fullName;

    private Date dob;

    private Integer gender;

    private String avatar;

    private String locale;

    private String timezone;

    private Boolean enabled;

    private String stt;

    public String getDobStr() {
        return DateUtil.dateToString(dob, DDMMYYYY);
    }

    public String getGenderStr() {
        return gender == 0 ? "Nam" : gender == 1 ? "Nữ" : "Khác";
    }
}
