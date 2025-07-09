package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolDTO {
   private Long id;
   private String code;
   private String name;
   private Integer status;
   private Long workingOffsiteId;
   private String workingOffsiteName;
   private Long schoolTypeId;
   private String schoolTypeName;
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
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date startYear;
   private String schoolDiagram;
   private String createdBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date createdDate;
   private String lastModifiedBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date lastModifiedDate;
   private Integer deleted;
   private String deletedBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date deletedDate;
   private String description;
}
