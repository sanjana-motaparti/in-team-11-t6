package com.dbtraining.reconx.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "counterparties")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Counterparty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
}

