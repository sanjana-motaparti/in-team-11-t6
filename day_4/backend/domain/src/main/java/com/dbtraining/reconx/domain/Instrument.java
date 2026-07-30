package com.dbtraining.reconx.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "instruments")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instrument {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
}