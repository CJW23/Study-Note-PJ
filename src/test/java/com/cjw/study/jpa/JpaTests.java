package com.cjw.study.jpa;

import com.cjw.study.jpa.entity.Member;
import com.cjw.study.jpa.entity.QMember;
import com.cjw.study.jpa.entity.QTeam;
import com.cjw.study.jpa.entity.Team;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JpaTests {
    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @Test
    @Transactional
    @Rollback(value = false)
    void 테스트_데이터() {
        for(int i=0; i<10; i++) {
            UUID uuid = UUID.randomUUID();
            Team team = new Team("team"+uuid.toString());
            for(int j=0; j<5; j++) {
                new Member("member"+uuid.toString(),j, team);
            }
            em.persist(team);
        }
    }

    /**
     *    select
     *         member0_.member_id as member_i1_0_,
     *         member0_.age as age2_0_,
     *         member0_.team_id as team_id4_0_,
     *         member0_.username as username3_0_
     *     from
     *         member member0_
     *     inner join
     *         team team1_
     *             on member0_.team_id=team1_.member_id
     *     쿼리가 나가지만 team에는 프록시 객체가 들어있음(데이터를 같이 안가져옴)
     */
    @Test
    void 일반_조인_테스트() {
        queryFactory = new JPAQueryFactory(em);
        QMember member = QMember.member;
        QTeam team= QTeam.team;
        List<Member> fetch = queryFactory
                .select(member)
                .from(member)
                .join(member.team, team).fetch();
        assertNotNull(fetch);
    }

    /**
     *     select
     *         team0_.member_id as member_i1_1_0_,
     *         members1_.member_id as member_i1_0_1_,
     *         team0_.name as name2_1_0_,
     *         members1_.age as age2_0_1_,
     *         members1_.team_id as team_id4_0_1_,
     *         members1_.username as username3_0_1_,
     *         members1_.team_id as team_id4_0_0__,
     *         members1_.member_id as member_i1_0_0__
     *     from
     *         team team0_
     *     inner join
     *         member members1_
     *             on team0_.member_id=members1_.team_id
     *     team을 불러왔지만 일대다인 member를 조인했기에 member수만큼 team이 중복됨 (team에 여러개의 member가 존재하기에)
     */
    @Test
    void 일대다_조인_테스트() {
        queryFactory = new JPAQueryFactory(em);
        QTeam team= QTeam.team;
        List<Team> fetch = queryFactory
                .select(team)
                .from(team)
                .join(team.members).fetchJoin()
                .fetch();
        assertNotNull(fetch);
    }

    /**
     *     select
     *         team0_.member_id as member_i1_1_,
     *         team0_.name as name2_1_
     *     from
     *         team team0_
     *
     *     이후 Team을 Loop에서 member 호출
     *     Team 엔티티 개수만큼 select 호출 -> N(Team엔티티 개수)+1(select Team) 문제 발생
     *
     */
    @Test
    @Transactional(readOnly = true)
    void N플러스1_테스트() {
        queryFactory = new JPAQueryFactory(em);
        QTeam team= QTeam.team;
        List<Team> fetch = queryFactory
                .select(team)
                .from(team)
                .fetch();

        for (Team fetch1 : fetch) {
            List<Member> members = fetch1.getMembers();
            members.forEach(m -> System.out.println(m.getAge()));
        }

        assertNotNull(fetch);
    }

    /**
     * batch_size = 2인 경우
     * teams 특정 index의 member를 호출시 다음 index의 members도 호출한다
     * 하지만 for를 사용하지않고 2씩 더하며 직접 index를 지정해 호출하면 batch로 가져오지 않았기에 호출마다 쿼리가 나가게된다
     */
    @Test
    @Transactional(readOnly = true)
    void batch_size_테스트() {
        queryFactory = new JPAQueryFactory(em);
        QTeam team= QTeam.team;
        List<Team> teams = queryFactory
                .select(team)
                .from(team)
                .fetch();
        //IN (0,1) 쿼리
        List<Member> members1 = teams.get(0).getMembers();
        System.out.println(members1);

        //IN (2,3) 쿼리
        List<Member> members2 = teams.get(2).getMembers();
        System.out.println(members2);

        //IN (4,5) 쿼리
        List<Member> members3 = teams.get(4).getMembers();
        System.out.println(members3);

        //IN (6,7) 쿼리
        List<Member> members4 = teams.get(6).getMembers();
        System.out.println(members4);

        assertNotNull(teams);
    }
}
