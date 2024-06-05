package com.example.menbosa.service.protector.postscript;

import com.example.menbosa.dto.protector.postscript.ImgFileDTO;
import com.example.menbosa.mapper.protector.postscript.ImgFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImgFileServiceImpl implements ImgFileService {

    private final ImgFileMapper imgFileMapper;

    @Override
    public void registerImgFile(ImgFileDTO imgFileDTO) {
        imgFileMapper.insertImgFile(imgFileDTO);
    }

    @Override
    public void removeImgFile(Long boardRecomNum) {
        imgFileMapper.deleteImgFile(boardRecomNum);
    }

    @Override
    public List<ImgFileDTO> findList(Long boardRecomNum) {
        return imgFileMapper.selectImgList(boardRecomNum);
    }
}
