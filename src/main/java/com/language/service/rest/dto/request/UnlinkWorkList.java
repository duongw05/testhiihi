package com.language.service.rest.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnlinkWorkList {
    private long parentId;
    private long subjectId;
}
