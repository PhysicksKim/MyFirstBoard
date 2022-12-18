package hello.firstBoard.domain.board.Pages;

import lombok.Data;

@Data
public class Search {

    private SearchType searchType;
    private String searchKeyword;

    public Search(SearchType searchType, String searchKeyword) {
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
    }
}
