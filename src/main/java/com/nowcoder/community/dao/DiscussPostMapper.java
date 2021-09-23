package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
// offset: start index of each page, limit: max index for each page
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

// @Param: 参数别名 如果方法只有一个参数并且在<if>里使用就必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

//    查询帖子详情
    DiscussPost selectDiscussPostById(int id);

}
