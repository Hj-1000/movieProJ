package com.hj1000.movieinfo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "movie")
public class MovieEntity {
    @Id // 생략불가능
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer code;   //일련번호
    private String name;    //영화제목
    private String director;    // 감독
    private String nation;   //국가
    private String genre;   //장르
    private String actor;   //주연배우
    private String opendate; // 개봉일
    private String synopsis; // 영화개요
    private String poster;    // 영화포스터
    //@column 생략시 변수명과 동일한 이름으로 자동 적용, 길이 생략시 기본값으로 자동지정
}
/* 작업후
1. 프로그램(서버) 실행
2. 브라우저에서 localhost:8080/h2-console
3. url 은 application.properties에 datasource.url 주소를 입력
    spring.datasource.url = (jdbc:h2:~/db/moviedb) ;MODE = MySQL
        @Table(name="(movie)")
*/
