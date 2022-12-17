package hello.firstBoard.domain.board.Pages;

import lombok.Data;

@Data
public class PageViewDTO extends Page {
    /**
     * ViewPage 에서는
     * nowPage , prevPageList , nextPageList 들어감
     * 생성을 위해 nowPage, pageSize, lastPage 필요
     */

    private static int pageButtons = 4; // 게시판 하단에 페이지 버튼 몇 개 보여줄지

    private int[] prevPageList;
    private int[] nextPageList;

    public PageViewDTO(int nowPage, int pageSize, int lastPage) {
        super(nowPage, pageSize);
        prevPageList = calculPrevPageList(nowPage);
        nextPageList = calculNextPageList(nowPage, lastPage); // lastPage 필요해서 DB 이후 호출되어야함
    }

    public PageViewDTO(PageRequestDTO requestPageDTO, int lastPage) {
        this(requestPageDTO.getPage(), requestPageDTO.getPageSize(), lastPage);
    }

    private int[] calculPrevPageList(int page) {
        int prevPageStart = page - pageButtons < 1 ? 1 : page - pageButtons;
        int prevPageLen = page - prevPageStart;
        int[] prevPageList = new int[prevPageLen];

        int tempPage = prevPageStart;
        for(int i = 0 ; i<prevPageLen ; i++)
            prevPageList[i] = tempPage++;

        return prevPageList;
    }

    private int[] calculNextPageList(int page, int lastPage) {
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
