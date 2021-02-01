package com.it.fa.service.label.impl;

import com.it.fa.dao.ILabelDao;
import com.it.fa.model.Label;
import com.it.fa.service.label.ILabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LabelServiceImpl implements ILabelService {
    @Resource
    private ILabelDao labelDao;

    @Override
    public List<Label> findAllByCategory() {
        return labelDao.findLabelByCategory();
    }

    @Override
    public List<Label> findAllByTag() {
        return labelDao.findLabelByTag();
    }

    @Override
    public int addCategory(String cname) {
        return labelDao.addCategory(cname);
    }

    @Override
    public int deleteLabelByLid(Integer lid) {
        return labelDao.deleteLabelByLid(lid);
    }

    @Override
    public void addTag(String name) {
         labelDao.addTag(name);
    }

    @Override
    public List findCategoryByName(String[] categories) {
        List list = new ArrayList();
        for(String str:categories){
            list.add(str);
        }
        return labelDao.findCategoryByName(list);
    }

    @Override
    public String[] findAllInRequest(Integer cid,Map metas) {
        return labelDao.findAllInRequest(cid,metas);
    }

    @Override
    public List<Integer> findAllByNames(List<String> newNames) {
        return labelDao.findAllByNames(newNames);
    }

    @Override
    public List<Integer> findAllByNames(String[] newNames) {
        return labelDao.findAllByNames(newNames);
    }

    @Override
    public List<Integer> findAllOldMetas(Integer cid, String[] oldMetas) {
        return labelDao.findAllOldMetas(cid,oldMetas);
    }

    @Override
    public Integer[] findAllNotInRequest(HashMap<String, Integer> map) {
        return labelDao.findAllNotInRequest(map);
    }

    @Override
    public int findAllInRequestLength(HashMap<String, Integer> map) {
        return labelDao.findAllInRequestLength(map);
    }


}
