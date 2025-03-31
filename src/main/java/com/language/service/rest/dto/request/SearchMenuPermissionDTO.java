package com.language.service.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchMenuPermissionDTO {
    private String menuCode;
    private String menuName;
    private String permissionCode;
    private String permissionName;
}
