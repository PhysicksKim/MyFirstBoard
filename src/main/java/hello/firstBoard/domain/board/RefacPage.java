package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RefacPage {

    protected int page;
    protected int pageSize;

    public RefacPage(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
