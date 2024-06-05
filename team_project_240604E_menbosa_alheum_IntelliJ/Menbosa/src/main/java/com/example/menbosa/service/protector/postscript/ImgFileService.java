package com.example.menbosa.service.protector.postscript;

import com.example.menbosa.dto.protector.postscript.ImgFileDTO;

import java.util.List;

public interface ImgFileService {
    void registerImgFile(ImgFileDTO imgFileDTO);

    void removeImgFile(Long boardRecomNum);

    List<ImgFileDTO> findList(Long boardRecomNum);
}
