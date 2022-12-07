package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RefacPageDAO extends RefacPage{
    /**
     * DB access 를 위해서는
     * startPost, pageSize 필요함
     *
     * DB에서 데이터 받아오는거는 List<Post> 이므로
     * Entity 클래스는 Post를 이용함
     */

    public RefacPageDAO(int nowPage, int pageSize) {
        super(nowPage, pageSize);
    }
}
