package com.ikvashnin.bot.citysearcher;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class City {
    String code;
    String name;
    Cases cases;
}