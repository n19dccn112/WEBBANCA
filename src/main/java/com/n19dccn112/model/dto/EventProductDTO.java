package n19dccn112.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventProductDTO {
    private Long eventId;
    private Long productId;
    private Date createDate;
    private Date updateDate;
}
