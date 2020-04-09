package xigua.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import xigua.community.model.Quesiton;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag}) ")
    void create(Quesiton quesiton);

    @Select("select * from question limit #{offset},#{size}")
    List<Quesiton> list(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator =  #{userId} limit #{offset},#{size}")
    List<Quesiton> listByUserId(@Param("userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from question where creator =  #{userId}")
    Integer countByUserId(@Param("userId") Integer userId);

    @Select("select * from question where id =  #{id}")
    Quesiton getById(@Param("id") Integer id);
}
