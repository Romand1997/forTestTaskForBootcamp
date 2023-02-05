package test_task_for_bootcamp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import test_task_for_bootcamp.BaseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table (name = "t_items")
@Getter
@Setter
public class Item extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne (fetch = FetchType.LAZY)
    private Category category;

    @Column (name = "description")
    private String description;
    @Column (name = "addingDateTime")
    private LocalDateTime addingDateTime;

    @Column (name = "timeOfLastBet")
    private LocalDateTime timeOfLastBet;

    @Column (name = "price")
    private double price;

    @Column (name = "isActive")
    private Boolean isActive;
    @ManyToOne (fetch = FetchType.LAZY)
    private User adOwner;
    @ManyToOne (fetch = FetchType.LAZY)
    private User lastBuyer;
    @Column(name = "timeOfSale")
    private int timeOfSale;
}
