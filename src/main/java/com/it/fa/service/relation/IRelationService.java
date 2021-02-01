package com.it.fa.service.relation;

import java.util.List;

public interface IRelationService {
    void addRelation(Integer cid,List<Integer> newLabels);

    void deleteRelation(Integer cid, List<Integer> list);
}
