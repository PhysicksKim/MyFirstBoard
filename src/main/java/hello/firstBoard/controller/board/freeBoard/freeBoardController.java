package hello.firstBoard.controller.board.freeBoard;

import hello.firstBoard.consts.ViewPathConst;
import hello.firstBoard.domain.board.Post;
import hello.firstBoard.domain.board.PostWrite;
import hello.firstBoard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class freeBoardController {

    private final BoardService boardService;

    // 게시글은
    // 글 번호 | 글 제목 | 작성자 | 작성일 로 보여줌

    @GetMapping("/board/free")
    public String freeBoardList(Model model) {

        return ViewPathConst.FREEBOARD_PAGE;
    }

//    @PostMapping("/board/free/post")
//    public String freeBoardSave(Model model) {
//        Post post = new Post();
//        post.setTitle("Test 제목");
//        post.setWriter("Test 작성자");
//        post.setContent("Test 글 내용");
//
//        boardService.savePost(post);
////        return "redirect:"+ViewPathConst.FREEBOARD_PAGE;
//        return "redirect:/";
//    }

    @GetMapping("/board/free/list")
    public String freeBoardListTest(Model model) {
        List<Post> postList = boardService.getPostList();
        log.info(postList.toString());
        for (Post post : postList) {
            log.info("Post Id : {}",post.getId());
            log.info("Post Writer : {}", post.getWriter());
            log.info("Post Title : {}", post.getTitle());
            log.info("Post Content : {}", post.getContent());
            log.info("Post Date : {}", post.getDate());
        }

        model.addAttribute("postList", postList);

        return ViewPathConst.FREEBOARD_LIST;
    }

    @GetMapping("/board/free/{postId}")
    public String freeBoardReadPost(@PathVariable long postId, Model model) {

        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_POST;
    }

    @GetMapping("/board/free/postWrite")
    public String freeBoardWritePost() {

        return "board/postWrite";
    }

    @PostMapping("/board/write")
    public String writePost(@ModelAttribute PostWrite postWrite) {

        log.info("TITLE : {}", postWrite.getTitle());
        log.info("WRITER : {}", postWrite.getWriter());
        log.info("CONTENT : {}", postWrite.getContent());

        Post post = new Post(postWrite);



        return "redirect:/";
    }
}
