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
@Table(name="user_group_map")
public class UserGroupMap extends BaseEntity {

   @Id
   @Column(name = "id", updatable = false, nullable = false)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GROUP_MAP_SEQ")
   @SequenceGenerator(name = "USER_GROUP_MAP_SEQ", sequenceName = "USER_GROUP_MAP_SEQ", allocationSize = 1)
   private long id;

   @ManyToOne
   @JoinColumn(name = "USER_ID")
   private User user;

   @ManyToOne
   @JoinColumn(name = "GROUP_ID")
   private Group group;

   @Column(name="STATUS")
   private Integer status;

}
