package com.it.fa.dao;

import com.it.fa.model.Attach;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IAttachDao {
    @Insert("insert into t_attach(created,fname,ftype,authorId,fkey) " +
            "values(UNIX_TIMESTAMP(NOW()),#{fname},#{ftype},#{authorId},#{fkey})")
    int add(Attach attach);
    @Select("select * from t_attach")
    List<Attach> findAll();
}
