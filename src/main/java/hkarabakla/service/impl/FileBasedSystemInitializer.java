package hkarabakla.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hkarabakla.constants.SystemConstants;
import hkarabakla.enums.PokemonType;
import hkarabakla.model.AttackReferance;
import hkarabakla.model.LastSession;
import hkarabakla.model.Pokemon;
import hkarabakla.service.Printer;
import hkarabakla.service.SystemInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileBasedSystemInitializer implements SystemInitializer {

    private Printer printer;
    private ObjectMapper objectMapper;

    private static FileBasedSystemInitializer fileBasedSystemInitializer;

    private FileBasedSystemInitializer() {
    }

    private FileBasedSystemInitializer(Printer printer) {
        this.printer = printer;
        this.objectMapper = new ObjectMapper();
    }

    public static FileBasedSystemInitializer getInstance(Printer printer) {

        if(!Optional.ofNullable(fileBasedSystemInitializer).isPresent()) {
            fileBasedSystemInitializer = new FileBasedSystemInitializer(printer);
        }

        return fileBasedSystemInitializer;
    }

    /**
     * Loads pokemons from the file system
     * @return List<Pokemon>
     */
    @Override
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        try {
            String pokemonJson = new String(Files.readAllBytes(Paths.get("src/main/resources/pokemons.json")));
            pokemons = objectMapper.readValue(pokemonJson, new TypeReference<List<Pokemon>>() {
            });
        } catch (IOException e) {
            printer.println(SystemConstants.POKEMON_FILE_CANNOT_READ, SystemConstants.FIVE_SECONDS, e);
            System.exit(0);
        }

        return pokemons;
    }

    /**
     * Loads pokemons interactions from the file system
     * @return
     */
    @Override
    public Map<PokemonType, Map<PokemonType, AttackReferance>> getAttackChart() {

        Map<PokemonType, Map<PokemonType, AttackReferance>> attackChart = new HashMap<>();

        try {
            String attackJson = new String(Files.readAllBytes(Paths.get("src/main/resources/power_of_types.json")));
            List<AttackReferance> attackReferances = objectMapper.readValue(attackJson, new TypeReference<List<AttackReferance>>() {
            });
            attackReferances.forEach(attackReferance -> {
                if (attackChart.containsKey(attackReferance.getOffensive())) {
                    attackChart.get(attackReferance.getOffensive()).put(attackReferance.getDefensive(), attackReferance);
                } else {
                    Map<PokemonType, AttackReferance> value = new HashMap<>();
                    value.put(attackReferance.getDefensive(), attackReferance);
                    attackChart.put(attackReferance.getOffensive(), value);
                }
            });

        } catch (IOException e) {
            printer.println(SystemConstants.POKEMON_TYPES_FILE_CANNOT_READ, SystemConstants.FIVE_SECONDS, e);
            System.exit(0);
        }

        return attackChart;
    }

    /**
     * Saves last session to the file system
     * @param lastSession LastSession
     */
    @Override
    public void saveCurrentSession(LastSession lastSession) {
        try {
            String lastSessionStr = objectMapper.writeValueAsString(lastSession);
            Files.write(Paths.get("src/main/resources/last_session.json"), lastSessionStr.getBytes());
        } catch (IOException e) {
            printer.println(SystemConstants.SESSION_FILE_CANNOT_SAVED, SystemConstants.FIVE_SECONDS, e);
            System.exit(0);
        }
    }

    /**
     * Loads the last session from the file system
     * @return LastSession
     */
    @Override
    public LastSession loadLastSession() {

        LastSession lastSession = null;
        try {
            String lastSessionStr = new String(Files.readAllBytes(Paths.get("src/main/resources/last_session.json")));
            lastSession = objectMapper.readValue(lastSessionStr, LastSession.class);
        } catch (IOException e) {
            printer.println(SystemConstants.SESSION_FILE_CANNOT_READ, SystemConstants.FIVE_SECONDS, e);
            System.exit(0);
        }

        return lastSession;
    }
}
