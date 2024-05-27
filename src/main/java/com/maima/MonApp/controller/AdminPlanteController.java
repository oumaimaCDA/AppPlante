package com.maima.MonApp.controller;

import com.maima.MonApp.model.Plante;
import com.maima.MonApp.model.User;
import com.maima.MonApp.request.CreatePlanteRequest;
import com.maima.MonApp.response.MessageResponse;
import com.maima.MonApp.service.PlanteService;
import com.maima.MonApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/admin/plantes")
public class AdminPlanteController {
    @Autowired
    private PlanteService planteService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Plante> createPlante(
            @RequestBody CreatePlanteRequest req,
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (user == null) {
                System.out.println("User not found for the provided JWT");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            System.out.println("User found: " + user.getUsername());
            if (!user.hasRole("ADMIN")) {
                System.out.println("User does not have ROLE_ADMIN");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Plante plante = planteService.createPlante(req, user);
            System.out.println("Plante created: " + plante);
            return new ResponseEntity<>(plante, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Plante> updatePlante(
            @RequestBody CreatePlanteRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (!user.hasRole("ADMIN")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Plante plante = planteService.updatePlante(id, req);
            return new ResponseEntity<>(plante, HttpStatus.CREATED);
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePlante(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (!user.hasRole("ADMIN")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            planteService.deletePlante(id);

            MessageResponse pla = new MessageResponse();
            pla.setMessage("plante deleted successfully");
            return new ResponseEntity<>(pla, HttpStatus.OK);
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Plante> updatePlanteStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (!user.hasRole("ADMIN")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Plante plante = planteService.updatePlanteStatus(id);
            return new ResponseEntity<>(plante, HttpStatus.OK);
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Plante> findPlanteByUserId(
            @RequestHeader("Authorization") String jwt
    ) {
        try {
            User user = userService.findUserByJwtToken(jwt);
            if (!user.hasRole("ADMIN")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Plante plante = planteService.getPlanteByUserId(user.getId());
            return new ResponseEntity<>(plante, HttpStatus.OK);
        } catch (Exception e) {
            // Gérer l'exception de manière appropriée
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}