package hello.firstBoard.validator;

import hello.firstBoard.domain.board.Posts.PostWrite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class PostWriteValidator implements Validator {

    /**
     *  - VARCHAR 는 한글자식 저장하는 방식이므로, VARCHAR(N)이면 모두 다 N글자까지 저장가능하다
     *  ex. writer는 varchar(20)이므로
     *  01234567890123456789 (숫자 20개) 까지 가능하고
     *  영일이삼사오육칠팔구영일이삼사오육칠팔구 (한글 20개) 까지 가능하다
     * ID	        INTEGER(10)
     * TITLE	    VARCHAR(255)
     * WRITER	    VARCHAR(20)
     * CONTENT	    VARCHAR(8000)
     * DATE	        TIMESTAMP(26)
     * HIT	        INTEGER(10)
     * DELETEFLAG	BOOLEAN(1)
     * @param clazz the {@link Class} that this {@link Validator} is
     * being asked if it can {@link #validate(Object, Errors) validate}
     * @return
     */

    @Override
    public boolean supports(Class<?> clazz) {
        log.info("{} 클래스에 대해 support 검사 결과 {}",
                clazz.getName(), PostWrite.class.isAssignableFrom(clazz));
        return PostWrite.class.isAssignableFrom(clazz);
    }

    /**
     * post 조건
     * 1. title, content, writer 는 공백만 있어서는 안된다
     *  title : 제목을 입력하세요
     *  content : 내용을 입력하세요
     *  writer : 작성자를 입력하세요
     * 2. title은 255자를 넘어서는 안된다
     * 3. writer는 20자를 넘어서는 안된다
     * 4. content는 8000자를 넘어서는 안된다
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        PostWrite postWrite = (PostWrite) target;

        // 조건 1. 공백만 들어올 경우
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required", "디폴트 메세지, 필수 값입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "required", "디폴트 메세지, 필수 값입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "required", "디폴트 메세지, 필수 값입니다");

        // post 조건
        // 2. title은 255자를 넘어서는 안된다
        // 3. writer는 20자를 넘어서는 안된다
        // 4. content는 8000자를 넘어서는 안된다
        if (postWrite.getTitle() == null || postWrite.getTitle().length() > 255)
            errors.rejectValue("title","range", null);

        if (postWrite.getWriter() == null || postWrite.getWriter().length() > 20)
            errors.rejectValue("writer","range", null);

        if (postWrite.getContent() == null || postWrite.getContent().length() > 8000)
            errors.rejectValue("content","range", null);

    }
}
