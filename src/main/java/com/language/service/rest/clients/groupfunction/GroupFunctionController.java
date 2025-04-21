package com.language.service.rest.clients.groupfunction;

import com.language.service.service.abs.groupfunction.GroupFunctionService;
import com.language.service.domain.dtos.GroupFunctionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(Constants.CLIENT_API+ "/groupFunction")
public class GroupFunctionController {
    private static final Logger logger = LoggerFactory.getLogger(GroupFunctionController.class);

    private final GroupFunctionService groupFunctionService;
    final ResponseFactory responseFactory;

    public GroupFunctionController(GroupFunctionService groupFunctionService, ResponseFactory responseFactory) {
        this.groupFunctionService = groupFunctionService;
        this.responseFactory = responseFactory;
    }

    @PostMapping(value = "/search")   
    public ResponseEntity<?> search(GroupFunctionDTO request, @PageableDefault Pageable pageable) {
       return responseFactory.success(groupFunctionService.search(request, pageable));
    }
    
    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody @Valid GroupFunctionDTO request) {
        return responseFactory.success(groupFunctionService.addGroupFunction(request));
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid GroupFunctionDTO request) {
        return responseFactory.success(groupFunctionService.updateGroupFunction(request));
    }
    @PostMapping(value = "/saveGroupFunction")
    public ResponseEntity<?> saveGroupFunction(@RequestBody @Valid GroupFunctionDTO request) {
        return responseFactory.success(groupFunctionService.saveGroupFunction(request));
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(groupFunctionService.deleteGroupFunction(id));
    }
    @PostMapping("/getGroupFunctions")
    public ResponseEntity<List<GroupFunctionDTO>> getGroupFunctions(@RequestBody GroupFunctionDTO request) {
        List<GroupFunctionDTO> groupFunctionData = groupFunctionService.getGroupFunctionData(request);
        return ResponseEntity.ok(groupFunctionData);
    }
}
