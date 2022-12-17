package hello.firstBoard.service.board;

import hello.firstBoard.domain.board.Pages.*;
import hello.firstBoard.domain.board.Posts.Post;
import hello.firstBoard.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<Post> getPostList(PageRequestDTO requestPageDTO) {
        PageDAO refacPageDAO = new PageDAO(requestPageDTO);
        List<Post> postList = boardRepository.getPostList(refacPageDAO);
        return postList;
    }

    public PageViewDTO getPageViewDTO(PageRequestDTO requestPageDTO) {
        int lastPage = getLastPage(requestPageDTO);
        PageViewDTO refacPageViewDTO = new PageViewDTO(requestPageDTO, lastPage);
        return refacPageViewDTO;
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

    public int getLastPage(PageRequestDTO requestPageDTO) {
        return getLastPage(requestPageDTO.getPageSize());
    }

    public List<Post> getSearchList(SearchRequestDTO searchRequestDTO) {
        SearchDAO searchDAO = new SearchDAO(searchRequestDTO);
        List<Post> postSearchList = boardRepository.getPostSearchList(searchDAO);
        log.info("searchDAO : {}", searchDAO);
        log.info("postSearchList : {}", postSearchList);
        return postSearchList;
    }

    public int getSearchLastPage(int pageSize, SearchDAO searchDAO) {
        // 만약 검색결과가 0이면 0을 리턴함
        return (int)Math.ceil(boardRepository.getSearchTotalPost(searchDAO)/(double)pageSize);
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
        int[] nextPageList = new int[nextPageLen > 0 ? nextPageLen : 0];

        // nowPage 다음 값부터 담아야 하니까 +1 해줌.
        // ex. 현재 3페이지면 4페이지부터 담아야하니까
        int tempPage = page+1;
        for(int i = 0 ; i<nextPageLen ; i++)
            nextPageList[i] = tempPage++;

        return nextPageList;
    }

}
