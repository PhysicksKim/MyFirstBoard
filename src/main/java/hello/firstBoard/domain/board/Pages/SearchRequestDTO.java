package hello.firstBoard.domain.board.Pages;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class SearchRequestDTO extends PageRequestDTO{

    private SearchType searchType;
    private String searchKeyword;

}
