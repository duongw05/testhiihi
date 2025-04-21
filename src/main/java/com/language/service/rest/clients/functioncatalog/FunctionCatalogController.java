package com.language.service.rest.clients.functioncatalog;

import com.language.service.service.abs.functioncatalog.FunctionCatalogService;
import com.language.service.domain.dtos.FunctionCatalogDTO;
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
@RequestMapping(Constants.CLIENT_API+ "/functionCatalog")
public class FunctionCatalogController {
    private static final Logger logger = LoggerFactory.getLogger(FunctionCatalogController.class);

    private final FunctionCatalogService functionCatalogService;
    final ResponseFactory responseFactory;

    public FunctionCatalogController(FunctionCatalogService functionCatalogService, ResponseFactory responseFactory) {
        this.functionCatalogService = functionCatalogService;
        this.responseFactory = responseFactory;
    }

    @PostMapping(value = "/search")   
    public ResponseEntity<?> search(@RequestBody FunctionCatalogDTO request, @PageableDefault Pageable pageable) {
       return responseFactory.success(functionCatalogService.search(request, pageable));
    }
    
    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody @Valid FunctionCatalogDTO request) {
        return responseFactory.success(functionCatalogService.saveFunctionCatalog(request));
    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid FunctionCatalogDTO request) {
        return responseFactory.success(functionCatalogService.updateFunctionCatalog(request));
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(functionCatalogService.deleteFunctionCatalog(id));
    }
    @GetMapping(value = "/getFunctionTree")
    public ResponseEntity<?> getFunctionCatalogTree() {
        return responseFactory.success(functionCatalogService.getFunctionCatalogTree());
    }

}
