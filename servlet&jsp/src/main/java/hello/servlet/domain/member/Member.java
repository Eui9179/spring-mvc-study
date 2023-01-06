package hello.servlet.domain.member;

import lombok.Getter;
import lombok.Setter;

// 생성자 생성: alt + insert
@Getter @Setter
public class Member {

    private Long id;
    private String username;
    private int age;

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
