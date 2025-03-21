package com.asciugano.edugame.controller;

import com.asciugano.edugame.model.Games;
import com.asciugano.edugame.repository.GamesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class GamesController {

    private final GamesRepository gamesRepository;

    public GamesController(GamesRepository gamesRepository) { this.gamesRepository = gamesRepository; }

    @GetMapping("/")
    public String info() {
        return "info";
    }

    @GetMapping("/info")
    public String info2() {
        return info();
    }

    @GetMapping("/play")
    public String play(Model model) {
        List<Games> games = gamesRepository.findAll();
        model.addAttribute("games", games);
        return "play";
    }

    @GetMapping("/play/{id}")
    public String play(@PathVariable Integer id, Model model) {
        Optional<Games> game = gamesRepository.findById(id);
        if (game.isPresent()) {
            model.addAttribute("game", game.get());
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return "game";
    }

}
