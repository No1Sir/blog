package com.it.fa.service.label;

import com.it.fa.model.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ILabelService {

    List<Label> findAllByCategory();

    List<Label> findAllByTag();

    int addCategory(String cname);

    int deleteLabelByLid(Integer lid);

    void addTag(String name);

    List findCategoryByName(String[] categories);

    String[] findAllInRequest(Integer cid,Map metas);

    List<Integer> findAllByNames(List<String> newNames);

    List<Integer> findAllByNames(String[] newNames);

    List<Integer> findAllOldMetas(Integer cid, String[] oldMetas);

    Integer[] findAllNotInRequest(HashMap<String, Integer> map);

    int findAllInRequestLength(HashMap<String, Integer> map);
}
