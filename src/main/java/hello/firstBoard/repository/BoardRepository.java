package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Page;
import hello.firstBoard.domain.board.Post;
import hello.firstBoard.domain.board.RefacPageDAO;
import hello.firstBoard.domain.board.SearchDAO;

import java.util.List;

// ID TITLE WRITER CONTENT DATE HIT DELETEFLAG
public interface BoardRepository {

    // 글 목록, 글 작성, 글 삭제, 글 수정 등을 담당함.
    // 차후에 게시판 번호로 게시판 구분 예정

    // Create
    Post save(Post post);

    // Read
    Post getPost(long postId);
    List<Post> getPostList(Page pagination);

    List<Post> getPostList(RefacPageDAO refacPageDAO);

    // Read (Search)
    List<Post> getPostSearchList(SearchDAO searchDAO);
    // SELECT * FROM BOARD WHERE DELETEFLAG=FALSE and WRITER LIKE CONCAT('%','test','%');
    int getSearchTotalPost(SearchDAO searchDAO);

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
