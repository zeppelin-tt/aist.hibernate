package aist.demo.dto.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTrigger {

    public static final String CLASS = "aist.demo.dto.json.JobTrigger";

    private String jobUrl;
    private String passOrToken;
    private String userLogin;

}
