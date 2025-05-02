package com.asciugano.edugame.controller;

import com.asciugano.edugame.model.Games;
import com.asciugano.edugame.model.User;
import com.asciugano.edugame.repository.GamesRepository;
import com.asciugano.edugame.repository.UserRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
public class GamesController {

    private final GamesRepository gamesRepository;
    private final UserRepository userRepository;

    public GamesController(GamesRepository gamesRepository, UserRepository userRepository) {
        this.gamesRepository = gamesRepository;
        this.userRepository = userRepository;
    }

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

            try {
                String imageName = game.get().getCreator() + "/" + game.get().getContainer();
                DockerClient dockerClient = (DockerClient) DockerClientBuilder.getInstance();
                dockerClient.pullImageCmd(imageName).start().awaitCompletion();

                CreateContainerResponse response = dockerClient.createContainerCmd(imageName)
                        .withName(game.get().getContainer())
                        .withHostConfig(HostConfig.newHostConfig()
                                .withPortBindings(
                                        new PortBinding(Ports.Binding.bindPort(5900), ExposedPort.tcp(5900))
                                ))
                        .exec();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
//                e.printStackTrace();
            }
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return "game";
    }

    @GetMapping("/user/cerca")
    public User getUser(@RequestParam String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent())
            return user.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}
