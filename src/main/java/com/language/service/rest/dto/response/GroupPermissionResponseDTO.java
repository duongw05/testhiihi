package com.language.service.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.language.service.common.utils.DataUtils;

@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class GroupPermissionResponseDTO {
    private Long permissionId;
    private String permissionCode;
    private String permissionName;
    private String permissionGroup;
    private Long groupId;
    public boolean getSwitch() {
        return !DataUtils.isNullOrZero(groupId);
    }

}
