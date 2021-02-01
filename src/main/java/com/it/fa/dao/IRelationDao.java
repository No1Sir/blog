package com.it.fa.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRelationDao {
    void addRelation(@Param("cid") Integer cid,@Param("list") List list);
    @Delete("<script>"+
            "delete from t_relationships where cid = #{cid} and "+
            "lid in "+
            "<foreach collection='labels' separator=',' open='(' close=')' item='c'> "+
            "#{c} "+
            "</foreach> "+
            "</script>")
    void deleteRelation(@Param("cid") Integer cid,@Param("labels") List<Integer> list);
}
