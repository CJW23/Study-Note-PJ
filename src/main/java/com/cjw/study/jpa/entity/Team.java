package com.cjw.study.jpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    //orphanRemoval: 부모 엔티티(Team)와 연관관계가 끊긴 자식 엔티티(Member)을 자동으로 삭제
    //orphanRemoval vs CascadeType.REMOVE : member의 연관관계를 끊으면(ex: members.remove(0))
    //CasecadeType.REMOVE: 자식 엔티티의 foriegnKey null처리, orphanRemoval는 자식 엔티티 자체를 제거
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
