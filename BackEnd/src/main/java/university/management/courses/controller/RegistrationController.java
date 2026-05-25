package university.management.courses.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import university.management.courses.dto.RegisterRequest;
import university.management.courses.dto.RegistrationDto;
import university.management.courses.service.RegistrationService;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/test")
    public String test() {
        return "Registration controller is working";
    }

    @PostMapping
    public ResponseEntity<RegistrationDto> register(@RequestBody RegisterRequest request) {
        RegistrationDto registration = registrationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @GetMapping("/{regNumber}")
    public RegistrationDto getRegistration(
            @PathVariable String regNumber,
            @RequestParam int semester
    ) {
        return registrationService.getRegistration(regNumber, semester);
    }

    @DeleteMapping("/{regNumber}")
    public ResponseEntity<Void> deleteRegistration(
            @PathVariable String regNumber,
            @RequestParam int semester
    ) {
        registrationService.deleteRegistration(regNumber, semester);
        return ResponseEntity.noContent().build();
    }
}