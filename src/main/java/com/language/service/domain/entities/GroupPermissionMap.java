package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="group_permission_map")
public class GroupPermissionMap extends BaseEntity {

   @ManyToOne
   @JoinColumn(name = "PERMISSION_ID")
   private Permission permission;

   @ManyToOne
   @JoinColumn(name = "GROUP_ID")
   private Group group;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_PERMISSION_MAP_SEQ")
   @SequenceGenerator(name = "GROUP_PERMISSION_MAP_SEQ", sequenceName = "GROUP_PERMISSION_MAP_SEQ", allocationSize = 1)
   @Column(name = "ID", nullable = false)
   private Long id;

}
