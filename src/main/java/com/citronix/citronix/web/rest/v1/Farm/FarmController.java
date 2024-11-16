package com.citronix.citronix.web.rest.v1.Farm;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.service.impl.FarmServiceImpl;
import com.citronix.citronix.web.vm.Farm.ResponseFarmVM;
import com.citronix.citronix.web.vm.Farm.addFarmVM;
import com.citronix.citronix.web.vm.Mapper.Farm.FarmMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/farms")
public class FarmController {
    private final FarmServiceImpl farmServiceImpl;
    private final FarmMapper farmMapper;
    public FarmController(FarmServiceImpl farmServiceImpl,FarmMapper farmMapper){
        this.farmServiceImpl=farmServiceImpl;
        this.farmMapper=farmMapper;
    }

    @PostMapping("/addFarm")
    public ResponseEntity<Map<String,Object>> addFarm(@RequestBody @Valid addFarmVM addfarmvm){
        Farm farm= farmMapper.toFarm(addfarmvm);
        Farm farm1=farmServiceImpl.addFarm(farm);
        ResponseFarmVM responseFarmVM=farmMapper.toResponseFarmVM(farm1);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Farm added successfully");
        response.put("data", responseFarmVM);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
