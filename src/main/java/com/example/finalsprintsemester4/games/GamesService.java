package com.example.finalsprintsemester4.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GamesService {

    private final GamesRepository gamesRepository;

    @Autowired
    public GamesService(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    // GET logic
    public List<Games> getGames(){
        return gamesRepository.findAll();
    }

//    public List<Games> getGames() {
//        List<Games> returnGame = gamesRepository.findAll();
//        Games gamesToFind = GamesServiceHelpers.getGamesLogic(returnGame, Games);
//        gamesRepository.findAll(gamesToFind);
//    }

    public Optional<Games> getGamesById(Long id){
        return gamesRepository.findById(id);
    }

//    public Optional<Games> getGamesById(@PathVariable Long id) {
//        Optional<Games> returnGameById = gamesRepository.findById(id);
//        Games gamesToFindById = GamesServiceHelpers.getGamesLogicById(returnGameById, Games);
//        gamesRepository.findById(gamesToFindById);
//    }

    // POST logic
    public void addNewGame(Games games) {
        System.out.println("Successfully added new game!!!");
        gamesRepository.save(games);
    }

//    public void addNewGame(Games games) {
//        System.out.println("Successfully added new game!!!");
//        Games gamesToAdd = GamesServiceHelpers.updateGameLogic(games);
//        gamesRepository.save(gamesToAdd);
//    }


    // PUT logic
    @Transactional
    public void updateGame(@PathVariable String id, @RequestBody Games games, HttpServletResponse response) {
        Optional<Games> returnValue = gamesRepository.findById(Long.parseLong(id));
        Games gamesToUpdate;

        if (returnValue.isPresent()) {
            gamesToUpdate = returnValue.get();

            gamesToUpdate.setTitle(games.getTitle());
            gamesToUpdate.setDeveloper(games.getDeveloper());
            gamesToUpdate.setRelease_date(games.getRelease_date());

            gamesRepository.save(gamesToUpdate);
        } else {
            try {
                response.sendError(404, "Game with id: " + games.getId() + " not found.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    @Transactional
//    public void updateGame(@PathVariable String id, @RequestBody Games games, HttpServletResponse response) {
//        Optional<Games> returnValue = gamesRepository.findById(Long.parseLong(id));
//        Games gamesToUpdate = GamesServiceHelpers.updateGameLogic(returnValue, games, response);
//        gamesRepository.save(gamesToUpdate);
//    }

     // DELETE logic
    public void deleteGame(Long id){
        boolean exists = gamesRepository.existsById(id);
        if (!exists){
            throw new IllegalStateException(
                    "You nerd, that id" + id + "ain't in this table"
            );
        }
        gamesRepository.deleteById(id);
    }

//    public void deleteGame(Long id){
//        Optional<Games> deleteValue = gamesRepository.findById(Long.parseLong(id));
//        Games gamesToDelete = GamesServiceHelpers.deleteGameLogic(deleteValue);
//        gamesRepository.delete(gamesToDelete);
//    }
}
