package hello.firstBoard.controller.board.freeBoard;

import hello.firstBoard.consts.ViewPathConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class freeBoardController {

    // 게시글은
    // 글 번호 | 글 제목 | 작성자 | 작성일 로 보여줌

    @GetMapping("/board/free")
    public String freeBoardList(Model model) {



        return ViewPathConst.FREEBOARD_PAGE;
    }
}
