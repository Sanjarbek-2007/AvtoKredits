package uz.yusa.avtokredits.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yusa.avtokredits.domain.Application;
import uz.yusa.avtokredits.dto.ApplicationSaveDto;
import uz.yusa.avtokredits.dto.GetApplicationDto;
import uz.yusa.avtokredits.service.ApplicationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apps")
public class ApplicationController {
    private final ApplicationService applicationService;



    @PostMapping("/add")
    public ResponseEntity<Application> saveApplication(@RequestBody ApplicationSaveDto application) {
        try {
            Application savedApplication = applicationService.saveApplication(application);
            return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetApplicationDto> getApplication(@PathVariable Long id) {

        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }
    @PutMapping("/{id}/close")
    public Boolean closeApplication(@PathVariable Long id) {

        applicationService.closeApplicationById(id);
        return true;
    }
    @PutMapping("/{id}/accept")
    public  Boolean acceptApplication(@PathVariable Long id) {

        applicationService.acceptApp(id);
        return true;
    }
    @GetMapping
    public ResponseEntity<List<GetApplicationDto>> getApplicationAdmins() {

        return ResponseEntity.ok(applicationService.getAllApplications());
    }


}
