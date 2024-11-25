package com.citronix.citronix.service.impl;

import com.citronix.citronix.domain.Field;
import com.citronix.citronix.domain.Tree;
import com.citronix.citronix.exception.Field.FieldNotFoundException;
import com.citronix.citronix.exception.Tree.TreeInvalidException;
import com.citronix.citronix.exception.Tree.TreeNotFoundException;
import com.citronix.citronix.exception.Tree.TreePlantingSaisonException;
import com.citronix.citronix.repository.FieldRepository;
import com.citronix.citronix.repository.TreeRepository;
import com.citronix.citronix.service.TreeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class TreeServiceImpl implements TreeService {
    private final TreeRepository treeRepository;
    private final FieldRepository fieldRepository;

    public TreeServiceImpl(TreeRepository treeRepository, FieldRepository fieldRepository) {
        this.treeRepository = treeRepository;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Tree addTree(UUID field_id, Tree tree) {
        Optional<Field> field=fieldRepository.findById(field_id);
        field.orElseThrow(()-> new FieldNotFoundException("field not found"));

        if (!tree.isPlantingSeason())
            throw new TreePlantingSaisonException("Trees can only be planted between March and May.");
        Field field1=field.get();

        double totalTreeField=field1.getArea()*100;
        double totalTreesExist=field1.getTreeList().size();
        if(totalTreesExist+1>totalTreeField) throw new TreeInvalidException("Field has reached the maximum tree density.    ");

        tree.setField(field1);

        return treeRepository.save(tree);
    }

    @Override
    public Tree updateTree(Tree tree) {
        return null;
    }

    @Override
    public void deleteTree(UUID id) {
        Optional<Tree> tree=treeRepository.findById(id);
        tree.orElseThrow(()-> new TreeNotFoundException("Tree not found"));
        treeRepository.delete(tree.get());
    }
    @Override
    public List<Tree> findAllTreesByField(UUID field_id){
        Optional<Field> field=fieldRepository.findById(field_id);
        field.orElseThrow(()-> new FieldNotFoundException("field not found"));
        return treeRepository.findAllByFieldId(field_id);
    }
}
