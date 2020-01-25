package com.hillel.evo.adviser.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "history_point")
@NoArgsConstructor
@Data
@Transactional
@EqualsAndHashCode(of = {"id"})
public class HistoryPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long userId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "point_with_business")
    List<Business> business;
    @NotNull
    private LocalDateTime searchDate;
}
