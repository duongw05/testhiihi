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
public class UploadSubjectDataDTO {
   private Long id;
   private String fileName;
   private String imageName;
   private Integer status;
   private Integer deleted;
   private String deletedBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date deletedDate;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date createdDate;
   private String createdBy;
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date lastModifiedDate;
   private String lastModifiedBy;
   private String requestCode;
   private Integer index;
   private String description;
}
