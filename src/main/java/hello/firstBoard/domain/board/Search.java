package hello.firstBoard.domain.board;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Search extends Page{

    private String searchKeyword;
    private SearchType searchType;

    public Search(String searchKeyword, SearchType searchType) {
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
    }

    public Search(int page, int pageSize, String searchKeyword, SearchType searchType) {
        super(page, pageSize);
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
    }

}
