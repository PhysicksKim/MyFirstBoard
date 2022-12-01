package hello.firstBoard.controller.board.freeBoard;

import hello.firstBoard.consts.ViewPathConst;
import hello.firstBoard.domain.board.PageVO;
import hello.firstBoard.domain.board.Post;
import hello.firstBoard.domain.board.PostWrite;
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

    // 게시글은
    // 글 번호 | 글 제목 | 작성자 | 작성일 로 보여줌
    @GetMapping("free") // 게시판 첫 진입 (리스트)
    public String freeBoardFirstList(Model model,
                                     @RequestParam(name = "page", defaultValue = "1") Integer page,
                                     @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
        // 여기서 @RequestParam에서 defaultValue를 안해주면, 쿼리파라미터가 안넘어올 경우 에러가 난다.
        // 근데 쿼리파라미터가 없으면 첫페이지로 지정하고 싶었기에, 위처럼 defaultValue = "-1" 로 했다

        // 1. 현재 몇 페이지인지, 한 페이지에 게시글 몇 개 보여줄건지 정보 담음
        PageVO pagination = new PageVO(page, pageSize);

        // 2. 하단에 페이지 버튼 표시 관련 모델 데이터 생성
        int lastPage = boardService.getLastPage(pageSize);
        int[] prevPageList = boardService.getPrevPageList(page);
        int[] nextPageList = boardService.getNextPageList(page, lastPage);

        // 3. 페이지네이션 객체에 담긴 정보를 토대로, 다음
        List<Post> postList = boardService.getPostList(pagination);

        // 페이지에 맞는 글 목록들을 담아서 넘겨줌
        model.addAttribute("postList", postList);

        // 페이징과 관련된 정보들을 담아서 넘겨줌
        model.addAttribute("pagination", pagination);
        model.addAttribute("prevPageList", prevPageList);
        model.addAttribute("nextPageList", nextPageList);

        return ViewPathConst.FREEBOARD_LIST;
    }

    @GetMapping("free/{postId}") // postId 글 페이지
    public String freeBoardReadPost(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_POST;
    }

    @GetMapping("free/write/{postId}") // postId 글 수정 페이지
    public String freeBoardWritePost(@PathVariable long postId, Model model) {
        Post post = boardService.getPost(postId);
        model.addAttribute("post", post);

        return ViewPathConst.FREEBOARD_UPDATE;
    }

    @GetMapping("free/write") // 새 글 작성 페이지
    public String freeBoardWritePost(Model model) {
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
