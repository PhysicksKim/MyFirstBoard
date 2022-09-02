package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Post;

import java.util.List;

public interface BoardRepository {

    // 글 목록, 글 작성, 글 삭제, 글 수정 등을 담당함.
    // 차후에 게시판 번호로 게시판 구분 예정

    // Create
    Post save(Post post);

    // Read
    Post getPost(long postId);
    List<Post> getPostList(); // 페이징 기능을 구현하려면, 페이지당 글 수, 페이지 번호 등을 받아와야하는데 이건 나중에 구현

    // Update
    void update(Post post);
    void update(long postId);

    // Delete
    void delete(Post post);
    void delete(long postId);

}
