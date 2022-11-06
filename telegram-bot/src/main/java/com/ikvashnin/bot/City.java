package com.ikvashnin.bot;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming()
public class City {
    String country_code;
    String code;
    Coordinates coordinates;
    String name;
    String time_zone;
    NameTranslations name_translations;
    Cases cases;
}