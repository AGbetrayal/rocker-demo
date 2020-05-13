package com.example.rockerproductdemo.emtry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author AGbetrayal
 * @date 2020/5/4 14:50
 */
@Entity
@Table(name = "t_student")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    @ManyToMany(mappedBy = "students")
//    private Set<Teacher> teachers = new HashSet<Teacher>();

    /*@Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", teachers=" + teachers.toString() +
                '}';
    }*/
}
