package ${requestPackage};

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ${entity}Request {


    @Data
    public static class SaveOrUpdate {
        private String id;
    }
    @Data
    public static class SwitchStatus {
        @NotBlank
        private String id;
        @NotBlank
        private String status;
    }
}
