package com.language.service.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolManagementRequest {
    private String code;
    private String name;
    private Long workingOffsite;
    private Long schoolType;
    private Integer isPremium;
    private String programs;
    private String map;
    private String area;
    private String address;
    private String areaCvct;
    private Double minuteNum;
    private Long rhta;
    private Long scheduler;
    private Long schedulerCumTa;
    private Date startYear;
    private String schoolDiagram;
    private String description;
}
