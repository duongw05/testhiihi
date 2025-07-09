package com.language.service.rest.clients.workingoffsite;

import com.language.service.service.abs.workingoffsite.WorkingOffsiteService;
import com.language.service.domain.dtos.WorkingOffsiteDTO;
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
@RequestMapping(Constants.CLIENT_API+ "/workingOffsite")
public class WorkingOffsiteController {
    private static final Logger logger = LoggerFactory.getLogger(WorkingOffsiteController.class);

    private final WorkingOffsiteService workingOffsiteService;
    final ResponseFactory responseFactory;

    public WorkingOffsiteController(WorkingOffsiteService workingOffsiteService, ResponseFactory responseFactory) {
        this.workingOffsiteService = workingOffsiteService;
        this.responseFactory = responseFactory;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<?> getAll() {
        return responseFactory.success(workingOffsiteService.getAll());
    }


    @PostMapping(value = "/search")   
    public ResponseEntity<?> search(WorkingOffsiteDTO request, @PageableDefault Pageable pageable) {
       return responseFactory.success(workingOffsiteService.search(request, pageable));
    }
    
    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody @Valid WorkingOffsiteDTO request) {
        return responseFactory.success(workingOffsiteService.saveWorkingOffsite(request));
    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid WorkingOffsiteDTO request) {
        return responseFactory.success(workingOffsiteService.updateWorkingOffsite(request));
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(workingOffsiteService.deleteWorkingOffsite(id));
    }
}
