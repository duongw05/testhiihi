package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
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
public class ActionAuditDTO {
   private Long id;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date actionDatetime;
   private String actionCode;
   private String tableName;
   private Long objectId;
   private Integer status;
   private String createdBy;
   private String lastModifiedBy;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
   private Date createdDate;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date lastModifiedDate;
   private Integer deleted;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date deletedDate;
   private String deletedBy;
   private String description;
   private Long objectParentId;
   private List<ActionDetailDTO> actionDetailDTOS;

}
