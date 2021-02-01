package com.it.fa.dao;
import com.it.fa.model.Label;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ILabelDao{
    @Select("select l.*,count(r.lid) as count from t_labels l" +
            " left join t_relationships r on l.lid = r.lid" +
            " where type = 'category' group by l.lid")
    List<Label> findLabelByCategory();
    @Select("select l.*,count(r.lid) as count from t_labels l" +
            " left join t_relationships r on l.lid = r.lid" +
            " where type = 'tag' group by l.lid")
    List<Label> findLabelByTag();
    @Delete("delete from t_labels where lid = #{lid}")
    int deleteLabelByLid(Integer lid);
    @Select("select * from t_labels")
    List<Label> findAll();
    @Insert("insert into t_labels(name,type) values(#{category},'category')")
    int addCategory(String category);
    @Insert("insert into t_labels(name,type) values(#{name},'tag')")
    void addTag(String name);
    List findCategoryByName(@Param("categories")List list);

    List<Integer> findTagsId(@Param("tags")List<String> newTags);

    String[] findAllInRequest(@Param("cid")Integer cid,@Param("metas") Map metas);

    List<Integer> findAllByNames(@Param("names") List<String> newNames);

    List<Integer> findAllByNames(@Param("names") String[] newNames);
    @Select("<script>\n"+
            "SELECT l.lid FROM `t_relationships` r " +
            "left join t_labels l on r.lid = l.lid " +
            "where r.cid = #{cid} " +
            "and name not in "+
            "<foreach item='c' collection='names' open='(' separator=',' close=')'> " +
            "#{c} "+
            "</foreach> "+
            "</script>")
    List<Integer> findAllOldMetas(@Param("cid") Integer cid,@Param("names") String[] oldMetas);

    Integer[] findAllNotInRequest(@Param("metas")HashMap<String, Integer> map);
    @Select("<script> select count(*) as count from t_labels where name in " +
            "<foreach item='c' collection='map.keys' open='(' separator=',' close=')'> " +
            "#{c} "+
            "</foreach> "+
            "</script>")
    int findAllInRequestLength(@Param("map") HashMap<String, Integer> map);
}
