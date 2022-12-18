package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Pages.Search;
import hello.firstBoard.domain.board.Posts.Post;
import hello.firstBoard.domain.board.Pages.PageDAO;
import hello.firstBoard.domain.board.Pages.SearchDAO;

import java.util.List;

// ID TITLE WRITER CONTENT DATE HIT DELETEFLAG
public interface BoardRepository {

    // 글 목록, 글 작성, 글 삭제, 글 수정 등을 담당함.
    // 차후에 게시판 번호로 게시판 구분 예정

    // Create
    Post save(Post post);

    // Read
    Post getPost(long postId);
    /*
    List<Post> getPostList(Page pagination);
    */
    List<Post> getPostList(PageDAO refacPageDAO);

    // Read (Search)
    List<Post> getPostSearchList(Search search, PageDAO pageDAO);
    // SELECT * FROM BOARD WHERE DELETEFLAG=FALSE and WRITER LIKE CONCAT('%','test','%');
    int getSearchTotalPost(Search search);

    // Update
    void update(Post post);
    void update(long postId);

    // Delete
    void delete(Post post);
    void delete(long postId);

    void plusHit(long postId);


    // Pagination
    int getTotalPost();

}
