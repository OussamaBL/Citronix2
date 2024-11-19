package com.citronix.citronix.service;

import com.citronix.citronix.domain.Tree;

import java.util.UUID;

public interface TreeService {
    Tree addTree(UUID field_id, Tree tree);

    Tree updateTree(Tree tree);
    void deleteTree(UUID id);
}
