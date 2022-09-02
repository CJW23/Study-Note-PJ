package com.cjw.study.jpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")	//컬럼 이름 지정, 지정 안할시 변수(id)값으로 지정
	private Long id;
	private String username;
	private int age;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "team_id")
	private Team team;

	private void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}

	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		if(team!=null) {
			changeTeam(team);
		}
	}

	public Member(String username, int age) {
		this(username, age, null);
	}

	public Member(String username) {
		this(username, 0);
	}
}
