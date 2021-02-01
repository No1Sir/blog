package com.it.fa.dao;


import com.it.fa.model.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ILogDao {
    void addLog(@Param("action")String action,@Param("ip") String remoteAddr);

    List<Log> findAll();
}
