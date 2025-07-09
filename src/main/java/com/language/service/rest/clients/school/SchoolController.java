package com.language.service.rest.clients.school;

import com.language.service.domain.dtos.SearchSchoolDTO;
import com.language.service.service.abs.school.SchoolService;
import com.language.service.domain.dtos.SchoolDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import com.language.service.common.Constants;
import com.language.service.common.ResponseFactory;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping(Constants.CLIENT_API+ "/school")
public class SchoolController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    private final SchoolService schoolService;
    final ResponseFactory responseFactory;

    public SchoolController(SchoolService schoolService, ResponseFactory responseFactory) {
        this.schoolService = schoolService;
        this.responseFactory = responseFactory;
    }

    @GetMapping("/search") // Thay đổi từ @PostMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String workingOffsiteName,
            @RequestParam(required = false) String areaCvct,
            @RequestParam(required = false) String programs,
            @RequestParam(required = false) Long rhta,
            @RequestParam(required = false) Long scheduler,
            @RequestParam(required = false) Long schedulerCumTa,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long schoolTypeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        System.out.println("page: " + page);
        System.out.println("size: " + size);

        Pageable pageable = PageRequest.of(page, size);

        SearchSchoolDTO searchDto = new SearchSchoolDTO();
        searchDto.setName(name);
        searchDto.setCode(code);
        searchDto.setWorkingOffsiteName(workingOffsiteName);
        searchDto.setAreaCvct(areaCvct);
        searchDto.setPrograms(programs);
        searchDto.setRhta(rhta);
        searchDto.setScheduler(scheduler);
        searchDto.setSchedulerCumTa(schedulerCumTa);
        searchDto.setStatus(status);
        searchDto.setSchoolTypeId(schoolTypeId);
        return responseFactory.success(schoolService.search(searchDto, pageable));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable Long id) {
        SchoolDTO dto = schoolService.detail(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody @Valid SchoolDTO request) {
        return responseFactory.success(schoolService.saveSchool(request));
    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody @Valid SchoolDTO request) {
        return responseFactory.success(schoolService.updateSchool(request));
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return responseFactory.success(schoolService.deleteSchool(id));
    }

    @GetMapping("/export-excel")
    public void exportExcel(@ModelAttribute SearchSchoolDTO dto, HttpServletResponse response) throws IOException {
        schoolService.xuatExcel(dto, response);
    }

}
