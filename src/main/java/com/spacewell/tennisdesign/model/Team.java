package com.spacewell.tennisdesign.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @NonNull
    private String teamName;
    /*a team can contain single player or 2 players*/
    private List<String> playerNames;
    private String pointsScored;
    private boolean serving;
}
