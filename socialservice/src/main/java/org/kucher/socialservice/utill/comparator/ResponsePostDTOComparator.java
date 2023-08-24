package org.kucher.socialservice.utill.comparator;

import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;

/**
 * A custom comparator for comparing ResponsePostDTO objects based on their creation dates.
 */
@Component
public class ResponsePostDTOComparator implements Comparator<ResponsePostDTO> {

    /**
     * Compares two ResponsePostDTO objects based on their creation dates.
     *
     * @param responsePostDTO1 The first ResponsePostDTO object to compare.
     * @param responsePostDTO2 The second ResponsePostDTO object to compare.
     * @return A negative integer, zero, or a positive integer as the first ResponsePostDTO's
     *         creation date is less than, equal to, or greater than the second ResponsePostDTO's
     *         creation date.
     */
    @Override
    public int compare(ResponsePostDTO responsePostDTO1, ResponsePostDTO responsePostDTO2) {
        // Compare based on the dtCreate field (creation timestamp)
        return responsePostDTO1.getDtCreate().compareTo(responsePostDTO2.getDtCreate());
    }
}
