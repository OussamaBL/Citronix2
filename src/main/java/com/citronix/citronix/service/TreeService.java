package com.citronix.citronix.service;

import com.citronix.citronix.domain.Tree;

import java.util.List;
import java.util.UUID;

public interface TreeService {
    Tree addTree(UUID field_id, Tree tree);

    Tree updateTree(Tree tree);
    void deleteTree(UUID id);

    List<Tree> findAllTreesByField(UUID field_id);
}
