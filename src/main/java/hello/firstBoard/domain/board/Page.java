package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor // 자식인 Search가 Lombok에서 super() 생성자를 사용하므로 NoArgsConstructor가 필요하다
/**
 * nowPage : 현재 페이지
 * startPost : 해당 페이지의 첫 포스트 id
 * pageSize : 페이지마다 표시할 포스트 수
 */
public class Page {

    protected int nowPage;
    protected int pageSize;

    // startPost 의 id가 아니라, 얼마나 오프셋된 포스트부터 보여줄건가를 뜻함
    protected int startPost;

    public Page(int page, int pageSize) {
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


