package hello.firstBoard.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {
    TITLE,
    CONTENT,
    TITLEORCONTENT,
    WRITER
}
