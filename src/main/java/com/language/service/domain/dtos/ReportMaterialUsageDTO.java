package com.language.service.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportMaterialUsageDTO {
    private String stt;
    private Long parentId;
    private String lstMaterial;
    private String lstChemical;
    private String valueLable;
    private Integer countMaterial;
    private Integer countChemical;
    private String lstIdParenMaterial;
    private String lstIParenChemical;

    public ReportMaterialUsageDTO(Long parentId, String lstMaterial, String lstChemical) {
        this.parentId = parentId;
        this.lstMaterial = lstMaterial;
        this.lstChemical = lstChemical;
    }
}
