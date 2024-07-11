package com.seven.entity;

/**
 * @author: seven
 * @since: 2024/7/11 10:48
 */
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "t_member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "loginacct")
    private String loginacct;
    @Column
    private String userpswd;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String authstatus;
    @Column
    private String usertype;
    @Column
    private String realname;
    @Column
    private String cardnum;
    @Column
    private String accttype;
}