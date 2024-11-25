package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Field;
import com.citronix.citronix.repository.FarmRepository;
import com.citronix.citronix.repository.FieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

    private Field field;
    private Farm farm;

    @BeforeEach
    void setUp() {
        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setArea(50.);
        farm.setFieldList(new ArrayList<>());

        field = new Field();
        field.setId(UUID.randomUUID());
        field.setArea(20.);
        field.setFarm(farm);
        field.setTreeList(new ArrayList<>());
    }

    @Test
    void testSave_Success() {
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        Field savedfield = fieldService.saveField(field);

        assertNotNull(savedfield);
        assertEquals(farm, savedfield.getFarm());
        assertTrue(farm.getFieldList().contains(savedfield));
        verify(fieldRepository).save(field);
    }

    @Test
    void testSave_Nullfarm() {
        assertThrows(IllegalArgumentException.class, () -> {
            fieldService.saveField(field);
        });
    }

    @Test
    void testGetfieldId_Success() {
        when(fieldRepository.findById(1)).thenReturn(Optional.of(field));

        field foundfield = fieldService.getfieldId(1);

        assertNotNull(foundfield);
        assertEquals(1, foundfield.getId());
        verify(fieldRepository).findById(1);
    }

    @Test
    void testGetfieldId_NotFound() {
        when(fieldRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            fieldService.getfieldId(999);
        });
    }

    @Test
    void testDelete_Success() {
        fieldService.delete(field);

        verify(arbreService).deleteByfield(field);
        verify(fieldRepository).delete(field);
    }

    @Test
    void testDelete_Nullfield() {
        assertThrows(IllegalArgumentException.class, () -> {
            fieldService.delete(null);
        });
    }

    @Test
    void testUpdate_Success() {
        field newfield = new field();
        newfield.setSuperficie(30.0f);

        when(fieldRepository.findById(1)).thenReturn(Optional.of(field));
        when(fieldRepository.save(any(field.class))).thenReturn(field);

        field updatedfield = fieldService.update(newfield, 1);

        assertNotNull(updatedfield);
        assertEquals(30.0f, updatedfield.getSuperficie());
        verify(fieldRepository).save(field);
    }

    @Test
    void testUpdate_SuperficieDepasseMoitie() {
        field newfield = new field();
        newfield.setSuperficie(60.0f);

        when(fieldRepository.findById(1)).thenReturn(Optional.of(field));

        assertThrows(RuntimeException.class, () -> {
            fieldService.update(newfield, 1);
        });
    }

    @Test
    void testCalculerSommeSuperficiesfields() {
        List<field> fields = new ArrayList<>();
        field field1 = new field();
        field1.setSuperficie(20.0f);
        field field2 = new field();
        field2.setSuperficie(30.0f);
        fields.add(field1);
        fields.add(field2);
        farm.setfields(fields);

        Float somme = fieldService.calculerSommeSuperficiesfields(farm);

        assertEquals(50.0f, somme);
    }

    @Test
    void testCalculerSommeSuperficiesfields_Nullfarm() {
        Float somme = fieldService.calculerSommeSuperficiesfields(null);

        assertEquals(0f, somme);
    }
}