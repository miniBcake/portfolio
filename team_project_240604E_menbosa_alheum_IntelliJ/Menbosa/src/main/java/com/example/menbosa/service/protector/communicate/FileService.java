package com.example.menbosa.service.protector.communicate;

import com.example.menbosa.dto.protector.communicate.FileDto;

import java.util.List;

public interface FileService {
    void registerFile(FileDto fileDto);

    void removeFile(Long boardCommuNum);

    List<FileDto> findFile(Long boardCommuNum);

}
