package org.csu.ffms.service;

import org.csu.ffms.domain.Disburse;
import org.csu.ffms.persistence.DisburseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisburseService {
    @Autowired
    DisburseMapper disburseMapper;

    public void newDisburse(Disburse disburse){
        disburseMapper.newDisburse(disburse);
    }

    public void deleteDisburse(int disburseId){
        disburseMapper.deleteDisburse(disburseId);
    }

    public Disburse updateDisburse(Disburse disburse){
        disburseMapper.updateDisburse(disburse);
        return disburse;
    }

    public List<Disburse> findDisburseList(Disburse disburse){
        return disburseMapper.findDisburseList(disburse);
    }


}
