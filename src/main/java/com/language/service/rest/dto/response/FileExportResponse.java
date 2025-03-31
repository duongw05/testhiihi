package com.language.service.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.ByteArrayOutputStream;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileExportResponse extends BaseResponseDTO{
    private ByteArrayOutputStream byteArrOutput;
}
