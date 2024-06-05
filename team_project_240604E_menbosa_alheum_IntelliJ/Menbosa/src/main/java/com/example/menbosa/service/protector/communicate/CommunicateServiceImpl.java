package com.example.menbosa.service.protector.communicate;


import com.example.menbosa.dto.protector.communicate.*;
import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.mapper.protector.communicate.CommunicateMapper;
import com.example.menbosa.mapper.protector.communicate.FileMapper;
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
public class CommunicateServiceImpl implements CommunicateService{
    private final CommunicateMapper communicateMapper;
    private final FileMapper fileMapper;

    @Value("C:/upload/")
    private String fileDir;

    //    소통 게시글 목록
    @Override
    public List<CommuListDto> selectCommuList() {
        return communicateMapper.selectCommuList();
    }

    //  소통 글쓰기
    @Override
    public void registerCommu(CommuWriteDto commuWriteDto) {
        communicateMapper.insertCommu(commuWriteDto);
    }

    @Override
    public void registerCommuWithFile(CommuWriteDto commuWriteDto, List<MultipartFile> files) throws IOException {
        communicateMapper.insertCommu(commuWriteDto);
        Long boardCommuNum = commuWriteDto.getBoardCommuNum();

        for(MultipartFile file : files) {
            if(file.isEmpty()){
                break;
            }


            FileDto fileDto = saveFile(file);
            fileDto.setBoardCommuNum(boardCommuNum);
            fileMapper.insertFile(fileDto);

        }
    }

    @Override
    public FileDto saveFile(MultipartFile files) throws IOException {
        //사용자가 올린 파일이름(확장자를 포함한다)
        String originalFilename = files.getOriginalFilename();
        //파일 이름에 붙여줄 uuid 생성
        UUID uuid = UUID.randomUUID();
        //uuid와 파일이름 합쳐준다
        String systemName = uuid.toString() + "_" + originalFilename;
        //상위 경로와 하위 경로를 합쳐준다
        File uploadPath = new File(fileDir, getUploadPath());

        //경로가 존재하지 않는다면 (폴더가 만들어지지 않았다면)
        if(!uploadPath.exists()){
            //경로에 필요한 모든 폴더를 생성한다
            uploadPath.mkdirs();
        }

        //전체경로와 파일이름을 연결한다
        File uploadFile = new File(uploadPath, systemName);

        //매개변수로 받은 Multipart 객체가 가진 파일을 우리가 만든 경로와 이름으로 저장한다
        files.transferTo(uploadFile);

        FileDto fileDto = new FileDto();
        fileDto.setFileServer(uuid.toString());
        fileDto.setFileUser(originalFilename);
        fileDto.setFileExt(getUploadPath());

        return fileDto;
    }

    @Override
    public CommuDetailDto findCommuDetail(Long boardCommuNum) {
        return communicateMapper.selectCommuDetail(boardCommuNum).orElseThrow(()-> new IllegalStateException("없는 게시물 번호"));
    }

    @Override
    public void modifyCommu(CommuUpdateDto commuUpdateDto, List<MultipartFile> files) throws IOException {
        communicateMapper.updateCommu(commuUpdateDto);
        Long boardCommuNum = commuUpdateDto.getBoardCommuNum();

        fileMapper.deleteFile(boardCommuNum);

        for (MultipartFile file : files){
            if(file.isEmpty()){
                break;
            }

            FileDto fileDto = saveFile(file);
            fileDto.setBoardCommuNum(boardCommuNum);
            fileMapper.insertFile(fileDto);
        }

    }

    @Override
    public void removeCommu(Long boardCommuNum) {
        List<FileDto> fileList = fileMapper.selectFile(boardCommuNum);
        fileMapper.deleteFile(boardCommuNum);
        communicateMapper.deleteCommu(boardCommuNum);

        for (FileDto file : fileList) {
            File target = new File(fileDir, file.getFileExt() + "/" + file.getFileServer() + "_" + file.getFileUser());

            if(target.exists()){
                target.delete();
            }
        }

    }

    private String getUploadPath() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    // 소통 페이징처리
    @Override
    public List<CommuListDto> findAllPage(Criteria criteria) {
        return communicateMapper.selectAllPage(criteria);
    }

    @Override
    public int findTotal() {
        return communicateMapper.selectTotal();
    }


    // 소통 검색
//    @Override
//    public List<CommuListDto> findSearch(String search) {
//        return communicateMapper.selectSearch(search);
//    }


    //    공지
    @Override
    public List<MainListDto> findAll() {
        return communicateMapper.selectMain();
    }

    @Override
    public MainViewDto findByNum(Long announceNum) {
        return communicateMapper.selectByNum(announceNum);
    }

    @Override
    public List<MainListDto> findAllPageAnno(Criteria criteria) { return communicateMapper.selectAllPageAnno(criteria); }

    @Override
    public int findTotalAnno() {
        return communicateMapper.selectTotalAnno();
    }

}
