package com.seven.entity;

/**
 * @author: seven
 * @since: 2024/7/11 13:26
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_project_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TProjectType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer projectid;

    private Integer typeid;

}