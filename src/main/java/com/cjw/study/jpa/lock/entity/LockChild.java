package com.cjw.study.jpa.lock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockChild {
    @Id
    @GeneratedValue
    @Column(name = "child_id", nullable = false)
    private Long childId;

    @Version
    private Integer version;

    private Long count;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private LockParent lockParent;

    public void setLockParent(LockParent lockParent) {
        this.lockParent = lockParent;
    }

    public static LockChild ofDefault() {
        return LockChild.builder()
                .count(0L)
                .build();
    }
}
