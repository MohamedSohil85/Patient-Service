package com.patient.patientservice.mapper;

import com.patient.patientservice.dto.PatientDTO;
import com.patient.patientservice.entity.Patient;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class PatientMapper implements EntityMapper<PatientDTO, Patient> {

    @Override
    public Patient toEntity(PatientDTO dto) {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(dto,Patient.class);
    }

    @Override
    public  PatientDTO toDto(Patient entity) {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(entity,PatientDTO.class);
    }

    @Override
    public List<Patient> toEntity(List<PatientDTO> dtoList) {
        List<Patient>patients=new ArrayList<>();
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        for (PatientDTO patientDTO:dtoList){
            patients.add(modelMapper.map(patientDTO,Patient.class));
        }
        return patients;
    }

    @Override
    public List<PatientDTO> toDto(List<Patient> entityList) {
        List<PatientDTO>patientDTOList=new ArrayList<>();
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        for (Patient patient:entityList){
            patientDTOList.add(modelMapper.map(patient,PatientDTO.class));
        }
        return patientDTOList;
    }

}
