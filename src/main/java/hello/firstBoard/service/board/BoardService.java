package hello.firstBoard.service.board;

import hello.firstBoard.domain.board.Post;
import hello.firstBoard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Post savePost(Post post) {
        return boardRepository.save(post);
    }

}
