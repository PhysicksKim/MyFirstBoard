package hello.firstBoard.domain.board.Posts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Post {
    // SQL table names : ID TITLE WRITER CONTENT DATE

    @EqualsAndHashCode.Include private long id;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime date;
    private int hit;

    public Post(PostWrite postWrite) {
        this.title = postWrite.getTitle();
        this.writer = postWrite.getWriter();
        this.content = postWrite.getContent();
    }
}
