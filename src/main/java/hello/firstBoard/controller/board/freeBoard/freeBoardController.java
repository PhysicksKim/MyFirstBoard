package hello.firstBoard.controller.board.freeBoard;

import hello.firstBoard.consts.ViewPathConst;
import hello.firstBoard.domain.board.Post;
import hello.firstBoard.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/board/free/post")
    public String freeBoardSave(Model model) {
        Post post = new Post();
        post.setTitle("Test 제목");
        post.setWriter("Test 작성자");
        post.setContent("Test 글 내용");

        boardService.savePost(post);
//        return "redirect:"+ViewPathConst.FREEBOARD_PAGE;
        return "redirect:/";
    }
}
