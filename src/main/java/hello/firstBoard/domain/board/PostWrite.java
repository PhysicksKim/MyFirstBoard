package hello.firstBoard.domain.board;

import lombok.Data;

@Data
public class PostWrite {

    private String title;
    private String writer;
    private String content;
}
