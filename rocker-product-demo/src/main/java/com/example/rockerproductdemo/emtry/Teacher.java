package com.example.rockerproductdemo.emtry;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author AGbetrayal
 * @date 2020/5/4 14:51
 */
@EqualsAndHashCode
@ToString(exclude = "students")
@Entity
@Table(name = "t_teacher")
@Data
public class Teacher {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    // @ManyToMany注释表示Teacher是多对多关系的一端。
    // @JoinTable描述了多对多关系的数据表关系。name属性指定中间表名称，joinColumns定义中间表与Teacher表的外键关系。
    // 中间表Teacher_Student的Teacher_ID列是Teacher表的主键列对应的外键列，inverseJoinColumns属性定义了中间表与另外一端(Student)的外键关系。
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "t_teacher_student", joinColumns = { @JoinColumn(name = "teacher_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "student_id") })
//    private Set<Student> students = new HashSet<Student>();

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    /*@Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }*/
}
