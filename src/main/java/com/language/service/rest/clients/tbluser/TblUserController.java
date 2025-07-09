package com.language.service.rest.clients.tbluser;

import com.language.service.service.abs.tbluser.TblUserService;
import com.language.service.domain.dtos.TblUserDTO;
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
@RequestMapping(Constants.CLIENT_API+ "/tblUser")
public class TblUserController {
    private static final Logger logger = LoggerFactory.getLogger(TblUserController.class);

    private final TblUserService tblUserService;
    final ResponseFactory responseFactory;

    public TblUserController(TblUserService tblUserService, ResponseFactory responseFactory) {
        this.tblUserService = tblUserService;
        this.responseFactory = responseFactory;
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<?> getAll() {
        return responseFactory.success(tblUserService.getAll());
    }

    @PostMapping(value = "/search")   
    public ResponseEntity<?> search(TblUserDTO request, @PageableDefault Pageable pageable) {
       return responseFactory.success(tblUserService.search(request, pageable));
    }
    
    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody @Valid TblUserDTO request) {
        return responseFactory.success(tblUserService.saveTblUser(request));
    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid TblUserDTO request) {
        return responseFactory.success(tblUserService.updateTblUser(request));
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(tblUserService.deleteTblUser(id));
    }
}
