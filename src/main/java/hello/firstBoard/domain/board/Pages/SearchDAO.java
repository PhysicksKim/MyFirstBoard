package hello.firstBoard.domain.board.Pages;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SearchDAO extends PageDAO {

    private String searchKeyword;
    private SearchType searchType;


    public SearchDAO(int nowPage, int pageSize, String searchKeyword, SearchType searchType) {
        super(nowPage, pageSize);
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
    }

    public SearchDAO(SearchRequestDTO searchRequestDTO) {
        this(   searchRequestDTO.getPage(),
                searchRequestDTO.getPageSize(),
                searchRequestDTO.getSearchKeyword(),
                searchRequestDTO.getSearchType());
    }


    public SearchDAO(String searchKeyword, SearchType searchType) {
        super(1,5);
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
    }

}
