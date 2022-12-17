package hello.firstBoard.domain.board.Pages;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 상속하는 SearchDAO 에서 @Data 에서 Lombok needs a default constructor 에러를 띄우기 때문에 추가
public class PageDAO extends Page {
    /**
     * DB access 를 위해서는
     * startPost, pageSize 필요함
     *
     * DB에서 데이터 받아오는거는 List<Post> 이므로
     * Entity 클래스는 Post를 이용함
     */

    private int offsetPostCount;

    public PageDAO(int nowPage, int pageSize) {
        super(nowPage, pageSize);

        this.offsetPostCount = (page-1)*pageSize;
    }

    public PageDAO(PageRequestDTO requestPageDTO) {
        this(requestPageDTO.getPage(), requestPageDTO.getPageSize());
    }
}
