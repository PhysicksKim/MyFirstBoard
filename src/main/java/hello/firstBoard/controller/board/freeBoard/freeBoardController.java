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

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class freeBoardController {


    // 해야 할 일
    // 1. 게시판 페이징 구현
    // 1-1. 일단 OFFSET 페이징으로 간단히 구현해봄
    // 1-2. 그 다음 Cursor 페이징으로 구현

    private final BoardService boardService;


    // 게시글은
    // 글 번호 | 글 제목 | 작성자 | 작성일 로 보여줌
    @GetMapping("/board/free")
    public String freeBoardListTest(Model model) {
        List<Post> postList = boardService.getPostList();

        model.addAttribute("postList", postList);

        return ViewPathConst.FREEBOARD_LIST;
    }

    @GetMapping("/board/free/{postId}")
    public String freeBoardReadPost(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_POST;
    }

    @GetMapping("/board/free/postWrite/{postId}")
    public String freeBoardWritePost(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return "board/postUpdate";
    }

    @GetMapping("/board/free/postWrite")
    public String freeBoardWritePost() {
        return "board/postWrite";
    }

    @PostMapping("/board/write")
    public String writePost(@ModelAttribute PostWrite postWrite) {
        Post post = new Post(postWrite);

        Post savedPost = boardService.savePost(post);

        return "redirect:/board/free/"+savedPost.getId();
    }

    @PostMapping("/board/update")
    public String updatePost(@ModelAttribute Post post) {
        boardService.updatePost(post);
        return "redirect:/board/free/"+post.getId();
    }

    @PostMapping("/board/free/postDelete")
    public String deletePost(@RequestParam long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }
}
