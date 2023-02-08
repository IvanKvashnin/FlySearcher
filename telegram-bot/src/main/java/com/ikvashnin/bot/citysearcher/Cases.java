package com.ikvashnin.bot.citysearcher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cases {
    String su;
    String da;
    String pr;
    String ro;
    String tv;
    String vi;
}
