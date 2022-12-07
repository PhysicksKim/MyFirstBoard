package hello.firstBoard.domain.board;

import lombok.Data;

@Data
public class RefacRequestPageDTO extends RefacPage{
    /**
     Request에서는 파라미터로 nowPage, pageSize 만 넘어옴
     */

    public RefacRequestPageDTO(int nowPage, int pageSize) {
        super(nowPage, pageSize);
    }
}
