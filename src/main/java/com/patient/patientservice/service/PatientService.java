package com.patient.patientservice.service;

import com.patient.patientservice.dto.PatientDTO;
import com.patient.patientservice.entity.Address;
import com.patient.patientservice.entity.Patient;
import com.patient.patientservice.exception.ResourceNotFound;
import com.patient.patientservice.mapper.PatientMapper;
import com.patient.patientservice.persistence.AddressRepository;
import com.patient.patientservice.persistence.PatientRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final AddressRepository addressRepository;
    PatientMapper patientMapper=new PatientMapper();

    public PatientService(PatientRepository patientRepository, AddressRepository addressRepository) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;

    }
    public ResponseEntity saveNewPatient(PatientDTO patientDTO){

        var patients = patientRepository.findAll();
        var patientDTOS = patientMapper.toDto(patients);

        var existingPatient = patientDTOS.stream()
                .filter(dto -> dto.getLastName().equalsIgnoreCase(patientDTO.getLastName())
                        && dto.getFirstName().equalsIgnoreCase(patientDTO.getFirstName()))
                .findFirst();

        if (existingPatient.isPresent()) {
            return new ResponseEntity<>(existingPatient.get(), HttpStatus.FOUND);
        }

        var address = new Address();
        address.setCountry(patientDTO.getCountry());
        address.setStreet(patientDTO.getStreet());
        address.setCity(patientDTO.getCity());
        address.setZipcode(patientDTO.getZipcode());
        address.setState(patientDTO.getState());
        patientDTO.setDateOfRegistration(LocalDateTime.now());

        addressRepository.save(address);
        var patient = patientMapper.toEntity(patientDTO);
        patient.setAddress(address);
        return new ResponseEntity<>(patientRepository.save(patient), HttpStatus.CREATED);
    }

    public List<PatientDTO>getAllPatients(){
        List<Patient> patients= new ArrayList<>(patientRepository.findAll(Sort.by("lastName")));
        return patientMapper.toDto(patients);

    }
    public ResponseEntity findPatientByNameAndBirthday(String lastName, String firstName, LocalDate date){
        Optional<Patient> optionalPatient=patientRepository.findPatientByLastNameAndFirstNameAndDateOfBirth(lastName, firstName, date);
        if (!optionalPatient.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Patient patient=optionalPatient.get();
        return new ResponseEntity(patientMapper.toDto(patient), HttpStatus.OK);
    }
    //change Infos of patient

    public PatientDTO changePatientInfo(PatientDTO patientDTO, Long id) throws ResourceNotFound {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setEmail(patientDTO.getEmail());
                    patient.setNationality(patientDTO.getNationality());
                    var address = patient.getAddress();
                    address.setCity(patientDTO.getCity());
                    address.setZipcode(patientDTO.getZipcode());
                    address.setCountry(patientDTO.getCountry());
                    address.setState(patientDTO.getState());
                    address.setStreet(patientDTO.getStreet());
                    patient.setPhoneNumber(patientDTO.getPhoneNumber());
                    return patientMapper.toDto(patientRepository.save(patient));
                })
                .orElseThrow(() -> new ResourceNotFound("Patient could not found"));
    }
}
