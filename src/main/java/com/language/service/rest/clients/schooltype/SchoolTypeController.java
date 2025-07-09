package com.language.service.rest.clients.schooltype;

import com.language.service.service.abs.schooltype.SchoolTypeService;
import com.language.service.domain.dtos.SchoolTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

@RestController
@RequestMapping(Constants.CLIENT_API+ "/schoolType")
public class SchoolTypeController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolTypeController.class);

    private final SchoolTypeService schoolTypeService;
    final ResponseFactory responseFactory;

    public SchoolTypeController(SchoolTypeService schoolTypeService, ResponseFactory responseFactory) {
        this.schoolTypeService = schoolTypeService;
        this.responseFactory = responseFactory;
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        return responseFactory.success(schoolTypeService.getAll());
    }

    @PostMapping(value = "/search")   
    public ResponseEntity<?> search(SchoolTypeDTO request, @PageableDefault Pageable pageable) {
       return responseFactory.success(schoolTypeService.search(request, pageable));
    }
    
    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody @Valid SchoolTypeDTO request) {
        return responseFactory.success(schoolTypeService.saveSchoolType(request));
    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid SchoolTypeDTO request) {
        return responseFactory.success(schoolTypeService.updateSchoolType(request));
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(schoolTypeService.deleteSchoolType(id));
    }
}
