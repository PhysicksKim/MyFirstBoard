package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Pagination {
    private int nowPage;
    private int lastPage;

    private int[] prevPages;
    private int[] nextPages;

    private int pageSize = 5;

    public void setFirstPage(){
        this.nowPage = 1;
        this.prevPages = new int[0];

        this.nextPages = new int[pageSize];
        for(int i = 0 ; i<this.nextPages.length ; i++) {
            nextPages[i] = i+1;
        }
    }

}
