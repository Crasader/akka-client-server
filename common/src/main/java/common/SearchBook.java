package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by hector on 25/05/2017.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
}
