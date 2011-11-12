package com.basinc.golfminus.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.basinc.golfminus.enums.State;
import com.basinc.golfminus.enums.TeeType;
import com.basinc.golfminus.enums.TournamentType;

public class Factories {

    @Produces
    @Named
    @ConversationScoped
    public List<TeeType> getTeeTypes() {
        return new ArrayList<TeeType>(Arrays.asList(TeeType.values()));
    }

    @Produces
    @Named
    @ConversationScoped
    public List<State> getStates() {
        return new ArrayList<State>(Arrays.asList(State.values()));
    }

    @Produces
    @Named
    @ConversationScoped
    public List<TournamentType> getTournamentTypes() {
        return new ArrayList<TournamentType>(Arrays.asList(TournamentType.values()));
    }

}
