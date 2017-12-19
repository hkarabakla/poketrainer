package hkarabakla.service;


import hkarabakla.enums.PokemonType;
import hkarabakla.model.AttackReferance;
import hkarabakla.model.LastSession;
import hkarabakla.model.Pokemon;

import java.util.List;
import java.util.Map;

public interface SystemInitializer {

    /**
     * Loads all the pokemons from specific system
     * @return List<Pokemon>
     */
    List<Pokemon> getAllPokemons();

    /**
     * Loads all the pokemons interactions from specific system
     * @return
     */
    Map<PokemonType, Map<PokemonType, AttackReferance>> getAttackChart();

    /**
     * Saves the last session to the specific system
     * @param lastSession LastSession
     */
    void saveCurrentSession(LastSession lastSession);

    /**
     * Saves the last session to the specific system
     * @return LastSession
     */
    LastSession loadLastSession();
}
