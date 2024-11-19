package com.citronix.citronix.web.vm.Mapper.Tree;

import com.citronix.citronix.domain.Farm;
import com.citronix.citronix.domain.Tree;
import com.citronix.citronix.web.vm.Farm.ResponseFarmVM;
import com.citronix.citronix.web.vm.Farm.addFarmVM;
import com.citronix.citronix.web.vm.Tree.ResponseTreeVM;
import com.citronix.citronix.web.vm.Tree.addTreeVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TreeMapper {
    ResponseTreeVM toResponseTreeVM(Tree tree);
    Tree toTree(addTreeVM addtreeVM);
}
