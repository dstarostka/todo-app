package projects.todolistapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.RepresentationModel;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "todo_item")
@EqualsAndHashCode(of = "id")
public class TodoItem extends RepresentationModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "details")
    private String details;

    @Column(name = "deadline")
    private LocalDate deadline;

    public TodoItem() {}

    @Builder
    public TodoItem(int id, String title, String details, LocalDate deadline) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.deadline = deadline;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}