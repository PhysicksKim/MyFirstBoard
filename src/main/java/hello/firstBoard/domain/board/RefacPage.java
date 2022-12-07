package hello.firstBoard.domain.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class RefacPage {

    protected int nowPage;
    protected int pageSize;

    protected int offsetPostCount;

    public RefacPage(int nowPage, int pageSize) {
        this.nowPage = nowPage;
        this.pageSize = pageSize;

        this.offsetPostCount = (nowPage-1)*pageSize;
    }
}
