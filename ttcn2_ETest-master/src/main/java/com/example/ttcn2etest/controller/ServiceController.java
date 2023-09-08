package com.example.ttcn2etest.controller;

import com.example.ttcn2etest.constant.DateTimeConstant;
import com.example.ttcn2etest.model.dto.ServiceDTO;
import com.example.ttcn2etest.model.etity.Service;
import com.example.ttcn2etest.request.service.FilterServiceRequest;
import com.example.ttcn2etest.request.service.ServiceRequest;
import com.example.ttcn2etest.service.serviceStudy.StudyService;
import com.example.ttcn2etest.utils.MyUtils;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service")
public class ServiceController extends BaseController {
    private final StudyService studyService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ServiceController(StudyService studyService) {
        this.studyService = studyService;
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllService() {
        try {
            List<ServiceDTO> response = studyService.getAllService();
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildResponse();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) {
        ServiceDTO response = studyService.getByIdService(id);
        return buildItemResponse(response);
    }

    @PostMapping("")
    ResponseEntity<?> creatService(@Validated @RequestBody ServiceRequest request) {
        ServiceDTO response = studyService.createService(request);
        return buildItemResponse(response);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateService(@Validated @RequestBody ServiceRequest request,
                                    @PathVariable("id") Long id) {
        ServiceDTO response = studyService.updateService(request, id);
        return buildItemResponse(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {
        ServiceDTO response = studyService.deleteByIdService(id);
        return buildItemResponse(response);
    }

    @DeleteMapping("/delete/all")
    ResponseEntity<?> deleteAllId(@RequestBody List<Long> ids) {
        try {
            List<ServiceDTO> response = studyService.deleteAllIdService(ids);
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildResponse();
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@Validated @RequestBody FilterServiceRequest request) throws ParseException {
        BigDecimal minPrice = request.getMinPrice();
        BigDecimal maxPrice = request.getMaxPrice();
        Page<Service> servicePage = studyService.filterService(
                request,
                !Strings.isEmpty(request.getDateFrom()) ? MyUtils.convertDateFromString(request.getDateFrom(), DateTimeConstant.DATE_FORMAT) : null,
                !Strings.isEmpty(request.getDateTo()) ? MyUtils.convertDateFromString(request.getDateTo(), DateTimeConstant.DATE_FORMAT) : null,
                minPrice, maxPrice
        );
        List<ServiceDTO> serviceDTOS = servicePage.getContent().stream().map(
                service -> modelMapper.map(service, ServiceDTO.class)
        ).collect(Collectors.toList());
        return buildListItemResponse(serviceDTOS, servicePage.getTotalElements());
    }

}
