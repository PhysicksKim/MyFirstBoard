package hello.firstBoard.domain.board;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SearchDAO extends Page{

    private String searchKeyword;
    private SearchType searchType;

    public SearchDAO(String searchKeyword, SearchType searchType) {
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
    }

    public SearchDAO(SearchDTO searchDTO) {
        super(1,5);
        this.searchKeyword  = searchDTO.getSearchKeyword();
        this.searchType     = searchDTO.getSearchType();
    }

}
