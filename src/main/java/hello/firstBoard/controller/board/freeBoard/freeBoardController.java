package hello.firstBoard.controller.board.freeBoard;

import hello.firstBoard.consts.ViewPathConst;
import hello.firstBoard.domain.board.Pages.*;
import hello.firstBoard.domain.board.Posts.Post;
import hello.firstBoard.domain.board.Posts.PostWrite;
import hello.firstBoard.service.board.BoardService;
import hello.firstBoard.validator.PostWriteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board/*")
public class freeBoardController {

    private final BoardService boardService;
    private final PostWriteValidator postWriteValidator;

    @InitBinder("postWrite") // "postWrite" 를 적어주지 않으면 모든 객체에 영향을 미친다
    public void initBoardValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(postWriteValidator);
    }

    @GetMapping("free") // 게시판 글 목록 보여주는 페이지
    public String pagePostList(Model model,
                               @ModelAttribute PageRequestDTO pageRequestDTO) {
        log.info("controller binding RefacRequestPageDTO : {}", pageRequestDTO);

        List<Post> postList = boardService.getPostList(pageRequestDTO);
        PageViewDTO pageViewDTO = boardService.getPageViewDTO(pageRequestDTO);

        model.addAttribute("postList", postList);
        model.addAttribute("pageViewDTO", pageViewDTO);

        return ViewPathConst.FREEBOARD_LIST;
    }

    //http://localhost:8080/board/free/search?searchType=제목&searchKeyword=asd
    @GetMapping("free/search")
    public String pageSearchList(Model model,
                                 @ModelAttribute SearchRequestDTO searchRequestDTO,
                                 @ModelAttribute PageRequestDTO pageRequestDTO) {
        // ----- log용 ------- 나중에 지움
        log.info("SearchRequestDTO : {}", searchRequestDTO);
        SearchDAO searchDAO = new SearchDAO(searchRequestDTO);
        log.info("searchDTO : {}" , searchRequestDTO);
        log.info("searchDAO : {}" , searchDAO);
        // ---------------------------

        List<Post> searchList = boardService.getSearchList(searchRequestDTO);
        PageViewDTO pageViewDTO = boardService.getPageViewDTO(pageRequestDTO);

        // 페이지에 맞는 글 목록들을 담아서 넘겨줌
        model.addAttribute("postList", searchList);
        model.addAttribute("pageViewDTO", pageViewDTO);

        return ViewPathConst.FREEBOARD_LIST;
    }

    @GetMapping("free/{postId}") // postId 글 페이지
    public String pagePostRead(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_POST;
    }

    @GetMapping("free/write/{postId}") // postId 글 수정 페이지
    public String pagePostUpdate(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_UPDATE;
    }

    @GetMapping("free/write") // 새 글 작성 페이지
    public String pagePostWrite(Model model) {
        model.addAttribute(new PostWrite());
        log.info(model.toString());
        return ViewPathConst.FREEBOARD_WRITE;
    }

    @PostMapping("write") // 글 작성 요청
    public String writePost(@Validated @ModelAttribute PostWrite postWrite,
                            BindingResult bindingResult,
                            Model model) {

        log.info(model.toString());

        // 에러 발생시
        if (bindingResult.hasErrors())
            return ViewPathConst.FREEBOARD_WRITE;

        // @Validated 처리 후에도, 에러가 없을 경우
        Post post = new Post(postWrite);
        Post savedPost = boardService.savePost(post); // 글 id를 갖고와야하므로

        // 글 id 에러시 처리
        // 글 id 에러시 boardRepository 에서 null 반환함.
        if (savedPost == null)
            return "redirect:/error";

        return "redirect:/board/free/"+savedPost.getId();
    }

    @PostMapping("update") // 글 수정 요청
    public String updatePost(@ModelAttribute Post post) {
        boardService.updatePost(post);
        return "redirect:/board/free/"+post.getId();
    }

    @DeleteMapping("free")
    public String deletePost(@RequestParam long id) {
        boardService.deletePost(id);
        return "redirect:/board/free";
    }
}
