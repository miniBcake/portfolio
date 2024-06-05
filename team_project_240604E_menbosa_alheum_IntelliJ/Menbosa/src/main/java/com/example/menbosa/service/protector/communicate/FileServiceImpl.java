package com.example.menbosa.service.protector.communicate;


import com.example.menbosa.dto.protector.communicate.FileDto;
import com.example.menbosa.mapper.protector.communicate.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileMapper fileMapper;

    @Override
    public void registerFile(FileDto fileDto) {
        fileMapper.insertFile(fileDto);
    }

    @Override
    public void removeFile(Long boardCommuNum) {
        fileMapper.deleteFile(boardCommuNum);
    }

    @Override
    public List<FileDto> findFile(Long boardCommuNum) {
        return fileMapper.selectFile(boardCommuNum);
    }


}
