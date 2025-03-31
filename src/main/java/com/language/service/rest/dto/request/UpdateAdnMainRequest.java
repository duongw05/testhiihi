package com.language.service.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.language.service.common.ConstantsErrorCode;

@Data
public class UpdateAdnMainRequest {

    @NotBlank(message = ConstantsErrorCode.MANAGER_ADN.ERROR_BARCODE_NOT_NULL)
    @Size(min = 1, max = 50, message = ConstantsErrorCode.MANAGER_ADN.ERROR_BARCODE_MAX_LENGTH)
    private String barcode;

    @NotNull(message = ConstantsErrorCode.WORK_LIST.ERROR_WORK_LIST_NOT_FOUND)
    private Long workListId;
}
