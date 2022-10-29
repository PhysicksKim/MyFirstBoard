package hello.firstBoard.controller.board.freeBoard;

import hello.firstBoard.consts.ViewPathConst;
import hello.firstBoard.domain.board.Pagination;
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

    private final BoardService boardService;


    // 게시글은
    // 글 번호 | 글 제목 | 작성자 | 작성일 로 보여줌
    @GetMapping("/board/free") // 게시판 첫 진입 (리스트)
    public String freeBoardFirstList(Model model,
                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "5") Integer postPerPage) {
        // 여기서 @RequestParam에서 defaultValue를 안해주면, 쿼리파라미터가 안넘어올 경우 에러가 난다.
        // 근데 쿼리파라미터가 없으면 첫페이지로 지정하고 싶었기에, 위처럼 defaultValue = "-1" 로 했다

        Pagination pagination = new Pagination();
        if(page == 1) {
            pagination.setFirstPage();
            pagination.setLastPage(boardService.getLastPage(postPerPage));
        }
        else {
            pagination.setNowPage(page);
            pagination.setLastPage(boardService.getLastPage(postPerPage));
        }


        // 기본 첫 페이지 보여주도록
        // 페이징에 따라서 포스트리스트 다르게 넣어서 출력해줘야함
        List<Post> postList = boardService.getPostList();
        model.addAttribute("postList", postList);
        model.addAttribute("pagination", pagination);

        return ViewPathConst.FREEBOARD_LIST;
    }

    @GetMapping("/board/free/{postId}") // postId 글 페이지
    public String freeBoardReadPost(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_POST;
    }

    @GetMapping("/board/free/write/{postId}") // postId 글 수정 페이지
    public String freeBoardWritePost(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return "board/postUpdate";
    }

    @GetMapping("/board/free/write") // 새 글 작성 페이지
    public String freeBoardWritePost() {
        return "board/postWrite";
    }

    @PostMapping("/board/write") // 글 작성 요청
    public String writePost(@ModelAttribute PostWrite postWrite) {
        Post post = new Post(postWrite);

        Post savedPost = boardService.savePost(post);

        return "redirect:/board/free/"+savedPost.getId();
    }

    @PostMapping("/board/update") // 글 수정 요청
    public String updatePost(@ModelAttribute Post post) {
        boardService.updatePost(post);
        return "redirect:/board/free/"+post.getId();
    }

    @PostMapping("/board/free/postDelete") // 글 삭제 요청
    public String deletePost(@RequestParam long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }
}
