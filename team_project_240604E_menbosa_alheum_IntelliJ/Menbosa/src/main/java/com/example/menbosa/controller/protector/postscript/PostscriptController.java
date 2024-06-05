package com.example.menbosa.controller.protector.postscript;

import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Page;
import com.example.menbosa.dto.protector.postscript.PostListDTO;
import com.example.menbosa.dto.protector.postscript.PostUpdateDTO;
import com.example.menbosa.dto.protector.postscript.PostViewDTO;
import com.example.menbosa.dto.protector.postscript.PostWriteDTO;
import com.example.menbosa.service.protector.postscript.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/alheum/community")
@RequiredArgsConstructor
public class PostscriptController {
    private final PostService postService;

    @GetMapping("/postMain")
    public String PostList(Criteria criteria, Model model) {
        List<PostListDTO> postList = postService.findPostAll(criteria);
        int total = postService.findTotal();
        Page page = new Page(criteria, total);

        model.addAttribute("postList", postList);
        model.addAttribute("page", page);
        return "/protector/protectorCommunity-postscriptMain";
    }

    @GetMapping("/postWrite")
    public String PostWrite(@SessionAttribute(value="proMemNum", required=false)Long proMemNum){
        return proMemNum == null ? "redirect:/alheum/user/login":"/protector/protectorCommunity-postscriptWrite";
    }

    @PostMapping("/postWrite")
    public String PostWrite(PostWriteDTO postWriteDTO, @SessionAttribute(value = "proMemNum") Long proMemNum,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("postFile")List<MultipartFile> files)  {

        postWriteDTO.setProMemNum(proMemNum);
        System.out.println("postWriteDTO: " + postWriteDTO);

        try {
            postService.regitsterPostWithImgFile(postWriteDTO, files);
        }catch (IOException e){
            e.printStackTrace();
        }

        Long boardRecomNum = postWriteDTO.getBoardRecomNum();
        redirectAttributes.addFlashAttribute("boardRecomNum", boardRecomNum);

        return "redirect:/alheum/community/postDetails?boardRecomNum=" + boardRecomNum;
    }

    @GetMapping("/postDetails")
    public String PostDetails(Long boardRecomNum, Model model){
        PostViewDTO detail = postService.findPostNum(boardRecomNum);
        model.addAttribute("detail", detail);
        return "protector/protectorCommunity-postscriptDetails";
    }

    @GetMapping("/postModify")
    public String PostModify(Long boardRecomNum, Model model){
        PostViewDTO detail = postService.findPostNum(boardRecomNum);
        model.addAttribute("detail", detail);

        return "/protector/protectorCommunity-postscriptModify";
    }

    @PostMapping("/postModify")
    public String PostModify(PostUpdateDTO postUpdateDTO,
                             @RequestParam("boardFile") List<MultipartFile> files,
                             RedirectAttributes redirectAttributes){
        try{
            postService.modifyImgFile(postUpdateDTO, files);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        Long boardRecomNum = postUpdateDTO.getBoardRecomNum();
        redirectAttributes.addFlashAttribute("boardRecomNum", boardRecomNum);
        return "redirect:/alheum/community/postDetails?boardRecomNum=" + boardRecomNum;
    }

    @GetMapping("/postRemove")
    public RedirectView PostRemove(@RequestParam("boardRecomNum") Long boardRecomNum){
        postService.removePost(boardRecomNum);
        return new RedirectView("/alheum/community/postMain");
    }
}
