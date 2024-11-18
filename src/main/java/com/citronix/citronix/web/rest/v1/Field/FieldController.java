package com.citronix.citronix.web.rest.v1.Field;

import com.citronix.citronix.domain.Field;
import com.citronix.citronix.service.impl.FieldServiceImpl;
import com.citronix.citronix.web.vm.Field.AddFieldVM;
import com.citronix.citronix.web.vm.Field.FieldResponseVM;
import com.citronix.citronix.web.vm.Field.UpdateFieldVM;
import com.citronix.citronix.web.vm.Mapper.Field.FieldMapper;
import com.citronix.citronix.web.vm.Mapper.Field.UpdateFieldMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldServiceImpl fieldServiceImpl;
    private final FieldMapper fieldMapper;
    private final UpdateFieldMapper updateFieldMapper;

    public FieldController(FieldServiceImpl fieldServiceImpl, FieldMapper fieldMapper,UpdateFieldMapper updateFieldMapper) {
        this.fieldServiceImpl = fieldServiceImpl;
        this.fieldMapper = fieldMapper;
        this.updateFieldMapper = updateFieldMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<FieldResponseVM> save(@RequestBody @Valid AddFieldVM addFieldVM) {
        Field savedField = fieldServiceImpl.addField(addFieldVM);
        FieldResponseVM response = fieldMapper.toResponseFieldVM(savedField);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{fieldUuid}")
    public ResponseEntity<FieldResponseVM> update(@PathVariable UUID fieldUuid,
                                                  @RequestBody @Valid UpdateFieldVM updateFieldVM) {
        Field field = updateFieldMapper.toField(updateFieldVM);
        field.setId(fieldUuid);
        Field updatedField = fieldServiceImpl.updateField(field);
        FieldResponseVM fieldResponseVM = fieldMapper.toResponseFieldVM(updatedField);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.OK);
    }

    @GetMapping("/find/{fieldUuid}")
    public ResponseEntity<FieldResponseVM> findById(@PathVariable UUID fieldUuid) {
        Field field = fieldServiceImpl.findById(fieldUuid);
        FieldResponseVM fieldResponseVM = fieldMapper.toResponseFieldVM(field);
        return new ResponseEntity<>(fieldResponseVM, HttpStatus.OK);
    }

    @GetMapping("/all/{farmUuid}")
    public ResponseEntity<Page<FieldResponseVM>> findAllByFarm(@PathVariable UUID farmUuid, Pageable pageable) {
        Page<Field> fields = fieldServiceImpl.findAllByFarm(farmUuid, pageable);
        Page<FieldResponseVM> fieldResponseVMS = fields.map(fieldMapper::toResponseFieldVM);
        return new ResponseEntity<>(fieldResponseVMS, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{fieldUuid}")
    public ResponseEntity<String> delete(@PathVariable UUID fieldUuid) {
        fieldServiceImpl.deleteField(fieldUuid);
        return new ResponseEntity<>("Field deleted successfully", HttpStatus.OK);
    }
}
