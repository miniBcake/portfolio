package com.example.menbosa.mapper.protector.postscript;

import com.example.menbosa.dto.protector.postscript.ImgFileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgFileMapper {
    void insertImgFile(ImgFileDTO imgFileDTO);

    void deleteImgFile(Long boardRecomNum);

    List<ImgFileDTO> selectImgList(Long boardRecomNum);
}
