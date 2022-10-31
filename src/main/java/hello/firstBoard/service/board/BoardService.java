package hello.firstBoard.service.board;

import hello.firstBoard.domain.board.Pagination;
import hello.firstBoard.domain.board.Post;
import hello.firstBoard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Post savePost(Post post) {
        return boardRepository.save(post);
    }

    public List<Post> getPostList(Pagination pagination) {
        return boardRepository.getPostList(pagination);
    }

    public Post getPost(long postId) {
        boardRepository.plusHit(postId);
        return boardRepository.getPost(postId);
    }

    public void updatePost(Post post) {
        boardRepository.update(post);
    }

    public void deletePost(long id) {
        boardRepository.delete(id);
    }

    public int getLastPage(int pageSize) {
        return boardRepository.getLastPage(pageSize);
    }
}
