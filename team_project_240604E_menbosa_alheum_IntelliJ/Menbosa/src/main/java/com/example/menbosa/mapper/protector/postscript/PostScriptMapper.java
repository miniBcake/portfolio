package com.example.menbosa.mapper.protector.postscript;

import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.postscript.PostListDTO;
import com.example.menbosa.dto.protector.postscript.PostUpdateDTO;
import com.example.menbosa.dto.protector.postscript.PostViewDTO;
import com.example.menbosa.dto.protector.postscript.PostWriteDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostScriptMapper {
    void insertPost(PostWriteDTO postWriteDTO);

    void deletePost(Long boardRecomNum);

    void updatePost(PostUpdateDTO postUpdateDTO);

    Optional<PostViewDTO> selectPostNum(Long boardRecomNum);

    List<PostListDTO> selectAll();

    List<PostListDTO> selectPostALL(Criteria criteria);

    int selectTotal();
}
