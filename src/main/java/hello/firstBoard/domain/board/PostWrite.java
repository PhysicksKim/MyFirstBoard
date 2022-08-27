package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;


@Data
public class PostWrite {

    private String title;
    private String writer;
    private String content;
}
