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
@Table(name="group_menu_map")
public class GroupMenuMap extends BaseEntity {

   @ManyToOne
   @JoinColumn(name = "MENU_ID")
   private Menu menu;

   @ManyToOne
   @JoinColumn(name = "GROUP_ID")
   private Group group;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_MENU_MAP_SEQ")
   @SequenceGenerator(name = "GROUP_MENU_MAP_SEQ", sequenceName = "GROUP_MENU_MAP_SEQ", allocationSize = 1)
   @Column(name = "ID", nullable = false)
   private Long id;

}
