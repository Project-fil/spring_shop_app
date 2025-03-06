package com.github.ratel.payload.filter;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StatisticFilter {

    private Date fromDate;

    private Date toDate;

    private Set<Long> userIdSet;

}
