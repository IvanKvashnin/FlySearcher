package com.ikvashnin.bot.citysearcher;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class City {
    String code;
    String name;

    Cases cases;

    public boolean isCityName(String name) {
        if (cases == null) {
            return false;
        }
        List<String> names = Arrays.asList(this.name, cases.da, cases.pr, cases.ro, cases.su, cases.tv, cases.vi);
        return names.contains(name);
    }
}