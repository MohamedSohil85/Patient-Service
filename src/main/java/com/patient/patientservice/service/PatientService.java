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

        List<Patient> patients=patientRepository.findAll();
        List<PatientDTO>patientDTOS=patientMapper.toDto(patients);
        for (PatientDTO patientDTO_:patientDTOS){
            if (patientDTO.getLastName().equalsIgnoreCase(patientDTO_.getLastName()))
                if (patientDTO.getFirstName().equalsIgnoreCase(patientDTO_.getFirstName())){
                    return new ResponseEntity<>(patientDTO_, HttpStatus.FOUND);
                }
        }


        Address address=new Address();
        address.setCountry(patientDTO.getCountry());
        address.setStreet(patientDTO.getStreet());
        address.setCity(patientDTO.getCity());
        address.setZipcode(patientDTO.getZipcode());
        address.setState(patientDTO.getState());
        patientDTO.setDateOfRegistration(LocalDateTime.now());


        addressRepository.save(address);
        Patient patient=patientMapper.toEntity(patientDTO);
        patient.setAddress(address);
        return new ResponseEntity(patientRepository.save(patient),HttpStatus.CREATED);
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

    public PatientDTO changePatientInfo(PatientDTO patientDTO, Long id)throws ResourceNotFound {
        Optional<Patient> optionalPatient=patientRepository.findById(id);
        Patient patient_=optionalPatient.map(patient -> {
            patient.setEmail(patientDTO.getEmail());
            patient.setNationality(patientDTO.getNationality());
            patient.getAddress().setCity(patientDTO.getCity());
            patient.getAddress().setZipcode(patientDTO.getZipcode());
            patient.getAddress().setCountry(patientDTO.getCountry());
            patient.getAddress().setState(patientDTO.getState());
            patient.getAddress().setStreet(patientDTO.getStreet());
            patient.setPhoneNumber(patientDTO.getPhoneNumber());
            return patientRepository.save(patient);
        }).orElseThrow(()->new ResourceNotFound("Patient could not found"));
        return patientMapper.toDto(patient_);
    }
}
