package com.cjw.study.jpa.lock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    /*@Version
    private Integer version;*/

    private long count;

    @OneToMany(mappedBy = "lockParent", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LockChild> lockChildren = new ArrayList<>();

    public static LockParent ofDefault() {
        return LockParent.builder().count(0L).build();
    }

    public void addLockChild(LockChild lockChild) {
        this.lockChildren.add(lockChild);
        lockChild.setLockParent(this);
    }
    public void plusCount() {
        this.count += 1;
    }
}
