package com.citronix.citronix.web.rest.v1.Farm;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.service.FarmService;
import com.citronix.citronix.web.vm.Farm.ResponseFarmVM;
import com.citronix.citronix.web.vm.Farm.UpdateFarmVM;
import com.citronix.citronix.web.vm.Farm.addFarmVM;
import com.citronix.citronix.web.vm.Field.SearchDTO;
import com.citronix.citronix.web.vm.Mapper.Farm.FarmMapper;
import com.citronix.citronix.web.vm.Mapper.Farm.UpdateFarmMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/farms")
public class FarmController {
    private final FarmService farmService;
    private final FarmMapper farmMapper;
    private final UpdateFarmMapper updateFarmMapper;
    public FarmController(FarmService farmService,FarmMapper farmMapper,UpdateFarmMapper updateFarmMapper){
        this.farmService=farmService;
        this.farmMapper=farmMapper;
        this.updateFarmMapper=updateFarmMapper;
    }

    @GetMapping("/allFarms")
    public ResponseEntity<Map<String,Object>> allFarms(){
        List<Farm> farmList =farmService.getFarms();
        List<ResponseFarmVM> responseFarmVMList= farmList.stream().map((farm -> farmMapper.toResponseFarmVM(farm) )).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("data", responseFarmVMList);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }
    @PostMapping("/addFarm")
    public ResponseEntity<Map<String,Object>> addFarm(@RequestBody @Valid addFarmVM addfarmvm){
        Farm farm= farmMapper.toFarm(addfarmvm);
        Farm farm1=farmService.addFarm(farm);
        ResponseFarmVM responseFarmVM=farmMapper.toResponseFarmVM(farm1);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Farm added successfully");
        response.put("data", responseFarmVM);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PutMapping("/updateFarm/{id}")
    public ResponseEntity<Map<String,Object>> updateFarm(@RequestBody @Valid UpdateFarmVM updateFarmVM, @PathVariable UUID id){
        Farm farm= updateFarmMapper.toFarm(updateFarmVM);
        farm.setId(id);
        Farm farm1=farmService.updateFarm(farm);
        ResponseFarmVM responseFarmVM=farmMapper.toResponseFarmVM(farm1);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Farm updated successfully");
        response.put("data", responseFarmVM);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFarm(@PathVariable UUID id) {
        farmService.deleteFarm(id);
        return ResponseEntity.ok("Farm deleted successfully");
    }

    @PostMapping("/findByCriteria")
    public ResponseEntity<List<ResponseFarmVM>> findByCriteria(@RequestBody @Valid SearchDTO searchDTO){
        List<Farm> farmList=farmService.findByCriteria(searchDTO);
        List<ResponseFarmVM> responseUserVMList=farmList.stream().map((farm)->farmMapper.toResponseFarmVM(farm)).collect(Collectors.toList());
        return new ResponseEntity<>(responseUserVMList,HttpStatus.OK);
    }


    @PostMapping("/saveWithoutList")
    public ResponseEntity<Map<String,Object>> saveWithoutList(@RequestBody @Valid addFarmVM addfarmvm){
        Farm farm= farmMapper.toFarm(addfarmvm);
        Farm farm1=farmService.saveWithoutList(farm);
        ResponseFarmVM responseFarmVM=farmMapper.toResponseFarmVM(farm1);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Farm added successfully");
        response.put("data", responseFarmVM);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @PostMapping("/saveWithList")
    public ResponseEntity<Map<String,Object>> saveWithList(@RequestBody @Valid addFarmVM addfarmvm){
        Farm farm= farmMapper.toFarm(addfarmvm);
        Farm farm1=farmService.saveWithList(farm);
        ResponseFarmVM responseFarmVM=farmMapper.toResponseFarmVM(farm1);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Farm added successfully");
        response.put("data", responseFarmVM);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @GetMapping("/getFarmLess4000")
    public ResponseEntity<Map<String,Object>> getFarmLess4000(){
        List<Farm> farmList=farmService.getFarmLess4000();
        List<ResponseFarmVM> farmVMList= farmList.stream().map((farm)-> farmMapper.toResponseFarmVM(farm) ).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("data", farmVMList);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
