package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
/**
 * nowPage : 현재 페이지
 * startPost : 해당 페이지의 첫 포스트 id
 * pageSize : 페이지마다 표시할 포스트 수
 */
public class Pagination {

    private int nowPage;
    private int pageSize;

    private int startPost;

    public Pagination(int page, int pageSize) {
        this.nowPage = page;
        this.pageSize = pageSize;

        this.startPost = (page-1)*pageSize;
    }
}

/*
시작번호 : (페이지-1)*사이즈 + 1
    ex1. 1page : (1-1)*10 + 1 = 1
    ex2. 2page : (2-1)*10 + 1 = 11

끝 번호 : 페이지*사이즈 or 시작번호+사이즈-1
*/


