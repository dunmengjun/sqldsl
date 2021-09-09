import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "user")
public class User {
    @Column
    private int id;

    @Column
    private String name;

    @Column
    private Integer age;
}
