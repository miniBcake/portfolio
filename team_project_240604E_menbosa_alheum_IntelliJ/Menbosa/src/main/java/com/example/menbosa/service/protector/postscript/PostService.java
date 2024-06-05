package com.example.menbosa.service.protector.postscript;

import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.postscript.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    void registerPost(PostWriteDTO postWriteDTO);

    void regitsterPostWithImgFile(PostWriteDTO postWriteDTO, List<MultipartFile> files) throws IOException;

    ImgFileDTO saveImgFile(MultipartFile file) throws IOException;

    void removePost(Long boardRecomNum);

    void modifyImgFile(PostUpdateDTO postUpdateDTO, List<MultipartFile> files) throws IOException;

    PostViewDTO findPostNum(Long boardRecomNum);

    List<PostListDTO> findAll();

    List<PostListDTO> findPostAll(Criteria criteria);

    int findTotal();
}
