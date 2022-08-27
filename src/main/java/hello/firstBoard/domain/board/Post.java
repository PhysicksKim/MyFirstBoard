package hello.firstBoard.domain.board;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Post {

    // ID TITLE WRITER CONTENT DATE

    @EqualsAndHashCode.Include private long id;
    private String title;
    private String writer;
    private String content;
    private Date date;

    public Post(PostWrite postWrite) {
        this.title = postWrite.getTitle();
        this.writer = postWrite.getWriter();
        this.content = postWrite.getContent();
    }
}
