package hello.firstBoard.domain.board.Pages;

@Deprecated
public class SearchViewDTO extends PageViewDTO {

    String searchKeyword;
    SearchType searchType;

    public SearchViewDTO(int nowPage, int pageSize, int lastPage, String searchKeyword, SearchType searchType) {
        super(nowPage, pageSize, lastPage);
        this.searchKeyword = searchKeyword;
        this.searchType = searchType;
    }

    public SearchViewDTO(SearchRequestDTO dto, int lastPage) {
        this(dto.page, dto.pageSize, lastPage, dto.getSearchKeyword(), dto.getSearchType());
    }
}
