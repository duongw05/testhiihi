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
public class GroupMenuMapDTO {
   private Long groupId;
   private Long menuId;
   private Integer deleted;
   private String deletedBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date deletedDate;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date createdDate;
   private String createdBy;
   private String lastModifiedBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date lastModifiedDate;
   private Long id;
}
