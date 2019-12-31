package projects.todolistapp.core.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "todo_item")
@EqualsAndHashCode(of = "id")
public class TodoItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
/*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
*/
    @Column(name = "title")
    private String title;

    @Column(name = "details")
    private String details;

    @Column(name = "deadline")
    private LocalDate deadline;

    public TodoItem() {}

    public TodoItem(String title, String details, LocalDate deadline) {
        this.title = title;
        this.details = details;
        this.deadline = deadline;
    }
}