package com.it.fa.service.relation.impl;

import com.it.fa.dao.IRelationDao;
import com.it.fa.model.Label;
import com.it.fa.service.label.ILabelService;
import com.it.fa.service.relation.IRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelationServiceImpl implements IRelationService {
    @Autowired
    private IRelationDao relationDao;
    @Override
    public void addRelation(Integer cid,List<Integer> newLabels) {
        relationDao.addRelation(cid,newLabels);
    }

    @Override
    public void deleteRelation(Integer cid, List<Integer> list) {
        relationDao.deleteRelation(cid,list);
    }
}
