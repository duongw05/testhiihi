package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupPermissionMapDTO {
   private Long id;
   private Long groupId;
   private Long permissionId;
   private String permissionCode;
   private String permissionName;
   private String permissionGroup;
   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   private Integer deleted;
   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   private String deletedBy;
   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy",timezone = "Asia/Saigon" )
   private Date deletedDate;

   public GroupPermissionMapDTO() {
   }

   public GroupPermissionMapDTO( Long permissionId, String permissionCode, String permissionName, String permissionGroup) {
      this.id = id;
      this.groupId = groupId;
      this.permissionId = permissionId;
      this.permissionCode = permissionCode;
      this.permissionName = permissionName;
      this.permissionGroup = permissionGroup;
   }
}
