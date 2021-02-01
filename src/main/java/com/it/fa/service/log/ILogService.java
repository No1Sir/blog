package com.it.fa.service.log;

import com.it.fa.constant.LogActions;
import com.it.fa.model.Log;

import java.util.List;

public interface ILogService {
    void addLog(String action, String remoteAddr);

    List<Log> findAll();
}
