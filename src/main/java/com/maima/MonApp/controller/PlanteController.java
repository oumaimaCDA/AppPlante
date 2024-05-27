package com.maima.MonApp.controller;

import com.maima.MonApp.dto.PlanteDto;
import com.maima.MonApp.model.Plante;
import com.maima.MonApp.model.User;
import com.maima.MonApp.request.CreatePlanteRequest;
import com.maima.MonApp.service.PlanteService;
import com.maima.MonApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class PlanteController {

    @Autowired
    private PlanteService planteService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Plante>> searchPlante(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Plante> plante=planteService.searchPlante(keyword);
        return new ResponseEntity<>(plante, HttpStatus.OK);

    }
    @GetMapping("/plantes")
    public ResponseEntity<List<Plante>> getAllPlante(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Plante> plante=planteService.getAllPlante();
        return new ResponseEntity<>(plante, HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Plante> findPlanteById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Plante plante=planteService.findPlanteById(id);
        return new ResponseEntity<>(plante, HttpStatus.OK);

    }
    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<PlanteDto> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        PlanteDto plante=planteService.addToFavorites(id,user);
        return new ResponseEntity<>(plante, HttpStatus.OK);

    }

    }


