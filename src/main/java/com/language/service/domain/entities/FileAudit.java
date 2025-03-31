package com.language.service.domain.entities;

import lombok.Getter;
import lombok.Setter;
import com.language.service.common.ColumnDisplayInfo;
import com.language.service.common.ConstantAuditCode;
import com.language.service.common.TableDisplayInfo;


@Setter
@Getter
@TableDisplayInfo(displayName = "Thông tin upload file")
public class FileAudit {
    @ColumnDisplayInfo(displayName = ConstantAuditCode.FILE.NAME)
    String fileName;
}
