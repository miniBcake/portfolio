package com.example.menbosa.api;

import com.example.menbosa.dto.protector.communicate.FileDto;
import com.example.menbosa.service.protector.communicate.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileApi {
    private final FileService fileService;

    @Value("C:/upload/")
    private String fileDir;

    @GetMapping("/v1/commu/{boardCommuNum}/files")
    public List<FileDto> findFile(@PathVariable("boardCommuNum") Long boardCommuNum){
        return fileService.findFile(boardCommuNum);
    }

    @GetMapping("/v1/files")
    public byte[] display(String fileName) throws IOException {
        File file = new File(fileDir, fileName);
        return FileCopyUtils.copyToByteArray(file);
    }
}
