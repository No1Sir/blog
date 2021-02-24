package com.it.fa.service.log.impl;

import com.it.fa.dao.ILogDao;
import com.it.fa.model.Log;
import com.it.fa.service.log.ILogService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogServiceImpl implements ILogService {
    @Resource
    private ILogDao logDao;
    @Override
    public void addLog(String action, String remoteAddr) {
        logDao.addLog(action,remoteAddr);
    }
    @Override
    public List<Log> findAll() {
        return logDao.findAll();
    }
}
