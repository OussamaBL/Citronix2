package com.citronix.citronix.web.rest.v1.Tree;

import com.citronix.citronix.domain.Tree;
import com.citronix.citronix.service.TreeService;
import com.citronix.citronix.web.vm.Mapper.Tree.TreeMapper;
import com.citronix.citronix.web.vm.Tree.ResponseTreeVM;
import com.citronix.citronix.web.vm.Tree.addTreeVM;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trees")
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }


    @PostMapping("/save/{fieldUuid}")
    public ResponseEntity<ResponseTreeVM> saveTree(@PathVariable UUID fieldUuid, @RequestBody @Valid addTreeVM addtreeVM) {
        Tree tree=treeMapper.toTree(addtreeVM);
        Tree savedTree = treeService.addTree(fieldUuid, tree);
        ResponseTreeVM response = treeMapper.toResponseTreeVM(savedTree);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all/{fieldUuid}")
    public ResponseEntity<List<ResponseTreeVM>> getAllTreesByField(@PathVariable UUID fieldUuid) {
        List<Tree> trees = treeService.findAllTreesByField(fieldUuid);
        List<ResponseTreeVM> response = trees.stream()
                .map(treeMapper::toResponseTreeVM)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteTree(@PathVariable UUID uuid) {
        treeService.deleteTree(uuid);
        return ResponseEntity.ok("Tree deleted successfully.");
    }
}
