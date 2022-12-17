package hello.firstBoard.domain.board.Pages;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) // toString() 에서 부모 요소도 출력되도록 함
public class PageRequestDTO extends Page {
    /**
     Request에서는 파라미터로 nowPage, pageSize 만 넘어옴
     */

    public PageRequestDTO(int nowPage, int pageSize) {
        super(nowPage, pageSize);
    }

    public PageRequestDTO() {
        super(1, 5); // default 값
    }
}
