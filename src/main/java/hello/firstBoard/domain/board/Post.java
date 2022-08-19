package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    // ID TITLE WRITER CONTENT DATE

    @EqualsAndHashCode.Include private long id;
    private String title;
    private String writer;
    private String content;
    private String date; // 이걸 java.util.Date를 써야할지 생각해봐야겠음

}
