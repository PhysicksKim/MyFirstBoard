package hello.firstBoard.service.board;

import hello.firstBoard.domain.board.Pagination;
import hello.firstBoard.domain.board.Post;
import hello.firstBoard.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private static int pageButtons = 4; // 게시판 하단에 페이지 버튼 몇 개 보여줄지

    public boolean checkPostValidation(Post post) {
        if (post.getWriter().trim().equals(""))
            return false;

        return true;
    }

    public Post savePost(Post post) {
        return boardRepository.save(post);
    }

    public List<Post> getPostList(Pagination pagination) {
        List<Post> postList = boardRepository.getPostList(pagination);
        log.info(postList.toString());
        return postList;
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
        return (int)Math.ceil(boardRepository.getTotalPost()/(double)pageSize);
    }

    public int[] getPrevPageList(int page) {
        int prevPageStart = page - pageButtons < 0 ? 1 : page - pageButtons;
        int prevPageLen = page - prevPageStart;
        int[] prevPageList = new int[prevPageLen];

        int tempPage = prevPageStart;
        for(int i = 0 ; i<prevPageLen ; i++)
            prevPageList[i] = tempPage++;

        return prevPageList;
    }

    public int[] getNextPageList(int page, int lastPage) {
        int nextPageEnd   = page + pageButtons > lastPage ? lastPage : page + pageButtons;
        int nextPageLen = nextPageEnd - page;
        int[] nextPageList = new int[nextPageLen];

        // nowPage 다음 값부터 담아야 하니까 +1 해줌.
        // ex. 현재 3페이지면 4페이지부터 담아야하니까
        int tempPage = page+1;
        for(int i = 0 ; i<nextPageLen ; i++)
            nextPageList[i] = tempPage++;

        return nextPageList;
    }

}
