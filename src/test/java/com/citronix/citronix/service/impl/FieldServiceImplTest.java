package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.exception.Farm.FarmNotFoundException;
import com.citronix.citronix.exception.Field.FieldNotFoundException;
import com.citronix.citronix.repository.FarmRepository;
import com.citronix.citronix.repository.FieldRepository;
import com.citronix.citronix.web.vm.Field.AddFieldVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FieldServiceImplTest {
    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private Farm farm;
    private Field field;
    private AddFieldVM addFieldVM;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setArea(100.0);

        field = new Field();
        field.setId(UUID.randomUUID());
        field.setArea(10.0);
        field.setFarm(farm);

        addFieldVM = new AddFieldVM();
        addFieldVM.setFarm_id(farm.getId());
        addFieldVM.setArea(10.0);
    }

    @Test
    public void testAddField_Success() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        Field result = fieldService.addField(addFieldVM);

        assertNotNull(result);
        assertEquals(field.getArea(), result.getArea());
        assertEquals(farm.getId(), result.getFarm().getId());

        verify(farmRepository).findById(farm.getId());
        verify(fieldRepository).save(any(Field.class));
    }

    @Test
    public void testAddField_FarmNotFound() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.empty());

        FarmNotFoundException exception = assertThrows(FarmNotFoundException.class, () -> {
            fieldService.addField(addFieldVM);
        });

        assertEquals("Farm not found", exception.getMessage());
    }

    @Test
    public void testUpdateField_Success() {
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.of(field));
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        field.setArea(15.0);
        Field updatedField = fieldService.updateField(field);

        assertEquals(15.0, updatedField.getArea());
        verify(fieldRepository).findById(field.getId());
        verify(fieldRepository).save(any(Field.class));
    }

    @Test
    public void testUpdateField_FieldNotFound() {
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.empty());

        FieldNotFoundException exception = assertThrows(FieldNotFoundException.class, () -> {
            fieldService.updateField(field);
        });

        assertEquals("Field not found", exception.getMessage());
    }

    @Test
    public void testDeleteField_Success() {
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.of(field));

        fieldService.deleteField(field.getId());

        verify(fieldRepository).findById(field.getId());
        verify(fieldRepository).delete(field);
    }

    @Test
    public void testDeleteField_FieldNotFound() {
        when(fieldRepository.findById(field.getId())).thenReturn(Optional.empty());

        FieldNotFoundException exception = assertThrows(FieldNotFoundException.class, () -> {
            fieldService.deleteField(field.getId());
        });

        assertEquals("Field not found", exception.getMessage());
    }

    @Test
    public void testSaveField_Success() {
        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        Field result = fieldService.saveField(field);

        assertNotNull(result);
        assertEquals(field.getArea(), result.getArea());
        assertEquals(farm.getId(), result.getFarm().getId());
        verify(farmRepository).findById(farm.getId());
        verify(fieldRepository).save(any(Field.class));
    }

    @Test
    public void testValidateField_AreaTooSmall() {
        field.setArea(0.05);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fieldService.saveField(field);
        });

        assertEquals("Field area must be at least 0.1 hectares (1,000 m²)", exception.getMessage());
    }

    @Test
    public void testValidateField_AreaTooLarge() {
        field.setArea(110.0); // larger than the farm's area

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fieldService.saveField(field);
        });

        assertEquals("Total field area must be less than the farm's total area", exception.getMessage());
    }

    @Test
    public void testValidateField_TooManyFields() {
        farm.setFieldList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            farm.getFieldList().add(new Field());
        }

        field.setArea(5.0);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fieldService.saveField(field);
        });

        assertEquals("A farm cannot have more than 10 fields", exception.getMessage());
    }
}