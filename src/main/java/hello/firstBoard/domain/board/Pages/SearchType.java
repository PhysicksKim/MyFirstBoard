package hello.firstBoard.domain.board.Pages;

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
