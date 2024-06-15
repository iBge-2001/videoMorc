package com.show.pojo;

import javax.persistence.*;
@Entity
@Table  (name = "conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private Users user1;
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private Users user2;
    // other fields and getters/setters
}
