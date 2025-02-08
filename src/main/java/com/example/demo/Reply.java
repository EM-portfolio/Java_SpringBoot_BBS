package com.example.demo;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="Reply")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    private String createDate;
    @Column
    private String createUser;
    @Column
    private String replyMessage;
    // 外部キー設定 
    @ManyToOne
    @JoinColumn(name = "BulletinBoad_id")
    private BulletinBoad parentBoad;
}
