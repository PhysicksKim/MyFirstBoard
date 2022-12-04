package hello.firstBoard.domain.board;

import lombok.Data;

@Data
public class SearchDTO {

    private SearchType searchType;
    private String searchKeyword;

}
