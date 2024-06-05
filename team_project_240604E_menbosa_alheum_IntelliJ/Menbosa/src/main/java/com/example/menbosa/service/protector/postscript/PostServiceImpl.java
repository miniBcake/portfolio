package com.example.menbosa.service.protector.postscript;

import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.postscript.*;
import com.example.menbosa.mapper.protector.communicate.FileMapper;
import com.example.menbosa.mapper.protector.postscript.ImgFileMapper;
import com.example.menbosa.mapper.protector.postscript.PostScriptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService  {

    private final PostScriptMapper postScriptMapper;
    private final ImgFileMapper imgFileMapper;
    private final FileMapper fileMapper;

    @Value("C:/upload/")
    private String fileDir;

    @Override
    public void registerPost(PostWriteDTO postWriteDTO) {
        postScriptMapper.insertPost(postWriteDTO);
    }

    @Override
    public void regitsterPostWithImgFile(PostWriteDTO postWriteDTO, List<MultipartFile> imgFiles) throws IOException {
        postScriptMapper.insertPost(postWriteDTO);
        Long boardRecomNum = postWriteDTO.getBoardRecomNum();

        for(MultipartFile imgFile : imgFiles) {
            if(imgFile.isEmpty()) {
                break;
            }

            ImgFileDTO imgFileDTO = saveImgFile(imgFile);
            imgFileDTO.setBoardRecomNum(boardRecomNum);
            imgFileMapper.insertImgFile(imgFileDTO);
        }
    }

    @Override
    public ImgFileDTO saveImgFile(MultipartFile imgFile) throws IOException {
        String originalImgFilename = imgFile.getOriginalFilename();
        UUID imgFileServer = UUID.randomUUID();

        String systemName = imgFileServer.toString() + "_" + originalImgFilename;

        File uploadPath = new File(fileDir, getUploadPath());

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        File uploadFile = new File(uploadPath, systemName);

        imgFile.transferTo(uploadFile);

        ImgFileDTO imgFileDTO = new ImgFileDTO();
        imgFileDTO.setImgFileServer(imgFileServer.toString());
        imgFileDTO.setImgFileUser(originalImgFilename);
        imgFileDTO.setImgFileExt(getUploadPath());

        return imgFileDTO;
    }

    private String getUploadPath() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    @Override
    public void removePost(Long boardRecomNum) {
        List<ImgFileDTO> imgFileList = imgFileMapper.selectImgList(boardRecomNum);
        imgFileMapper.deleteImgFile(boardRecomNum);
        postScriptMapper.deletePost(boardRecomNum);

        for(ImgFileDTO file:imgFileList){
            File targetFile = new File(fileDir, file.getImgFileExt() + "/" + file.getImgFileServer() + "_" + file.getImgFileUser());

            if(targetFile.exists()) {
                targetFile.delete();
            }
        }

    }

    @Override
    public void modifyImgFile(PostUpdateDTO postUpdateDTO, List<MultipartFile> files) throws IOException {
        postScriptMapper.updatePost(postUpdateDTO);
        Long boardRecomNum = postUpdateDTO.getBoardRecomNum();

        fileMapper.deleteFile(boardRecomNum);

        for(MultipartFile imgFile : files) {
            if(imgFile.isEmpty()) {
                break;
            }

            ImgFileDTO imgFileDTO = saveImgFile(imgFile);
            imgFileDTO.setBoardRecomNum(boardRecomNum);
            imgFileMapper.insertImgFile(imgFileDTO);
        }
    }

    @Override
    public PostViewDTO findPostNum(Long boardRecomNum) {
        return postScriptMapper.selectPostNum(boardRecomNum).orElseThrow(() -> new IllegalStateException("유효하지 않은 게시물 번호"));
    }

    @Override
    public List<PostListDTO> findAll() {
        return postScriptMapper.selectAll();
    }

    @Override
    public List<PostListDTO> findPostAll(Criteria criteria) {
        return postScriptMapper.selectPostALL(criteria);
    }

    @Override
    public int findTotal() {
        return postScriptMapper.selectTotal();
    }
}
