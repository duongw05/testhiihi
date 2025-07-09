package com.language.service.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchSchoolDTO {
    private Long schoolTypeId;
    private String name;
    private String code;
    private String workingOffsiteName;
    private String areaCvct;
    private String programs;
    private Long rhta;
    private Long scheduler;
    private Long schedulerCumTa;
    private Integer status;
}
