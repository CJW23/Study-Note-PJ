package com.cjw.study.jpa.lock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Version
    private Integer version;

    private long count;

    @OneToMany(mappedBy = "lockParent", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LockChild> lockChildren;

    public static LockParent ofDefault() {
        return LockParent.builder().count(0L).build();
    }

    public void plusCount() {
        this.count += 1;
    }
}
