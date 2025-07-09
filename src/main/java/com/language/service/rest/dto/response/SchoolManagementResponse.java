package com.language.service.rest.dto.response;

import com.language.service.domain.entities.SchoolType;
import com.language.service.domain.entities.WorkingOffsite;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolManagementResponse {
    private Long id;
    private String code;
    private String name;
    private Integer status;
    private Long workingOffsite;
    private Long schoolType;
    private Integer isPremium;
    private String map;
    private String latitude;
    private String longtitude;
    private String area;
    private String address;
    private String programs;
    private String areaCvct;
    private Double minuteNum;
    private Long rhta;
    private Long scheduler;
    private Long schedulerCumTa;
    private Date startYear;
    private String schoolDiagram;

    private String description;
}
