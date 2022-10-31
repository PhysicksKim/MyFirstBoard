package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
/**
 * 게시판 아래에 출력해야하는 페이지 목록들
 */
public class Pagination {

    private int nowPage;
    private int startPost;
    private int pageSize;

    public Pagination() {}

    public Pagination(int page, int pageSize) {
        this.pageSize = pageSize;

        nowPage = page;
        startPost   = (page-1)*pageSize;
    }

}
/*
시작번호 : (페이지-1)*사이즈 + 1
    ex1. 1page : (1-1)*10 + 1 = 1
    ex2. 2page : (2-1)*10 + 1 = 11

끝 번호 : 페이지*사이즈 or 시작번호+사이즈-1
*/


