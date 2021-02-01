package com.it.fa.service.attach.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.fa.dao.IAttachDao;
import com.it.fa.model.Attach;
import com.it.fa.service.attach.IAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttachServiceImpl implements IAttachService {
    @Resource
    private IAttachDao attachDao;

    @Override
    public int add(Attach attach) {
        return attachDao.add(attach);
    }

    @Override
    public PageInfo<Attach> findAll(int page, int limit) {
        PageHelper.startPage(page,limit);
        List<Attach> attaches = attachDao.findAll();
        PageInfo pageInfo = new PageInfo(attaches);
        return pageInfo;
    }
}
