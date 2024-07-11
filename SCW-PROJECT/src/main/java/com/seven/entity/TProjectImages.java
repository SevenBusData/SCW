package com.seven.entity;

/**
 * @author: seven
 * @since: 2024/7/11 13:25
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_project_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TProjectImages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer projectid;

    private String imgurl;

    private Integer imgtype;

    private static final long serialVersionUID = 1L;


}