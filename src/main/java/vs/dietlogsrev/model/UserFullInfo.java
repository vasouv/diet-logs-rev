package vs.dietlogsrev.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFullInfo {
    
    private String username;
    private String email;
    private UserNeededInfo info;
    private List<MeasurementResponse> measurements;

}
