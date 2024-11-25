package com.citronix.citronix.web.rest.v1.Harvest;

import com.citronix.citronix.domain.Harvest;
import com.citronix.citronix.service.HarvestService;
import com.citronix.citronix.web.vm.Harvest.HarvestResponseVM;
import com.citronix.citronix.web.vm.Harvest.HarvestVM;
import com.citronix.citronix.web.vm.Mapper.Harvest.HarvestMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/harvests")
public class HarvestController {
    private final HarvestService harvestService;
    private final HarvestMapper harvestMapper;

    public HarvestController(HarvestService harvestService, HarvestMapper harvestMapper) {
        this.harvestService = harvestService;
        this.harvestMapper = harvestMapper;
    }


    @PostMapping("/save/{fieldUuid}")
    public ResponseEntity<HarvestResponseVM> save(@RequestBody @Valid HarvestVM harvestVM,@PathVariable UUID fieldUuid) {
        Harvest harvest = harvestMapper.toHarvest(harvestVM);
        Harvest createdHarvest = harvestService.save(harvest , fieldUuid);
        HarvestResponseVM responseVM = harvestMapper.toResponseVM(createdHarvest);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseVM);
    }

    @GetMapping("/find/{harvestId}")
    public ResponseEntity<HarvestResponseVM> getHarvestById(@PathVariable UUID harvestId) {
        Harvest harvest = harvestService.findById(harvestId);
        HarvestResponseVM response = harvestMapper.toResponseVM(harvest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{harvestId}")
    public ResponseEntity<String> deleteHarvest(@PathVariable UUID harvestId) {
        harvestService.delete(harvestId);
        return new ResponseEntity<>("Harvest deleted successfully.", HttpStatus.OK);
    }
}
